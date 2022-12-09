package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Adapters.AttStdListAdapter;
import com.app.gemstoneschool.Adapters.NewsAdapter;
import com.app.gemstoneschool.Model.AttStdListModel;
import com.app.gemstoneschool.NoticeRecycler.NewsModel;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoticeActivity extends AppCompatActivity {
    Toolbar toolbar;
    BroadcastReceiver broadcastReceiver;
    SharedPreferences sharedPreferences;


    //api fetch data
    RecyclerView recyclerView;
    List<NewsModel> userlist = new ArrayList<>();
    NewsAdapter adapter;

    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String KEY_CLID="clid";//class id

    String clId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen
        setContentView(R.layout.activity_notice);

        ProgressDialog progressDialog=new ProgressDialog(this);//progress dialouge initialization
        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

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

        sharedPreferences= getSharedPreferences(SHARED_PREF_NAME_STD, Context.MODE_PRIVATE);
        clId=sharedPreferences.getString(KEY_CLID,null);
        Log.d("clsid",clId);
        //toolbar with back button

        toolbar=findViewById(R.id.toolbarnotice);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //api fetch data

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getData();
    }



    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/student/get_newsupdate.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);

                    JSONArray jsonArray5=respObj.getJSONArray("todayhw");

                    Log.d("std details",jsonArray5.toString());
                    for(int i=0;i<jsonArray5.length();i++){
                        JSONObject jsonObject=jsonArray5.getJSONObject(i);
                        String news_id=jsonObject.optString("news_id");
                        String news_desc=jsonObject.optString("news_desc");
                        String news_title=jsonObject.optString("news_title");
                        String news_image=jsonObject.optString("news_image");
                        String news_date=jsonObject.optString("news_date");


                        userlist.add(new NewsModel(news_id,news_desc,news_title,news_image,news_date));
                        Log.d("s", String.valueOf(userlist));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter = new NewsAdapter(getApplicationContext(), userlist);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("class_id", clId);


                return params;
            }
        };

        queue.add(request);
    }


    //end of api fetch data
  //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    protected void onDestroy() {//no internet connection
        super.onDestroy();//no internet connection
        unregisterReceiver(broadcastReceiver);//no internet connection
    }
}