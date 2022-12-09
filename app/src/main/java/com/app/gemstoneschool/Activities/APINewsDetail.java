package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gemstoneschool.Dashbord;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.bumptech.glide.Glide;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class APINewsDetail extends AppCompatActivity {
    Toolbar toolbar1;//defining toolbar

    TextView detailNews,dDate,newT;//defining textview
    ImageView Nwpicture,Shrnws;//defining imageview

    BroadcastReceiver broadcastReceiver;//used for inter connection


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen

        setContentView(R.layout.activity_apinews_detail);

        detailNews=findViewById(R.id.news);//initialize detail news
        dDate=findViewById(R.id.newsdate);//initialize date textview
        newT=findViewById(R.id.newstitle);//initialize title textview
        Nwpicture=findViewById(R.id.imgnws);//initialize image view for displaying img
        Shrnws=findViewById(R.id.shrnws);//share news

        //toolbar
        toolbar1=findViewById(R.id.toolbarnews);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String result = Html.fromHtml(getIntent().getExtras().getString("detail")).toString();

        detailNews.setText(result);//seting textview with data coming from newsadapter
        newT.setText(getIntent().getExtras().getString("newsTitle"));//seting textview with data coming from newsadapter


        //image displaying on img view
        Glide.with(getApplicationContext())
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(Nwpicture);

        dDate.setText(getIntent().getExtras().getString("dateformat"));
        String dt=getIntent().getExtras().getString("dateformat");//seting textview with data coming from newsadapter
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");//date format of date fetching from api
        SimpleDateFormat formatterOut = new SimpleDateFormat("dd-MM-yyyy");//we wand to diplay date in this format


        //formating date into desire one
        try {

            Date date = formatter.parse(dt);
            System.out.println(date);
            dDate.setText(formatterOut.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //dDate.setText(getIntent().getExtras().getString("dateformat"));

        //sharing news
        Shrnws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //share option for sharing app
                try {

                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Demo Share");
                    String sharemsg=getIntent().getExtras().getString("newsTitle")+"\n\n"+getIntent().getExtras().getString("detail");
                    intent.putExtra(Intent.EXTRA_TEXT,sharemsg);
                    startActivity(Intent.createChooser(intent,"share by"));

                }catch (Exception e){
                    Toast.makeText(APINewsDetail.this, "Error occured", Toast.LENGTH_SHORT).show();

                }
            }
        });


        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


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