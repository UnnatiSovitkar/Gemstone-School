package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.bumptech.glide.Glide;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

public class DetailHomeWork extends AppCompatActivity {
    Toolbar toolbar;

    TextView yr,brd,section,clas,subject,title,description,date;
    ImageView img;

    BroadcastReceiver broadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen

        setContentView(R.layout.activity_detail_home_work);

        yr=findViewById(R.id.yr_detail);//textview
        brd=findViewById(R.id.board_detail);//textview
        section=findViewById(R.id.sec_detail);//textview
        clas=findViewById(R.id.cls_detail);//textview
        subject=findViewById(R.id.sub_detail);//textview
        title=findViewById(R.id.ttl_detail);//textview
        description=findViewById(R.id.desc_detail);//textview
        date=findViewById(R.id.dt_detail);//textview

        img=findViewById(R.id.imghwdetail);//img view




        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        //toolbar
        toolbar=findViewById(R.id.toolbar_hw_details);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CleanerProperties props = new CleanerProperties();//removing html tags
        props.setPruneTags("script");//removing html tags
        String result = new HtmlCleaner(props).clean(getIntent().getExtras().getString("desc")).getText().toString();//removing html tags




        //fetching bundle data from adapter
        yr.setText(getIntent().getExtras().getString("year"));
        brd.setText(getIntent().getExtras().getString("board"));
        section.setText(getIntent().getExtras().getString("section"));
        clas.setText(getIntent().getExtras().getString("clas"));
        subject.setText(getIntent().getExtras().getString("sub"));
        title.setText(getIntent().getExtras().getString("title"));
        description.setText(result);
        date.setText(getIntent().getExtras().getString("date"));

        //setting image to image view
        Glide.with(getApplicationContext())
                .load(getIntent().getExtras().getString("img"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(img);


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