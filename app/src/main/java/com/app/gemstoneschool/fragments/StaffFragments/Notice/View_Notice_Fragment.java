package com.app.gemstoneschool.fragments.StaffFragments.Notice;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Activities.NoticeActivity;
import com.app.gemstoneschool.Adapters.NewsAdapter;
import com.app.gemstoneschool.Adapters.THWAdapter;
import com.app.gemstoneschool.Adapters.TeacherNewsAdapter;
import com.app.gemstoneschool.Model.TeacherNewsModel;
import com.app.gemstoneschool.Model.TodaysHwModel;
import com.app.gemstoneschool.NoticeRecycler.NewsModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View_Notice_Fragment extends Fragment {
    //api fetch data
    RecyclerView recyclerView;
    List<TeacherNewsModel> userlist = new ArrayList<>();
    TeacherNewsAdapter adapter;

    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece
    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_INSTID_PROFILESTAFF="instidstaff_profile";

    String instId;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_notice_fragment,container,false);

        sharedPreferences_staff_profile =getActivity().getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        instId=sharedPreferences_staff_profile.getString(KEY_INSTID_PROFILESTAFF,null);

        ProgressDialog progressDialog=new ProgressDialog(getContext());//progress dialouge initialization
        //progress dialogue
        progressDialog.setMessage("Please Wait");
        progressDialog.show();
        //giving delay of 3 sec to progress dialogue
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.dismiss();

            }
        },3000);
        //toolbar with back button

        recyclerView = v.findViewById(R.id.recyclerview_vnotice);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getData();

        return v;
    }

    private void getData() {

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        String url="https://gemstonews.in/gemstoneerp/apis/teacher/get_notice_of_teacher_list.php";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {



                try {
                    JSONArray jsonArray=response.getJSONArray("todayhw");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        userlist.add(new TeacherNewsModel(jsonObject.getString("news_id"),
                                jsonObject.getString("news_desc"),
                                jsonObject.getString("news_title"),
                                jsonObject.getString("news_image"),
                                jsonObject.getString("news_date")
                                ));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapter=new TeacherNewsAdapter(getContext(),userlist);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                Log.d("error",error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("inst_id", instId);
                return params;
            }
        };
        requestQueue.add(jsonObjectRequest);

    }
    //end of api fetch data

}


