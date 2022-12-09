package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.recyclerview.widget.RecyclerView;
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

import com.app.gemstoneschool.Adapters.EqCloseAdapter;
import com.app.gemstoneschool.Adapters.TabLayoutAdapter;
import com.app.gemstoneschool.Model.EqCloseModel;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.fragments.StaffFragments.Enquiry.CloseEnquiryFragment;
import com.app.gemstoneschool.fragments.StaffFragments.Enquiry.NewEnquiryFragment;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

public class StaffSendEnqActivity extends AppCompatActivity {

    BroadcastReceiver broadcastReceiver;
    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen

        setContentView(R.layout.activity_staff_send_enq);



        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        tabLayout = findViewById(R.id.layout_staff_neweq);
        viewPager = findViewById(R.id.vpager_staff_neweq);

        tabLayout.setupWithViewPager(viewPager);

        TabLayoutAdapter vpAdaptar = new TabLayoutAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdaptar.addfragment(new NewEnquiryFragment(), "New Enquiry");
        vpAdaptar.addfragment(new CloseEnquiryFragment(), "Close Enquiry");

        viewPager.setAdapter(vpAdaptar);

        //toolbar with back button

        toolbar=findViewById(R.id.toolbareq_list);
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
}