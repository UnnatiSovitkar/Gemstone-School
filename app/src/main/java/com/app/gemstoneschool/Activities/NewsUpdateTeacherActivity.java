package com.app.gemstoneschool.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NewsUpdateTeacherActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece for profile fetch

    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_USERID_PROFILESTAFF="useridstaff_profile";

    DatePickerDialog datePickerDialogdetail;

    ProgressDialog progress;

    EditText e1, e2, e3;
    ImageView img1,clbt,rotimg;
    Button b1, b2,b3,datecal1;
    String encodeimg;
    Bitmap bitmap;
    Bitmap reducedbitmap;
    byte[] byteimg=null;
    String uid;


    BroadcastReceiver broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen

        setContentView(R.layout.activity_news_update_teacher);


        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));



        e1 = findViewById(R.id.t_ednoticeid);//news title
        e2 = findViewById(R.id.t_ednotice);//news title
        e3 = findViewById(R.id.des_ednotice);//news description
        datecal1=findViewById(R.id.dt_edit);//date picker button
        img1 = findViewById(R.id.img_edit);//image view
        rotimg = findViewById(R.id.imgrotate_ednotice);//image view

        b2 = findViewById(R.id.bt_ednotice);//upload button for uploading image to server
        clbt=findViewById(R.id.imgcl_ednotice);//cancel image


        initDatepicker();


        //share prefferece for staff profile detail
        sharedPreferences_staff_profile = getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        //user id is used for fetching api of news uploading
        uid=sharedPreferences_staff_profile.getString(KEY_USERID_PROFILESTAFF,null);


//        Toast.makeText(getApplicationContext(), getIntent().getExtras().getInt("id"), Toast.LENGTH_SHORT).show();
        e1.setText(getIntent().getExtras().getString("id"));
        Log.d("id_news",e1.getText().toString());

        e2.setText(getIntent().getExtras().getString("newsTitle"));
        e3.setText(getIntent().getExtras().getString("detail"));

        String dt=getIntent().getExtras().getString("dateformat");
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterOut = new SimpleDateFormat("dd-MM-yyyy");


        try {

            Date date = formatter.parse(dt);
            System.out.println(date);
            datecal1.setText(formatterOut.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        Glide.with(getApplicationContext())
                .load(getIntent().getExtras().getString("newsimg"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(img1);
        //datepicker function for selection of date




//        Toast.makeText(getContext(), uid, Toast.LENGTH_SHORT).show();

        ///on image click for selecting phto photo from camera or galllery

        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //function for selecting i,age
                selectImage();
            }
        });
///on cross image click for canceling phto photo from camera or galllery
        clbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img1.setImageResource(R.drawable.noimagefound);
            }
        });
//upload button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "welcome", Toast.LENGTH_SHORT).show();
                uploadBitmap(bitmap);

                progress=new ProgressDialog(getApplicationContext());
                progress.setMessage("Please Wait");
                progress.show();

            }
        });
////date picker button
        datecal1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                datePickerDialogdetail.show();

            }
        });

        rotimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                roateImage();
            }
        });

    }
    ///date picker function
    private void initDatepicker() {

        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month=month+1;
                String date=makeDateString(dayOfMonth,month,year);
                datecal1.setText(date);
            }

        };
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        int style= AlertDialog.THEME_HOLO_LIGHT;
        datePickerDialogdetail=new DatePickerDialog(getApplicationContext(),style,dateSetListener,year,month,day);
    }
///date picker function


    private String makeDateString(int dayOfMonth, int month, int year) {


        return getMonthFormat(month)+" "+dayOfMonth+" "+year;
    }
    ///date picker function
    ///date picker function
    private String getMonthFormat(int month) {
        if(month==1)
            return "JAN";
        if(month==2)
            return "FEB";
        if(month==3)
            return "MARCH";
        if(month==4)
            return "APRIL";
        if(month==5)
            return "MAY";
        if(month==6)
            return "JUNE";
        if(month==7)
            return "JULY";
        if(month==8)
            return "AUGUST";
        if(month==9)
            return "SEPT";
        if(month==10)
            return "OCT";
        if(month==11)
            return "NOV";
        if(month==12)
            return "DEC";

        return "JAN";
    }



    public void getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 50, byteArrayOutputStream);
//        uploadBitmap(bitmap);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        byteimg=byteArrayOutputStream.toByteArray();

//        return byteArrayOutputStream.toByteArray();
    }



    //selection of image from camera or gallery
    private void selectImage() {
        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo"))
                {
                    Intent icamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(icamera, 1);
                }
                else if (options[item].equals("Choose from Gallery"))
                {
                    Intent intent = new   Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, 2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
                img1.setImageBitmap(bitmap);
                getFileDataFromDrawable(bitmap);
//                uploadBitmap(bitmap);
                //encodeBitmapImg(bitmap);

            } else if (requestCode == 2) {
                //getting the image Uri
                Uri imageUri = data.getData();
                try {
                    //getting bitmap object from uri
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri);


                    //displaying selected image to imageview
                    img1.setImageBitmap(bitmap);
                    getFileDataFromDrawable(bitmap);

                    //calling the method uploadBitmap to upload image
//                    uploadBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
//        final String tags = editTextTags.getText().toString().trim();
        String url = "https://gemstonews.in/gemstoneerp/apis/teacher/send_notice.php";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            e1.setText("");
                            e2.setText("");
                            datecal1.setText("");
                            img1.setImageResource(R.drawable.noimagefound);
                            progress.dismiss();
                            Intent i=new Intent(getApplicationContext(), TeacherDashboardActivity.class);
                            startActivity(i);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("user_id",uid);
                params.put("news_title",e1.getText().toString().trim());
                params.put("news_desc",e2.getText().toString().trim());
                params.put("news_date",datecal1.getText().toString().trim());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
//                params.put("news_image", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                params.put("news_image", new DataPart(imagename + ".png", byteimg));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);
    }


    private void roateImage() {
        Matrix matrix = new Matrix();
        img1.setScaleType(ImageView.ScaleType.MATRIX); //required
        matrix.postRotate((float) 90, img1.getDrawable().getBounds().width()/2,img1.getDrawable().getBounds().height()/2);
        img1.setImageMatrix(matrix);
    }

    protected void onDestroy() {//no internet connection
        super.onDestroy();//no internet connection
        unregisterReceiver(broadcastReceiver);//no internet connection
    }
}