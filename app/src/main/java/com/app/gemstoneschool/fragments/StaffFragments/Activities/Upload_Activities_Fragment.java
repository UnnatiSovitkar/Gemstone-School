package com.app.gemstoneschool.fragments.StaffFragments.Activities;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Activities.StaffActivityActivity;
import com.app.gemstoneschool.Activities.TeacherDashboardActivity;
import com.app.gemstoneschool.Activities.VolleyMultipartRequest;
import com.app.gemstoneschool.Adapters.UploadActivityAdaptar;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Upload_Activities_Fragment extends Fragment {


    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece for profile fetch

    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_USERID_PROFILESTAFF="useridstaff_profile";
    String uid;

    ProgressDialog progress;


    EditText title, desc;
    ImageView img,img_close,img_single;
    Button btn_upload;
    byte[] bytesofimage=null;
    Bitmap bitmap;
    UploadActivityAdaptar adaptar;
    ArrayList<Uri> uriList = new ArrayList<>();

    //declare by maddy
    List<Bitmap> imagesEncodedList;

    RecyclerView recyclerView;
    RelativeLayout rel_Layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.upload_activities_fragment,container,false);


        imagesEncodedList = new ArrayList<Bitmap>();

        title = v.findViewById(R.id.t_uactivity);//edit text
        desc = v.findViewById(R.id.desc_uactivity);//edit text
        img = v.findViewById(R.id.upload_act);//image view
        img_close= v.findViewById(R.id.imgcl_uactivity);//imageview
        btn_upload=v.findViewById(R.id.bt_uactivity);//button upload
        img_single = v.findViewById(R.id.img_uactivity);//image view


        recyclerView=v.findViewById(R.id.multiimg_rvact);
        rel_Layout=v.findViewById(R.id.rlayoutact);

        //seting adapter for multiple image displaying
        adaptar=new UploadActivityAdaptar(uriList);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),1));
        adaptar.setonItemSelectedListner(new UploadActivityAdaptar.OnItemClickListner() {
            @Override
            public void onItemClick(int position) {
                //now delete..
                uriList.remove(position);
                //then notify..
                adaptar.notifyItemRemoved(position);
            }
        });

        //share prefferece for staff profile detail
        sharedPreferences_staff_profile =getActivity().getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        //user id is used for fetching api of news uploading
        uid=sharedPreferences_staff_profile.getString(KEY_USERID_PROFILESTAFF,null);


        //selecting image from gallery
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                selectImage();//select image function
//                rel_Layout.setVisibility(View.GONE);
//                img_single.setImageBitmap(null);
            }
        });

        //check permission for selection of multiple photos from gallery
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);

        }

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                img_single.setImageDrawable(null);
                rel_Layout.setVisibility(View.GONE);

            }
        });
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(img_single.getDrawable()==null){
                    uploadData();
                }
                else {
                    uploadBitmap(bitmap);
                    
                }
                
                progress=new ProgressDialog(getContext());
                progress.setMessage("Please Wait");
                progress.show();            }
        });

       

        return v;
    }


//selecting images from camera or gallery
    private void selectImage() {

        final CharSequence[] options = { "Take Photo", "Choose from Gallery","Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == RESULT_OK) {//for single image camera
            if (requestCode == 1) {

                Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
                rel_Layout.setVisibility(View.VISIBLE);
                img_single.setImageBitmap(bitmap);
                getFileDataFromDrawable(bitmap);



            } else if (requestCode == 2) {//for multiimage
                //getting the image Uri
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;

                    Toast.makeText(getContext(), "Please select Single image", Toast.LENGTH_SHORT).show();

                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        currentItem = currentItem + 1;
                        try {
                            uriList.add(imageUri);
                            imagesEncodedList.add(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri));  // Code to handle multiple images
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
                        Log.d("imageUri2Bitmap", String.valueOf(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri2)));

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri2);//single image display
                        rel_Layout.setVisibility(View.VISIBLE);
                        img_single.setImageBitmap(bitmap);//single image display
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
        bytesofimage=byteArrayOutputStream.toByteArray();
        Log.d("img", String.valueOf(bytesofimage));
    }

//uploading image to server
    private void uploadBitmap(Bitmap bitmap) {

        String url = "https://gemstonews.in/gemstoneerp/apis/teacher/send_activity.php";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));

                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            progress.dismiss();
                            title.setText("");
                            desc.setText("");
                            img_single.setImageResource(R.drawable.noimagefound);

                            Intent i=new Intent(getContext(), StaffActivityActivity.class);
                            startActivity(i);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getContext(), "Multiple image error"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        if(error.getMessage().equals("java.lang.NullPointerException: Attempt to get length of null array")){
                            progress.dismiss();
                            Toast.makeText(getContext(), "Please Select Photo", Toast.LENGTH_LONG).show();
                        }
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
                params.put("activity_title",title.getText().toString().trim());
                params.put("activity_desc",desc.getText().toString().trim());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */




            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();

                long imagename = System.currentTimeMillis();
                params.put("activity_image", new DataPart(imagename + ".png", bytesofimage));

                return params;

            }

        };

        //adding the request to volley
        Volley.newRequestQueue(getActivity()).add(volleyMultipartRequest);
    }

//fetching api if image is not present
    private void uploadData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url = "https://gemstonews.in/gemstoneerp/apis/teacher/send_activity.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    progress.dismiss();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                    title.setText("");
                    desc.setText("");
                    img_single.setImageDrawable(null);

                    Intent i=new Intent(getContext(), TeacherDashboardActivity.class);
                    startActivity(i);

                    Log.d("message",msg);


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> parms=new HashMap<>();
                parms.put("user_id",uid);
                parms.put("activity_title",title.getText().toString().trim());
                parms.put("activity_desc",desc.getText().toString().trim());
                return parms;
            }
        };

        queue.add(request);

    }

}
