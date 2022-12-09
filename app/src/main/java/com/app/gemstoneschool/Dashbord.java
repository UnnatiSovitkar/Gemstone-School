package com.app.gemstoneschool;

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
import android.util.Log;
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
import com.app.gemstoneschool.Activities.MainActivity;
import com.app.gemstoneschool.fragments.Calenderfragment;
import com.app.gemstoneschool.fragments.Contactusdrawerfragment;
import com.app.gemstoneschool.fragments.DrawerFragmentAboutus;
import com.app.gemstoneschool.fragments.Galleryfragment;
import com.app.gemstoneschool.fragments.StaffFragments.Activities.View_Activities_Fragment;
import com.app.gemstoneschool.fragments.StudentFragments.Homefragment;
import com.app.gemstoneschool.fragments.StudentFragments.Mefragment;
import com.app.gemstoneschool.fragments.Remarkdrawerfragment;
import com.app.gemstoneschool.fragments.Sendenquirydrawerfragment;
import com.app.gemstoneschool.fragments.StudentFragments.ViewStdHwfragment;
import com.app.gemstoneschool.fragments.Videosfragment;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.google.android.datatransport.backend.cct.BuildConfig;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Dashbord extends AppCompatActivity {

    Toolbar toolbar;//top toolbar
    DrawerLayout drawer;//drawer with items

    SharedPreferences sharedPreferences;

    String stdname,mobileN,srid,cls,section,board,year,name,clid;

    //created 03-10-2022
    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String KEY_MOBILE="mobile";
    private static final String KEY_CLID="clid";
    private static final String Key_SRID="srId";
    private static final String KEY_NAME="studName";
    private static final String KEY_CLS="studcls";
    private static final String KEY_SECTION="studsection";
    private static final String KEY_BOARD="studbrd";
    private static final String KEY_YEAR="studyr";

    NavigationView navigationView;
    BroadcastReceiver broadcastReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //disable screenshot
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashbord);


        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        navigationView=findViewById(R.id.nav_view);//initialization of drawer navigation
        ProgressDialog progress=new ProgressDialog(this);
        //mobile no from sharepreference

        sharedPreferences=getApplicationContext().getSharedPreferences(SHARED_PREF_NAME_STD,MODE_PRIVATE);

        String mobileNo=sharedPreferences.getString(KEY_MOBILE,null);
        System.out.println(mobileNo);

        srid=sharedPreferences.getString(Key_SRID,null);
        System.out.println(srid);


        //fetching std detail through api
        getStdData();


       System.out.println("mmmmm"+name);



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_nav);//initialize bottom navigation bar
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,new Homefragment()).commit();//default as home fragment

        bottomNavigationView.setSelectedItemId(R.id.homebottom);//default asa home fragment
        //selecting fragment from bottom navigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedfragment=null;
                //selecting fragments by using item id
                switch (item.getItemId()){
                    case R.id.homebottom:

                        selectedfragment=new Homefragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;
                    case R.id.gallarybottom:
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
                        },3000);

                        selectedfragment=new Calenderfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;
                    case R.id.summarybottom://Activity fragment

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
                        },3000);
                        selectedfragment=new View_Activities_Fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                    case R.id.mebottom://profile fragment

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
                        },2000);
                        selectedfragment=new Mefragment();
                        Bundle bundle=new Bundle();//bundle is used for saving data
                        bundle.putString("mobNo",mobileNo);//store mob no in string
                        //bundle.putString("ermsg",er);
                        selectedfragment.setArguments(bundle);//string is stored in bundle
                        //replacing with me fragment
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectedfragment).commit();
                        break;

                }


                return true;
            }
        });

        //drawer navigation

        toolbar = findViewById(R.id.toolBar);//drawer navigation
        drawer = findViewById(R.id.drawerlayout);//drawer navigation


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

                    case R.id.homedrawer:

                        selectfragment1=new Homefragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectfragment1).commit();
                        //Toast.makeText(Dashbord.this, "Home", Toast.LENGTH_LONG).show();
                        break;

                    case R.id.hwdrawer:

                        selectfragment1=new ViewStdHwfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectfragment1).commit();
                        //Toast.makeText(Dashbord.this, "Home", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.aboutschooldrawer:
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
                        },3000);

                        selectfragment1=new DrawerFragmentAboutus();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectfragment1).commit();
                        //Toast.makeText(Dashbord.this, "About School", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.gallerydrawer:
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
                        },3000);

                        selectfragment1=new Galleryfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectfragment1).commit();

                        //Toast.makeText(Dashbord.this, "Gallary", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.activitydrawer:
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
                        },3000);

                        selectfragment1=new View_Activities_Fragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectfragment1).commit();
                       // Toast.makeText(Dashbord.this, "Videos", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.feedbackdrawer:

                        selectfragment1=new Remarkdrawerfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectfragment1).commit();
                        //Toast.makeText(Dashbord.this, "Remark", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.contactdrawer:

                        selectfragment1=new Contactusdrawerfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectfragment1).commit();
                        //Toast.makeText(Dashbord.this, "Contact us", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.senddrawer:
                        selectfragment1=new Sendenquirydrawerfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer,selectfragment1).commit();
                        //Toast.makeText(Dashbord.this, "Send Enquiry", Toast.LENGTH_LONG).show();
                        break;
                    case R.id.logoutdrawer:
                        /*
                        //for Logout profile
                        SharedPreferences preferences=getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor editor=preferences.edit();
                        editor.clear();
                        editor.commit();

                         */
                        sharedPreferences=getSharedPreferences(SHARED_PREF_NAME_STD,MODE_PRIVATE);
                        SharedPreferences.Editor editor1=sharedPreferences.edit();
                        editor1.clear();
                        editor1.commit();
                        Intent intentLogout=new Intent(Dashbord.this, MainActivity.class);
                        startActivity(intentLogout);
                        finishAffinity();//finishing activity

                        break;

                    case R.id.sharedrawer:

                        //share option for sharing app
                        try {

                            Intent intent=new Intent(Intent.ACTION_SEND);
                            intent.setType("text/plain");
                            intent.putExtra(Intent.EXTRA_SUBJECT,"Demo Share");
                            String sharemsg="https://play.google.com/store/apps/details?id="+ BuildConfig.VERSION_NAME+"\n\n";
                            intent.putExtra(Intent.EXTRA_TEXT,sharemsg);
                            startActivity(Intent.createChooser(intent,"share by"));

                        }catch (Exception e){
                            Toast.makeText(Dashbord.this, "Error occured", Toast.LENGTH_SHORT).show();

                        }
                        break;
                    default:
                        return true;
                }

                return true;
            }
        });

    }

    public void getStdData(){

        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        String url="https://gemstonews.in/gemstoneerp/apis/student/get_student_profile.php";
        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject1=new JSONObject(response);
                    JSONArray jsonArray1=jsonObject1.getJSONArray("std_profile");
                    for(int i=0;i<jsonArray1.length();i++){
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        stdname=jsonObject.getString("send_sudentname");
                        mobileN=jsonObject.getString("sendstud_phone");
                        cls=jsonObject.getString("send_classname");
                        clid=jsonObject.getString("get_classid");
                        section=jsonObject.getString("send_secation");
                        board=jsonObject.getString("send_board");
                        year=jsonObject.getString("send_syear");
                        // profile name in navigation drawer
                        View headerView=navigationView.getHeaderView(0);
                        TextView Namestd=headerView.findViewById(R.id.namedrawer);
                        Namestd.setText(stdname);

                        Log.d("name",stdname);

//                            Toast.makeText(getContext(), srno.toString(), Toast.LENGTH_SHORT).show();
                        SharedPreferences.Editor editor= sharedPreferences.edit();
                        editor.putString(KEY_NAME,stdname);
                        editor.putString(KEY_CLS,cls);//sr.no for calling other api
                        editor.putString(KEY_CLID,clid);//sr.no for calling other api
                        editor.putString(KEY_SECTION,section);
                        editor.putString(KEY_BOARD,board);
                        editor.putString(KEY_YEAR,year);

                        // Toast.makeText(getContext(),KEY_NAME, Toast.LENGTH_LONG).show();

                        //Saving values to editor
                        editor.commit();


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getApplicationContext(), "error.getMessage()", Toast.LENGTH_LONG).show();
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