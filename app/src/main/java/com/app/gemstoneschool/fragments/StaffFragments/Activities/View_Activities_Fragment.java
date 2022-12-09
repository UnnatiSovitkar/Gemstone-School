package com.app.gemstoneschool.fragments.StaffFragments.Activities;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Adapters.ActivityAdapter;
import com.app.gemstoneschool.Adapters.TeacherNewsAdapter;
import com.app.gemstoneschool.Model.ActivityModel;
import com.app.gemstoneschool.Model.TeacherNewsModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class View_Activities_Fragment extends Fragment {

    //api fetch data
    RecyclerView recyclerView;
    List<ActivityModel> list = new ArrayList<>();
    ActivityAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_activities_fragment,container,false);

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

        recyclerView = v.findViewById(R.id.recyclerview_vactivity);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getData();

//        WebView webView=v.findViewById(R.id.webview_vactivity);
//        webView.loadUrl("https://gemstonews.in/app_activity.php");
//        WebSettings webSettings=webView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
        return v;
    }



    private void getData() {

        RequestQueue requestQueue= Volley.newRequestQueue(getContext());
        String url="https://gemstonews.in/gemstoneerp/apis/student/get_activity.php";

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {



                try {
                    JSONArray jsonArray=response.getJSONArray("todayN");
                    for(int i=0;i<jsonArray.length();i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        list.add(new ActivityModel(jsonObject.getString("activity_id"),
                                jsonObject.getString("activity_title"),
                                jsonObject.getString("activity_desc"),
                                jsonObject.getString("activity_img"),
                                jsonObject.getString("cdate")));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapter=new ActivityAdapter(getContext(),list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
    //end of api fetch data


}
