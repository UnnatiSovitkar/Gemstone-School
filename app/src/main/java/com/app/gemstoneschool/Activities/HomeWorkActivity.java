package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;

import com.app.gemstoneschool.Adapters.TabLayoutAdapter;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.fragments.StudentFragments.Previous_Hw_Fragment;
import com.app.gemstoneschool.fragments.StudentFragments.Today_Hw_Fragment;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.google.android.material.tabs.TabLayout;


public class HomeWorkActivity extends AppCompatActivity {

    Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);


        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        ///tollbar bar with back button//////
        toolbar = findViewById(R.id.featuredcoursestoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
/////////tab layout functioning//////
        tabLayout = findViewById(R.id.tlayout);
        viewPager = findViewById(R.id.vpager);

        tabLayout.setupWithViewPager(viewPager);

        TabLayoutAdapter vpAdaptar = new TabLayoutAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdaptar.addfragment(new Today_Hw_Fragment(), "Today's Homework");
        vpAdaptar.addfragment(new Previous_Hw_Fragment(), "Previous Homework");

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