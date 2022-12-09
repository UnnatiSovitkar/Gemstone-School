package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
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

public class FeesActivity extends AppCompatActivity {

    SharedPreferences sp;
    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String Key_SRID="srId";
    private static final String KEY_MOBILE="mobile";
    private static final String KEY_NAME="studName";
    private static final String KEY_CLS="studcls";
    private static final String KEY_SECTION="studsection";
    private static final String KEY_BOARD="studbrd";
    private static final String KEY_YEAR="studyr";
    private static final String Key_ADMID="admissionId";
    private static final String Key_INSTID="instituteId";

    String srid;

    private TextView adm_id,totalfee,amount_payable,payinst_status,paidamt,remain,
            payment_installment,name,clas,board,section,yr;

    Toolbar toolbar;
    BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen
        setContentView(R.layout.activity_fees);


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
        }, 2000);
//        toolbar with back button

        toolbar = findViewById(R.id.toolbarfees);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name = findViewById(R.id.feesname);
        clas = findViewById(R.id.feesclass);
        board = findViewById(R.id.feesbrd);
        section = findViewById(R.id.feessection);
        yr = findViewById(R.id.feesyr);


        sp=getSharedPreferences(SHARED_PREF_NAME_STD,MODE_PRIVATE);//initialization of shareprefference1 object
        srid=sp.getString(Key_SRID,null);//save the content of KEY_name into string
        name.setText(sp.getString(KEY_NAME,null));
        clas.setText(sp.getString(KEY_CLS,null));
        board.setText(sp.getString(KEY_BOARD,null));
        section.setText(sp.getString(KEY_SECTION,null));

        int y= Integer.parseInt(sp.getString(KEY_YEAR,null));
        yr.setText(y+"-"+(y+1));

        //api fetch data

        adm_id=findViewById(R.id.t1);
        amount_payable=findViewById(R.id.t3);
        paidamt=findViewById(R.id.t5);
        remain=findViewById(R.id.t6);

        getData();
    }


    private void getData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://gemstonews.in/gemstoneerp/apis/student/getstudent_payreceipts.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String message=jsonObject.getString("message");
                    if(message.equalsIgnoreCase("Success")){


                        adm_id.setText(jsonObject.getString("admission_id"));
                        amount_payable.setText(jsonObject.getString("amount_payable"));
                        paidamt.setText(jsonObject.getString("paidamt"));
                        remain.setText(jsonObject.getString("remain"));
                        payment_installment.setText(jsonObject.getString("payment_installment"));

//                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();

                    }else {
                        Toast.makeText(getApplicationContext(), "No Record Found...!", Toast.LENGTH_LONG).show();
                    }

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
                parms.put("sr_id",srid);

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