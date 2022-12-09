package com.app.gemstoneschool.fragments.StaffFragments.Attendence;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Adapters.NewsAdapter;
import com.app.gemstoneschool.Adapters.THWAdapter;
import com.app.gemstoneschool.Adapters.TakeAttendenceAdapter;
import com.app.gemstoneschool.Model.SelectbrdModel;
import com.app.gemstoneschool.Model.SelectclModel;
import com.app.gemstoneschool.Model.SelectyrModel;
import com.app.gemstoneschool.Model.TakeAttendenceModel;
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

public class Upload_Attendence_Fragment extends Fragment {

    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece

    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_INSTID_PROFILESTAFF="instidstaff_profile";
    String instId;
    List<TakeAttendenceModel> userlist = new ArrayList<>();
    TakeAttendenceAdapter adapter;

    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.upload_attendence_fragment,container,false);
        sharedPreferences_staff_profile =getActivity().getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        instId=sharedPreferences_staff_profile.getString(KEY_INSTID_PROFILESTAFF,null);


        SwipeRefreshLayout swipeRefreshLayout =v.findViewById(R.id.refreshLayout);

        // Refresh  the layout
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        // Your code goes here
                        // In this code, we are just
                        // changing the text in the textbox

                        // This line is important as it explicitly
                        // refreshes only once
                        // If "true" it implicitly refreshes forever
                        swipeRefreshLayout.setRefreshing(false);



                    }
                }
        );


        recyclerView = v.findViewById(R.id.rvatt);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getData();
        return v;
    }

    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/teacher/get_classes_list_for_take_att.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject respObj = new JSONObject(response);
                    JSONArray jsonArray=respObj.getJSONArray("todayhw_test");

                    Log.d("class details",jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String classn=jsonObject.optString("class_name");
                        String boardname=jsonObject.optString("board_name");
                        String sec_name=jsonObject.optString("sec_name");
                        String start_year=jsonObject.optString("start_year");
                        String Today_att_status=jsonObject.optString("Today_att_status");
                        String class_id=jsonObject.optString("class_id");
                        String board_id=jsonObject.optString("board_id");
                        String sec_id=jsonObject.optString("sec_id");
                        String ay_id=jsonObject.optString("ay_id");

                        userlist.add(new TakeAttendenceModel(classn,boardname,sec_name,start_year,Today_att_status,class_id
                                ,board_id,sec_id,ay_id));
//                        Log.d("s", String.valueOf(cllist));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter=new TakeAttendenceAdapter(getContext(),userlist);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("inst_id",instId);



                return params;
            }
        };

        queue.add(request);
    }


}
