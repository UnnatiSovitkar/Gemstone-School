package com.app.gemstoneschool.fragments.StaffFragments.Attendence;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
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
import com.app.gemstoneschool.Activities.AttendenceShowStdList;
import com.app.gemstoneschool.Adapters.AttStdListAdapter;
import com.app.gemstoneschool.Adapters.TakeAttendenceAdapter;
import com.app.gemstoneschool.Adapters.ViewAttendenceAdapter;
import com.app.gemstoneschool.Model.SelectbrdModel;
import com.app.gemstoneschool.Model.SelectclModel;
import com.app.gemstoneschool.Model.SelectyrModel;
import com.app.gemstoneschool.Model.TakeAttendenceModel;
import com.app.gemstoneschool.Model.ViewAttendenceModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class View_Attendence_Fragment extends Fragment {
    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece

    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_INSTID_PROFILESTAFF="instidstaff_profile";
    String instId,clas,yr,todaysdate,d1;

    Spinner spincls,spinbrd,spinyr;
    Button viewAttendenceBtn;
    List<SelectclModel> cllist=new ArrayList<>();
    List<SelectyrModel> yrlist=new ArrayList<>();
    private DatePickerDialog datePickerDialog;
    Button datepicker;
    String changeddate;


    ArrayAdapter<SelectclModel> clAdapter;
    ArrayAdapter<SelectyrModel> yrAdapter;


    List<ViewAttendenceModel> userlist = new ArrayList<>();
    ViewAttendenceAdapter adapter;

    RecyclerView recyclerView;
    private Calendar calendar;
    private SimpleDateFormat dateFormat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.view_attendence__fragment,container,false);
        sharedPreferences_staff_profile =getActivity().getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        instId=sharedPreferences_staff_profile.getString(KEY_INSTID_PROFILESTAFF,null);
        spincls = v.findViewById(R.id.clspinner);
        spinyr = v.findViewById(R.id.yearspinner);
        viewAttendenceBtn = v.findViewById(R.id.btnviewattendence);

        datepicker =v.findViewById(R.id.viewdate_pickr);

        //datepicker function for selection of date
        initDatepicker();
        //todays date
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        todaysdate = dateFormat.format(calendar.getTime());
        Log.d("todaysdate",todaysdate);


        // for class selection spinner
        spincls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectclModel selectclModel=(SelectclModel) spincls.getSelectedItem();
                clas=selectclModel.getId();
                Log.d("id", selectclModel.getId());

                Log.d("class", String.valueOf(selectclModel.getName()));

//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // for year selection spinner

        spinyr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                SelectyrModel selectyrModel=(SelectyrModel) spinyr.getSelectedItem();
                yr=selectyrModel.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        viewAttendenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               if(datepicker.getText().toString().equals(todaysdate)) {

                   Toast.makeText(getContext(), "Please Select Previous date ", Toast.LENGTH_SHORT).show();

               }
               else{
                   viewAttendence();
               }

            }
        });
        // get data form api
        getData();//for spinner api fetching

        recyclerView = v.findViewById(R.id.rv_view_att);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);



        //date picker button
        datepicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    datePickerDialog.show();

            }
        });

        System.out.println("datepicker = "+datepicker.getText().toString());


        return v;
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

                d1=datepicker.getText().toString();

                SimpleDateFormat format1 = new SimpleDateFormat("dd-MM-yyyy");
                SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
                //String datestring=sdf.format(d);

                try {
                    Date date1 = format1.parse(d1);
                    changeddate=sdf1.format(date1);
                    Log.d("changeddate",changeddate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

            }

        };

        int style= AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        datePickerDialog=new DatePickerDialog(getContext(),style,dateSetListener,year,month,day);
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


    // get spinner data from api 03/09/2022
    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/teacher/get_coursedetails.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    JSONArray jsonArray=respObj.getJSONArray("class_details");
                    JSONArray jsonArray4=respObj.getJSONArray("ay_details");

                    Log.d("class details",jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String classn=jsonObject.optString("class_name");
                        String clid=jsonObject.optString("class_id");

                        cllist.add(new SelectclModel(classn,clid));
//                        Log.d("s", String.valueOf(cllist));
                        clAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,cllist);
                        clAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spincls.setAdapter(clAdapter);


                    }


                    for(int k=0;k<jsonArray4.length();k++){
                        JSONObject jsonObject=jsonArray4.getJSONObject(k);
                        String yr=jsonObject.optString("ay_year");
                        String yrid=jsonObject.optString("ay_id");

                        yrlist.add(new SelectyrModel(yr,yrid));
                        yrAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,yrlist);
                        yrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinyr.setAdapter(yrAdapter);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

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


                params.put("get_course","0");



                return params;
            }
        };

        queue.add(request);
    }


    private void viewAttendence() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/teacher/get_take_att_list.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    String msg=respObj.getString("todayhwmessage");
                    Toast.makeText(getContext(), msg, Toast.LENGTH_LONG).show();
                    Log.d("response msg",msg);
                    if(msg.equals("Success")) {
                        JSONArray jsonArray = respObj.getJSONArray("todayhw_test");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            String srid = jsonObject.optString("sr_id");
                            String admiid = jsonObject.optString("admi_id");
                            String stud_name = jsonObject.optString("stud_name");
                            String att_status = jsonObject.optString("att_status");


                            userlist.add(new ViewAttendenceModel(srid, admiid, stud_name, att_status));
                        }

                    }else {

                        Toast.makeText(getContext(), "No Data is Available!", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                adapter=new ViewAttendenceAdapter(getContext(),userlist);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_LONG).show();
                Log.d("error msg", String.valueOf(error));

            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("class_id",clas);
                params.put("ay_id",yr);
                params.put("stud_attend_date",changeddate);

                return params;
            }
        };

        queue.add(request);
    }

}
