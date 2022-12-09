package com.app.gemstoneschool.fragments.StaffFragments.HomeWork;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Adapters.TeacherHwAdapter;
import com.app.gemstoneschool.Adapters.TeacherNewsAdapter;
import com.app.gemstoneschool.Model.TeacherHwModel;
import com.app.gemstoneschool.Model.TeacherNewsModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View_Hw_Fragment extends Fragment {

    //api fetch data
    RecyclerView recyclerView;
    List<TeacherHwModel> userlist = new ArrayList<>();
    TeacherHwAdapter adapter;

    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece for profile fetch

    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_USERID_PROFILESTAFF="useridstaff_profile";
    String uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_hw_fragment,container,false);



        //share prefferece for staff profile detail
        sharedPreferences_staff_profile =getActivity().getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        //user id is used for fetching api of news uploading
        uid=sharedPreferences_staff_profile.getString(KEY_USERID_PROFILESTAFF,null);


        recyclerView = v.findViewById(R.id.recyclerview_vhw);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getHwData();
        return v;
    }

    public void getHwData() {

        ////fetching staff Profile api using mobile number
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://gemstonews.in/gemstoneerp/apis/teacher/get_homework.php";//Api//
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject Obj = new JSONObject(response);
                    JSONArray jsonArray1 = Obj.getJSONArray("hw_details");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String hwid = jsonObject.getString("hwa_id");
                        String acyear = jsonObject.getString("acad_year");
                        String board = jsonObject.getString("board_name");
                        String cls = jsonObject.getString("class_name");
                        String sec = jsonObject.getString("sec_name");
                        String sub = jsonObject.getString("sub_id");
                        String title = jsonObject.getString("hw_title");
                        String desc = jsonObject.getString("hw_description");
                        String date = jsonObject.getString("hw_date");
                        String img = jsonObject.getString("hw_material_file");


                        userlist.add(new TeacherHwModel(hwid,acyear,board,cls,sec,sub,title,desc,date,img));
//                        Toast.makeText(TeacherDashboardActivity.this, username, Toast.LENGTH_SHORT).show();
                        //put data on shared preference

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapter=new TeacherHwAdapter(getContext(),userlist);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parms = new HashMap<>();

                parms.put("teacher_id",uid);


                return parms;
            }
        };
        //Adding the string request to the queue
        queue.add(request);

    }

}
