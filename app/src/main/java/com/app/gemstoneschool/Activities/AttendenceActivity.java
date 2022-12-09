package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Adapters.AttendenceAdapter;
import com.app.gemstoneschool.Model.AttendenceModel;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendenceActivity extends AppCompatActivity {

    SharedPreferences sp;//defining share prefference
    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String Key_ADMID="admissionId";
    String admid;


    BroadcastReceiver broadcastReceiver;//no internet

    //api fetch data
    RecyclerView recyclerView;
    List<AttendenceModel> attendencelist = new ArrayList<>();
    AttendenceAdapter adapter;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen
        setContentView(R.layout.activity_attendence);

        ProgressDialog progressDialog = new ProgressDialog(this);//progress dialouge initialization
        //no internet connection
        broadcastReceiver = new NetworkBroadcast();
        registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

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
        }, 3000);
//        toolbar with back button

        toolbar = findViewById(R.id.toolbaratt);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sp=getSharedPreferences(SHARED_PREF_NAME_STD,MODE_PRIVATE);//initialization of shareprefference1 object
        admid=sp.getString(Key_ADMID,null);//save the content of KEY_name into string
//        t.setText(srid);

        //api fetch data


        recyclerView = findViewById(R.id.attrecyclev);//initializing recycler view
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        getData();//fetching data using api using volley lib


    }




    //fetching data using api using volley lib
    private void getData() {

        ////fetching homework data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://gemstonews.in/gemstoneerp/apis/student/getstudent_attdapi.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String message=jsonObject.getString("message");
                    if(message.equalsIgnoreCase("Success")){
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for(int i=0;i<jsonArray.length();i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            attendencelist.add(new AttendenceModel(jsonObject1.getString("stud_att_id"),
                                    jsonObject1.getString("inst_id"),
                                    jsonObject1.getString("user_id"),
                                    jsonObject1.getString("user_type"),
                                    jsonObject1.getString("class_id"),
                                    jsonObject1.getString("board_id"),
                                    jsonObject1.getString("sec_id"),
                                    jsonObject1.getString("ay_id"),
                                    jsonObject1.getString("admission_id"),
                                    jsonObject1.getString("att_status"),
                                    jsonObject1.getString("leave_reason"),
                                    jsonObject1.getString("stud_att_date"),
                                    jsonObject1.getString("stud_att_status"),
                                    jsonObject1.getString("created_at"),
                                    jsonObject1.getString("updated_at")));

//                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "No Record Found...!", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter=new AttendenceAdapter(getApplicationContext(),attendencelist);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                Log.d("admission id",admid);
                parms.put("admi_id",admid);

                return parms;
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