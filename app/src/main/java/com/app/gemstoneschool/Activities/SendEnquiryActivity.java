package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Model.SelectPType;
import com.app.gemstoneschool.Model.SelectbrdModel;
import com.app.gemstoneschool.Model.SelectclModel;
import com.app.gemstoneschool.Model.SelectyrModel;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SendEnquiryActivity extends AppCompatActivity {

    Toolbar toolbar;
    BroadcastReceiver broadcastReceiver;

    SharedPreferences sp;
    String id;
    String type,year;

    Button submit;
    EditText fName,lName,email,mobile,message,schnam;
    String parent_name,stud_name,mobile_no,email_id,msg,school_name;
    String stdrd;

    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String Key_SRID="srId";

    Spinner spincls,spinr_ptype;
    List<String> cllist=new ArrayList<>();




    ArrayAdapter<SelectclModel> clAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen
        setContentView(R.layout.activity_send_enquiry);//toolbar with back button

        submit=findViewById(R.id.sbmtenq);
        fName=findViewById(R.id.fname);
        lName=findViewById(R.id.lname);
        email=findViewById(R.id.eid);
        mobile=findViewById(R.id.mobn);
        spincls=findViewById(R.id.classpinner_eq);
        spinr_ptype=findViewById(R.id.spinner_eq_type);
        message=findViewById(R.id.msg);
//        ptype=findViewById(R.id.type);
        schnam=findViewById(R.id.sn);


        //current year
        Calendar calendar = Calendar.getInstance();
        int yr = calendar.get(Calendar.YEAR);
        int yr2=calendar.get(Calendar.YEAR)+1;

        year=String.valueOf(yr)+"-"+String.valueOf(yr2);

        List<SelectPType> userlist=new ArrayList<>();
        SelectPType type1=new SelectPType("NewParent","New Parent");

        userlist.add(type1);
        SelectPType type2=new SelectPType("oparent","Old Parent");
        userlist.add(type2);

        ArrayAdapter<SelectPType> typeArrayAdapter=new ArrayAdapter<SelectPType>(this,
                android.R.layout.simple_spinner_item,userlist);
        typeArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);
        spinr_ptype.setAdapter(typeArrayAdapter);


        // for class selection spinner
        spinr_ptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                SelectPType selectPType=(SelectPType) parent.getSelectedItem();
                type=selectPType.getType();
                if(type.equals("oparent")){
                    schnam.setVisibility(View.GONE);
                }
//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        getSpinnerData();

        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        sp=getSharedPreferences(SHARED_PREF_NAME_STD,MODE_PRIVATE);//initialization of shareprefference1 object
        id=sp.getString(Key_SRID,null);

        toolbar=findViewById(R.id.toolbarsendenquiry);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        // for class selection spinner
        spincls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                stdrd=spincls.getSelectedItem().toString();
                Log.d("cl",stdrd);

//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getData();
//                    Toast.makeText(SendEnquiryActivity.this, "Submitted Successfully!", Toast.LENGTH_LONG).show();


            }
        });

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
        String url="https://gemstonews.in/gemstoneerp/apis/student/parent_sendenqry.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    Toast.makeText(SendEnquiryActivity.this,jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    fName.setText("");
                    lName.setText("");
                    email.setText("");
                    mobile.setText("");
                    message.setText("");
                    schnam.setText("");

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
                parms.put("sendenq","1");
                parms.put("fullname",fName.getText().toString());
                parms.put("students",lName.getText().toString());
                parms.put("mobile",mobile.getText().toString());
                parms.put("emailid",email.getText().toString());
                parms.put("pstd",stdrd);
                parms.put("pmsg",message.getText().toString());
                parms.put("year",year);
                parms.put("ptype",type);
                parms.put("SchoolName",schnam.getText().toString());



                return parms;
            }
        };

        queue.add(request);

    }

    
    private void getSpinnerData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/teacher/get_coursedetails.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    JSONArray jsonArray=respObj.getJSONArray("class_details");

                    Log.d("class details",jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String classn=jsonObject.optString("class_name");

                        cllist.add(classn);
                        Log.d("s", String.valueOf(cllist));
                        clAdapter=new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item,cllist);
                        clAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spincls.setAdapter(clAdapter);


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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


                params.put("get_course","0");



                return params;
            }
        };

        queue.add(request);
    }

}