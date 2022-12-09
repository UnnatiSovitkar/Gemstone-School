package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
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
import com.app.gemstoneschool.BuildConfig;
import com.app.gemstoneschool.Dashbord;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.fragments.Calenderfragment;
import com.app.gemstoneschool.fragments.Contactusdrawerfragment;
import com.app.gemstoneschool.fragments.DrawerFragmentAboutus;
import com.app.gemstoneschool.fragments.Galleryfragment;
import com.app.gemstoneschool.fragments.StaffFragments.Activities.View_Activities_Fragment;
import com.app.gemstoneschool.fragments.StudentFragments.Homefragment;
import com.app.gemstoneschool.fragments.Remarkdrawerfragment;
import com.app.gemstoneschool.fragments.Sendenquirydrawerfragment;
import com.app.gemstoneschool.fragments.StaffFragments.StaffHomefragment;
import com.app.gemstoneschool.fragments.StaffFragments.StaffProfile_fragment;
import com.app.gemstoneschool.fragments.Videosfragment;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class TeacherDashboardActivity extends AppCompatActivity {

    Toolbar toolbar;//top toolbar
    DrawerLayout drawer;//drawer with items
    String mobno;//used for fetching profile api

    BroadcastReceiver broadcastReceiver;

    SharedPreferences sharedPreferences_staff;//staff shareprefferece for mobile no
    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece for profile fetch


    //create a share preferences name and keys name for staff
    private static final String SHARED_PREF_NAME_STAFF="myprefstaff";
    private static final String KEY_MOBILE_STAFF="mobilestaff";

    //create a share preferences name and keys name for staff profile details
    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_NAME_PROFILESTAFF="namestaff_profile";
    private static final String KEY_INSTID_PROFILESTAFF="instidstaff_profile";
    private static final String KEY_USERID_PROFILESTAFF="useridstaff_profile";
    private static final String KEY_MOBILE_PROFILESTAFF="mobilestaff_profile";
    private static final String KEY_ADDRESS_PROFILESTAFF="addressstaff_profile";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen

        setContentView(R.layout.activity_teacher_dashboard);

        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        sharedPreferences_staff =getSharedPreferences(SHARED_PREF_NAME_STAFF, Context.MODE_PRIVATE);
        //here mob no is used to fetch profile api //
        mobno=sharedPreferences_staff.getString(KEY_MOBILE_STAFF,null);


        //share prefferece for staff profile detail
        sharedPreferences_staff_profile =getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);

//        Toast.makeText(this, nameStaff.toString(), Toast.LENGTH_SHORT).show();

        NavigationView navigationView=findViewById(R.id.nav_view_tdash);//initialization of drawer navigation
        ProgressDialog progress=new ProgressDialog(this);

        // profile name in navigation drawer
        View headerView=navigationView.getHeaderView(0);
        TextView Namestd=headerView.findViewById(R.id.namedrawer_tdash);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav_tdash);//initialize bottom navigation bar
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,new StaffHomefragment()).commit();//default as home fragment

        bottomNavigationView.setSelectedItemId(R.id.homebottom_tdash);//default asa home fragment
        //selecting fragment from bottom navigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedfragment=null;
                //selecting fragments by using item id
                switch (item.getItemId()){
                    case R.id.homebottom_tdash:

                        selectedfragment=new StaffHomefragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,selectedfragment).commit();
                        break;
                    case R.id.gallarybottom_tdash:
                        //progress dialogue

                        progress.setMessage("Please Wait");
                        progress.show();
                        //giving delay of 3 sec to progress dialogue
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progress.setCanceledOnTouchOutside(true);
                                progress.dismiss();

                            }
                        },1800);

                        selectedfragment=new Calenderfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,selectedfragment).commit();
                        break;
                    case R.id.summarybottom_tdash://Activity fragment

                        //progress dialogue

                        progress.setMessage("Please Wait");
                        progress.show();
                        //giving delay of 3 sec to progress dialogue
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progress.setCanceledOnTouchOutside(true);
                                progress.dismiss();

                            }
                        },1800);
                        selectedfragment=new View_Activities_Fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,selectedfragment).commit();
                        break;

                    case R.id.mebottom_tdash://profile fragment

                        //progress dialogue

                        progress.setMessage("Please Wait");
                        progress.show();
                        //giving delay of 3 sec to progress dialogue
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progress.setCanceledOnTouchOutside(true);
                                progress.dismiss();

                            }
                        },1800);
                        selectedfragment=new StaffProfile_fragment();
                        Bundle bundle=new Bundle();//bundle is used for saving data
//                        bundle.putString("mobNo",mobileNo);//store mob no in string
                        //bundle.putString("ermsg",er);
                        selectedfragment.setArguments(bundle);//string is stored in bundle
                        //replacing with me fragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,selectedfragment).commit();
                        break;

                }


                return true;
            }
        });

        //drawer navigation

        toolbar = findViewById(R.id.toolBar_tdash);//drawer navigation
        drawer = findViewById(R.id.drawerlayout_teacher);//drawer navigation


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {//drawer navigation
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }//drawer navigation
        });
        //selecting items from drawer layout
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {


                int id=item.getItemId();
                drawer.closeDrawer(GravityCompat.START);
                Fragment selectfragment1=null;

                switch (id){

                    case R.id.homedrawer_tdash:

                        selectfragment1=new StaffHomefragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,selectfragment1).commit();
                        //Toast.makeText(Dashbord.this, "Home", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.aboutschooldrawer_tdash:
                        //progress dialogue

                        progress.setMessage("Please Wait");
                        progress.show();
                        //giving delay of 3 sec to progress dialogue
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progress.setCanceledOnTouchOutside(true);
                                progress.dismiss();

                            }
                        },1800);

                        selectfragment1=new DrawerFragmentAboutus();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,selectfragment1).commit();
                        //Toast.makeText(Dashbord.this, "About School", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.gallerydrawer_tdash:
                        //progress dialogue

                        progress.setMessage("Please Wait");
                        progress.show();
                        //giving delay of 3 sec to progress dialogue
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progress.setCanceledOnTouchOutside(true);
                                progress.dismiss();

                            }
                        },1800);

                        selectfragment1=new Galleryfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,selectfragment1).commit();

                        //Toast.makeText(Dashbord.this, "Gallary", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.activitydrawer_tdash:
                        //progress dialogue

                        progress.setMessage("Please Wait");
                        progress.show();
                        //giving delay of 3 sec to progress dialogue
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progress.setCanceledOnTouchOutside(true);
                                progress.dismiss();

                            }
                        },1800);

                        selectfragment1=new View_Activities_Fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,selectfragment1).commit();
                        // Toast.makeText(Dashbord.this, "Videos", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.contactdrawer_tdash:

                        selectfragment1=new Contactusdrawerfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer_tdash,selectfragment1).commit();
                        //Toast.makeText(Dashbord.this, "Contact us", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.logoutdrawer_tdash:
//                        for Logout profile
                        SharedPreferences preferences=getSharedPreferences(SHARED_PREF_NAME_STAFF,MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.clear();
                        editor.commit();
//                        sp=getSharedPreferences(SHARED_PREF_NAME1,MODE_PRIVATE);
//                        SharedPreferences.Editor editor1=sp.edit();
//                        editor1.clear();
//                        editor1.commit();
                        Intent intentLogout=new Intent(TeacherDashboardActivity.this, MainActivity.class);
                        startActivity(intentLogout);
                        finishAffinity();//finishing activity

                        break;

                    case R.id.sharedrawer_tdash:

                        //share option for sharing app
                        try {

                            Intent intent=new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT,"Demo Share");
                            String sharemsg="https://play.google.com/store/apps/details?id="+ BuildConfig.APPLICATION_ID+"\n\n";
                            intent.putExtra(Intent.EXTRA_TEXT,sharemsg);
                            startActivity(Intent.createChooser(intent,"share by"));

                        }catch (Exception e){
                            Toast.makeText(TeacherDashboardActivity.this, "Error occured", Toast.LENGTH_SHORT).show();

                        }
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });





        ////fetching staff Profile api using mobile number
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = "https://gemstonews.in/gemstoneerp/apis/teacher/get_teacherprofile.php";//Api//
                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {

                            JSONObject Obj = new JSONObject(response);
                            JSONArray jsonArray1 = Obj.getJSONArray("todayuser_profile");
                            for (int i = 0; i < jsonArray1.length(); i++) {
                                JSONObject jsonObject = jsonArray1.getJSONObject(i);

                                Namestd.setText(jsonObject.getString("user_name"));

                                String instid = jsonObject.getString("inst_id");
                                String userid = jsonObject.getString("user_id");
                                String username = jsonObject.getString("user_name");
                                String userph = jsonObject.getString("user_mob");
                                String useradd = jsonObject.getString("user_address");
        //                        Toast.makeText(TeacherDashboardActivity.this, username, Toast.LENGTH_SHORT).show();
                                //put data on shared preference

                                SharedPreferences.Editor editorprofile = sharedPreferences_staff_profile.edit();
                                editorprofile.putString(KEY_INSTID_PROFILESTAFF, instid);
                                editorprofile.putString(KEY_USERID_PROFILESTAFF, userid);
                                editorprofile.putString(KEY_NAME_PROFILESTAFF, username);
                                editorprofile.putString(KEY_MOBILE_PROFILESTAFF, userph);
                                editorprofile.putString(KEY_ADDRESS_PROFILESTAFF, useradd);

                                //Saving values to editor
                                editorprofile.commit();
                            }



                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        try {
                            JSONObject respObj = new JSONObject(String.valueOf(error));
                            String errorMsg = respObj.getString("message");
                            Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                })
                 {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {

                        Map<String, String> parms = new HashMap<>();

                        parms.put("user_mob", mobno);


                        return parms;
                    }
                };
                //Adding the string request to the queue
                queue.add(request);



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }


    protected void onDestroy() {//no internet connection
        super.onDestroy();//no internet connection
        unregisterReceiver(broadcastReceiver);//no internet connection
    }
}