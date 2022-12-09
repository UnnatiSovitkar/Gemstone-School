package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    Toolbar toolbar;
    BroadcastReceiver broadcastReceiver;
    EditText feedback;
    TextView pName;
    Button submit;

    SharedPreferences sp;

    String id;


    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String Key_SRID="srId";
    private static final String KEY_NAME="studName";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen
        setContentView(R.layout.activity_feedback);

        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        //sending feedback using gmail//
        pName=findViewById(R.id.prntN);
        feedback=findViewById(R.id.fb);
        submit=findViewById(R.id.sbmit);

        sp=getSharedPreferences(SHARED_PREF_NAME_STD,MODE_PRIVATE);//initialization of shareprefference1 object
        String nameStd=sp.getString(KEY_NAME,null);//save the content of KEY_name into string
        id=sp.getString(Key_SRID,null);

        pName.setText(nameStd);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(feedback.getText().toString().equals(""))) {
                    getData();
                    Toast.makeText(FeedbackActivity.this, "Submitted successfully", Toast.LENGTH_SHORT).show();
                    feedback.setText(null);
                    Intent intent = new Intent(getApplicationContext(), ThankYouFeedback.class);
                    startActivity(intent);

                }
                else {
                    Toast.makeText(FeedbackActivity.this, "Please fill the feedback!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //toolbar with back button

        toolbar=findViewById(R.id.toolbarfeedback);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
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

    private void getData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://gemstonews.in/gemstoneerp/apis/student/parent_sendfeedback.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);

                } catch (Exception e) {
                    e.printStackTrace();
                }

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
                parms.put("sr_id",id);
                parms.put("testimonial_name",pName.getText().toString());
                parms.put("testimonial_desc",feedback.getText().toString());

                return parms;
            }
        };

        queue.add(request);

    }
}