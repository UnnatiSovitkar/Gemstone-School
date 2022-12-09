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
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.bumptech.glide.Glide;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewNewsDetailTeacherActivity extends AppCompatActivity {

    Toolbar toolbar1;

    TextView detailNews,dDate,newT;
    ImageView Nwpicture,Shrnws;
BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen

        setContentView(R.layout.activity_view_news_detail_teacher);


        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));




        detailNews=findViewById(R.id.newsteach);
        dDate=findViewById(R.id.newsdateteach);
        newT=findViewById(R.id.newstitleteach);
        Nwpicture=findViewById(R.id.imgnwsteach);//imge coming through api
        Shrnws=findViewById(R.id.shrnwsteach);//share news

        //toolbar
        toolbar1=findViewById(R.id.toolbarnewsteach);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        CleanerProperties props = new CleanerProperties();//removing html tags
//        props.setPruneTags("script");//removing html tags
//        String result = new HtmlCleaner(props).clean(getIntent().getExtras().getString("detail")).getText().toString();//removing html tags


        String result = Html.fromHtml(getIntent().getExtras().getString("detail")).toString();

        detailNews.setText(result);
        newT.setText(getIntent().getExtras().getString("newsTitle"));

        Glide.with(getApplicationContext())
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(Nwpicture);

        String dt=getIntent().getExtras().getString("dateformat");
//        dDate.setText(dt);

        Log.d("dssss",dt);

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterOut = new SimpleDateFormat("dd-MM-yyyy");


        try {

            Date date = formatter.parse(dt);
            System.out.println(date);
            dDate.setText(formatterOut.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
//        dDate.setText(getIntent().getExtras().getString("dateformat"));

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
                    Toast.makeText(ViewNewsDetailTeacherActivity.this, "Error occured", Toast.LENGTH_SHORT).show();

                }

            }
        });

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