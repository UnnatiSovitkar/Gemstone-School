package com.app.gemstoneschool.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Dashbord;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.service.NetworkBroadcast;
import com.bumptech.glide.Glide;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TodayHWDetails extends AppCompatActivity {

    Toolbar toolbar1;

    TextView title,description,status;
    ImageView img,uploadimage,imgselect,closeImg;
    Button cameraopen,upload;

    String hw_id,srid;
    byte[] bytesofimage=null;
    ArrayList<Uri> uriList = new ArrayList<>();
    List<Bitmap> imagesEncodedList;
    Bitmap bitmap;
    RelativeLayout rel_Layout;
    int CAMERA_REQ_CODE=100;
    BroadcastReceiver broadcastReceiver;

    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //disable screenshot
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//full screen
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);//full screen

        setContentView(R.layout.activity_today_hwdetails);


        title=findViewById(R.id.dthwtitle);
        status=findViewById(R.id.thwdstatus);
        description=findViewById(R.id.thwdesc);
        uploadimage=findViewById(R.id.imgcameratd);//imge for capturing
        imgselect = findViewById(R.id.imgselecttoday);
        upload = findViewById(R.id.btncameratd);
        closeImg = findViewById(R.id.clsimgtd);
        rel_Layout=findViewById(R.id.relhwstdtoday);
        img=findViewById(R.id.thwimg);//imge coming through api

        //no internet connection
        broadcastReceiver=new NetworkBroadcast();
        registerReceiver(broadcastReceiver,new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));


        Glide.with(getApplicationContext())
                .load(getIntent().getExtras().getString("thwimage"))
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(img);

        //toolbar
        toolbar1=findViewById(R.id.toolbartodayhwdetails);
        setSupportActionBar(toolbar1);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        title.setText(getIntent().getExtras().getString("title"));
        status.setText(getIntent().getExtras().getString("status"));
        description.setText(getIntent().getExtras().getString("detaildesc"));

        imgselect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
            }
        });
        closeImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadimage.setImageDrawable(null);
                rel_Layout.setVisibility(View.GONE);

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadBitmap(bitmap);
                progress=new ProgressDialog(TodayHWDetails.this);
                progress.setMessage("Please Wait");
                progress.show();
            }
        });

    }

    //captured image saved in imageview//
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//for single image camera
            if (requestCode == 1) {

                Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
                rel_Layout.setVisibility(View.VISIBLE);
                uploadimage.setImageBitmap(bitmap);
                getFileDataFromDrawable(bitmap);


            } else if (requestCode == 2) {//for multiimage
                //getting the image Uri
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;

                    Toast.makeText(getApplicationContext(), "Please select Single image", Toast.LENGTH_SHORT).show();

                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        currentItem = currentItem + 1;
                        try {
                            uriList.add(imageUri);
                            imagesEncodedList.add(MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imageUri));  // Code to handle multiple images
//                            encodeBitmapImg(bitmap);

                            Log.d("imageUri", String.valueOf(imageUri));

                        } catch (Exception e) {
//                        Log.e( "File select error", e);
                        }
                    }

                } else if (data.getData() != null) {//for single img gallery

                    final Uri imageUri2 = data.getData();

                    try {

                        uriList.add(imageUri2);//for multiimage
                        Log.d("imageUri2", String.valueOf(imageUri2));//for multiimage
                        Log.d("imageUri2Bitmap", String.valueOf(MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri2)));

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri2);//single image display
                        rel_Layout.setVisibility(View.VISIBLE);
                        uploadimage.setImageBitmap(bitmap);//single image display
                        getFileDataFromDrawable(bitmap);


                    } catch (Exception e) {
//                    Log.e(TAG, "File select error", e);
                    }

                }
            }
        }
    }

    public void getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        bytesofimage = byteArrayOutputStream.toByteArray();
        Log.d("img", String.valueOf(bytesofimage));
    }

    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(TodayHWDetails.this);
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
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {

                        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                    }
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "select picture"),2);
                }
                else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    private void uploadBitmap(Bitmap bitmap) {

        String url = "https://gemstonews.in/gemstoneerp/apis/student/send_homework_by_student.php";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            img.setImageDrawable(null);

                            Intent i=new Intent(getApplicationContext(), Dashbord.class);
                            startActivity(i);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Multiple image error"+error.getMessage(), Toast.LENGTH_SHORT).show();

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
                params.put("sr_id",srid);
                params.put("hwa_id",hw_id);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */




            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                long imagename = System.currentTimeMillis();
                params.put("hw_image", new DataPart(imagename + ".png", bytesofimage));

                return params;

            }

        };

        //adding the request to volley
        Volley.newRequestQueue(getApplicationContext()).add(volleyMultipartRequest);
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