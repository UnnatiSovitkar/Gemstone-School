package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;

public class AboutSchoolActivity extends AppCompatActivity {
    Toolbar toolbar;

    BroadcastReceiver broadcastReceiver;//use to identify internet connection is on or off

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen
        setContentView(R.layout.activity_about_school);

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

        WebView webView=findViewById(R.id.webview);
        webView.loadUrl("https://gemstonews.in/app_about.php");
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //toolbar with back button

        toolbar=findViewById(R.id.toolbaraboutschool);
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

    //no internet connection
    protected void onDestroy() {//no internet connection
        super.onDestroy();//no internet connection
        unregisterReceiver(broadcastReceiver);//no internet connection
    }

}