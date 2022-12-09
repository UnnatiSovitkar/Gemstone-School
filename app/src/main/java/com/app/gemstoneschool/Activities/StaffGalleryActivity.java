package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.app.gemstoneschool.Adapters.TabLayoutAdapter;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.fragments.StaffFragments.Activities.Upload_Activities_Fragment;
import com.app.gemstoneschool.fragments.StaffFragments.Activities.View_Activities_Fragment;
import com.app.gemstoneschool.fragments.StaffFragments.Gallery.Upload_Gallery_Fragment;
import com.app.gemstoneschool.fragments.StaffFragments.Gallery.View_Gallery_Fragment;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.google.android.material.tabs.TabLayout;

public class StaffGalleryActivity extends AppCompatActivity {

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


        setContentView(R.layout.activity_staff_gallery);


        ///tollbar bar with back button//////
        toolbar = findViewById(R.id.toolbar_staff_gallery);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
/////////tab layout functioning//////
        tabLayout = findViewById(R.id.tlayout_staff_gallery);
        viewPager = findViewById(R.id.vpager_staff_gallery);

        tabLayout.setupWithViewPager(viewPager);

        TabLayoutAdapter vpAdaptar = new TabLayoutAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        vpAdaptar.addfragment(new Upload_Gallery_Fragment(), "Upload Gallery");
        vpAdaptar.addfragment(new View_Gallery_Fragment(), "View Gallery");

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