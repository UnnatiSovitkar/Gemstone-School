package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Adapters.AttStdListAdapter;
import com.app.gemstoneschool.Adapters.TakeAttendenceAdapter;
import com.app.gemstoneschool.Model.AllStdListModel;
import com.app.gemstoneschool.Model.AttStdListModel;
import com.app.gemstoneschool.Model.TakeAttendenceModel;
import com.app.gemstoneschool.R;
import com.bumptech.glide.load.model.Model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttendenceShowStdList extends AppCompatActivity {

    Toolbar toolbar;
    String name,id,date,yr;
    ArrayList<AttStdListModel> userlist = new ArrayList<>();
    List<AttStdListModel> list=new ArrayList<AttStdListModel>();
    ArrayList<AllStdListModel> stdlist = new ArrayList<>();

    AttStdListAdapter adapter;
    Button sbmt;

    RecyclerView recyclerView;
    private DatePickerDialog datePickerDialog;
    Button datepicker;

    private Calendar calendar;
    private SimpleDateFormat dateFormat;
    private String datee,clid,sectionId,brdId,yrId,instId,usid;

    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece

    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_INSTID_PROFILESTAFF="instidstaff_profile";
    private static final String KEY_USERID_PROFILESTAFF="useridstaff_profile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen

        setContentView(R.layout.activity_attendence_show_std_list);
        sbmt =findViewById(R.id.smt);


        sharedPreferences_staff_profile =getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        instId=sharedPreferences_staff_profile.getString(KEY_INSTID_PROFILESTAFF,null);
        usid=sharedPreferences_staff_profile.getString(KEY_USERID_PROFILESTAFF,null);

        //getting data from TakeAttendenceAdapter cardview
        clid=getIntent().getExtras().getString("ClassId");
        sectionId=getIntent().getExtras().getString("sectionId");
        Log.d("cliddd",clid);
        brdId=getIntent().getExtras().getString("boardId");
        yrId=getIntent().getExtras().getString("YearId");


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        date = sdf.format(new Date());
        Log.d("dttt",date);

        recyclerView =findViewById(R.id.rvstdlist);

        userlist = (ArrayList<AttStdListModel>) getModel(false);
        adapter = new AttStdListAdapter(this,userlist);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));




//        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
//        recyclerView.setHasFixedSize(true);

        getData();


        //toolbar
        toolbar=findViewById(R.id.toolbarstdlist);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sbmt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitAttendence();

//                Intent intent = new Intent(AttendenceShowStdList.this,SubmitAttendenceActivity.class);
//                startActivity(intent);
            }
        });

        datepicker = findViewById(R.id.adddate_pickr);

        //datepicker function for selection of date
        initDatepicker();

        //date picker button
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });


    }
    private List<AttStdListModel> getModel(boolean isSelect){
        List<AttStdListModel> list = new ArrayList<>();
        for(int i = 0; i < list.size(); i++){

            AttStdListModel model = new AttStdListModel();
            model.setSelected(isSelect);
//            model.setStud_name(String.valueOf(list.get(i)));
            model.setAdmi_id(String.valueOf(list.get(i)));
            list.add(model);
        }
        return list;
    }


    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/teacher/get_studteachers_att.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);

                    JSONArray jsonArray5=respObj.getJSONArray("studatt_details");

                    Log.d("std details",jsonArray5.toString());
                    for(int i=0;i<jsonArray5.length();i++){
                        JSONObject jsonObject=jsonArray5.getJSONObject(i);
                        String sr_id=jsonObject.optString("sr_id");
                        String admi_id=jsonObject.optString("admi_id");
                        String stud_name=jsonObject.optString("stud_name");
                        String stud_phone=jsonObject.optString("stud_phone");
                        String stud_email=jsonObject.optString("stud_email");
                        String attd_status=jsonObject.optString("attd_status");
                        String attd_reason=jsonObject.optString("attd_reason");

                        userlist.add(new AttStdListModel(sr_id,admi_id,stud_name,stud_phone,stud_email,
                                attd_status,attd_reason));
                        Log.d("s", String.valueOf(userlist));


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter=new AttStdListAdapter(getApplicationContext(),userlist);
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


                params.put("get_studattd","1");
                params.put("class_id",clid);
                params.put("board_id",brdId);
                params.put("sec_id",sectionId);
                params.put("ay_id",yrId);
                params.put("stud_attend_date",date);


                return params;
            }
        };

        queue.add(request);
    }

    //submit attendence

    private void submitAttendence() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/teacher/take_studteacher_att.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    String msg=respObj.getString("message");
                    Toast.makeText(AttendenceShowStdList.this, msg, Toast.LENGTH_LONG).show();
                    Log.d("response msg",msg);
                    if(msg.equals("Attendance Generated Successfully..!")){
                        finish();



                    }




                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_LONG).show();
                Log.d("error msg", String.valueOf(error));

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("student_attend_submit","1");
                params.put("board_id",brdId);
                params.put("class_id",clid);
                params.put("sec_id",sectionId);
                params.put("ay_id",yrId);

                ArrayList<String> allstd = new ArrayList<String>();

                for(int i=0; i < userlist.size(); i++) {
                    allstd.add(userlist.get(i).getAdmi_id());
                }
                params.put("admission_id", String.valueOf(allstd));
                Log.d("allstudents", String.valueOf(allstd));


                ///selected student list
                ArrayList<String> attstd = new ArrayList<String>();
                for (int i = 0; i < AttStdListAdapter.userlist.size(); i++) {
                    if (AttStdListAdapter.userlist.get(i).getSelected()) {
                        attstd.add(AttStdListAdapter.userlist.get(i).getAdmi_id());
                    }
                }
                params.put("att_status", String.valueOf(attstd));
                Log.d("attstudents", String.valueOf(attstd));

                params.put("stud_attend_date",date);
                params.put("inst_id",instId);
                params.put("user_id",usid);

                return params;
            }
        };

        queue.add(request);
    }

    ///date picker function
    private void initDatepicker() {
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);



        String date=makeDateString(day,month+1,year);
        Log.d("dtttt",date);
        datepicker.setText(date);




        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month=month+1;
                String date=makeDateString(dayOfMonth,month,year);
                datepicker.setText(date);
            }

        };

        int style= AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        datePickerDialog=new DatePickerDialog(AttendenceShowStdList.this,style,dateSetListener,year,month,day);
    }
///date picker function


    private String makeDateString(int dayOfMonth, int month, int year) {

        int mth= month;
        String fm=""+mth;
        String fd=""+dayOfMonth;
        if(mth<10){
            fm ="0"+mth;
        }
        if (dayOfMonth<10){
            fd="0"+dayOfMonth;
        }
        String date= fd+"-"+fm+"-"+year;

        return date;
    }


    //toolbar back to home
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==android.R.id.home){

            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}