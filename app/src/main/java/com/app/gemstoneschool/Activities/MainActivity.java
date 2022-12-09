package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import android.widget.Toast;

import com.app.gemstoneschool.Dashbord;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.fragments.Calenderfragment;
import com.app.gemstoneschool.fragments.Homefragmentmain;
import com.app.gemstoneschool.fragments.Loginfragment;
import com.app.gemstoneschool.fragments.StaffFragments.Activities.View_Activities_Fragment;
import com.app.gemstoneschool.fragments.Videosfragment;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences_staff;


    //create a share preferences name and keys name for student

    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String KEY_MOBILE="mobile";

    //create a share preferences name and keys name for staff
    private static final String SHARED_PREF_NAME_STAFF="myprefstaff";
    private static final String KEY_MOBILE_STAFF="mobilestaff";
    private static final String Key_ERROR_STAFF="messagestaff";

    BroadcastReceiver broadcastReceiver;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen
        setContentView(R.layout.activity_main);


        //share prefernce for staff//
        sharedPreferences_staff = getSharedPreferences(SHARED_PREF_NAME_STAFF, Context.MODE_PRIVATE);
        String mobno=sharedPreferences_staff.getString(KEY_MOBILE_STAFF,null);


        if (mobno != null) {

            //if data is available then directly go on staff dashboard activity...
            Intent j = new Intent(this, TeacherDashboardActivity.class);
            startActivity(j);
        }
        ////firebase cloud messaging////

//        FirebaseMessaging.getInstance().getToken()
//                .addOnCompleteListener(new OnCompleteListener<String>() {
//                    @Override
//                    public void onComplete(@NonNull Task<String> task) {
//                        if (!task.isSuccessful()) {
//                           System.out.println("Fetching FCM registration token failed");
//                            return;
//                        }
//
//                        // Get new FCM registration token
//                        String token = task.getResult();
//
//                        // Log and toast
//                       System.out.println(token);
//                        Toast.makeText(MainActivity.this,"your device token"+ token, Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//






        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));

        ProgressDialog progress=new ProgressDialog(this);
        //initialization

        //String mobileN=getArguments().getString("mobNo");
        sharedPreferences =getSharedPreferences(SHARED_PREF_NAME_STD, Context.MODE_PRIVATE);
        String mobn = sharedPreferences.getString(KEY_MOBILE, null);

        if (mobn != null) {

            //if data is available then directly go on dashboard activity...
            Intent intent = new Intent(getApplicationContext(), Dashbord.class);
            startActivity(intent);
        }



        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navmain);//initialize bottom navigation bar
        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer1,new Homefragmentmain()).commit();//default as home fragment

        bottomNavigationView.setSelectedItemId(R.id.homebottommain);//default asa home fragment
        //selecting fragment from bottom navigation
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectedfragment=null;
                //selecting fragments by using item id
                switch (item.getItemId()){
                    case R.id.homebottommain:


                        selectedfragment=new Homefragmentmain();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer1,selectedfragment).commit();
                        break;
                    case R.id.gallarybottommain:
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer1,selectedfragment).commit();
                        break;
                    case R.id.summarybottommain://Activity fragment
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer1,selectedfragment).commit();
                        break;

                    case R.id.loginbottommain://profile fragment
                        //progress dialogue
                        selectedfragment=new Loginfragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.framecontainer1,selectedfragment).commit();

                        break;

                }


                return true;
            }
        });

    }

    protected void onDestroy() {//no internet connection
        super.onDestroy();//no internet connection
        unregisterReceiver(broadcastReceiver);//no internet connection
    }


}