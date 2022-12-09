package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.app.gemstoneschool.Adapters.TabLayoutAdapter;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.fragments.StaffFragments.Attendence.Upload_Attendence_Fragment;
import com.app.gemstoneschool.fragments.StaffFragments.Attendence.View_Attendence_Fragment;
import com.app.gemstoneschool.fragments.StudentFragments.Previous_Hw_Fragment;
import com.app.gemstoneschool.fragments.StudentFragments.Today_Hw_Fragment;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.google.android.material.tabs.TabLayout;

public class StaffAttActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen


        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        ProgressDialog progressDialog=new ProgressDialog(this);//progress dialouge initialization

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

        setContentView(R.layout.activity_staff_att);

        ///tollbar bar with back button//////
        toolbar = findViewById(R.id.toolbar_staff_att);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
/////////tab layout functioning//////
        tabLayout = findViewById(R.id.tlayout_staff_att);
        viewPager = findViewById(R.id.vpager_staff_att);

        tabLayout.setupWithViewPager(viewPager);

        TabLayoutAdapter vpAdaptar = new TabLayoutAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdaptar.addfragment(new Upload_Attendence_Fragment(), "Upload Attendence");
        vpAdaptar.addfragment(new View_Attendence_Fragment(), "View Attendence");

        viewPager.setAdapter(vpAdaptar);

    }
    ////for back to home////
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