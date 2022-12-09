package com.app.gemstoneschool.fragments.StaffFragments.Gallery;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Activities.StaffGalleryActivity;
import com.app.gemstoneschool.Activities.TeacherDashboardActivity;
import com.app.gemstoneschool.Activities.VolleyMultipartRequest;
import com.app.gemstoneschool.R;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Upload_Gallery_Fragment extends Fragment {

    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece for profile fetch

    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_USERID_PROFILESTAFF="useridstaff_profile";

    private DatePickerDialog datePickerDialog;

    ProgressDialog progress;

    int angle = 0;

    ImageView img,clbt,rotimg,img_gallery;
    Button b1, b2,b3,datecal;
    String encodeimg;
    Bitmap bitmap;
    Bitmap reducedbitmap;
    byte[] byteimg=null;
    String uid;

    ArrayList<Uri> uri = new ArrayList<>();
    ArrayList<Bitmap> images;






    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.upload_gallery_fragment,container,false);



        img = v.findViewById(R.id.upload_gallery);//image view
        img_gallery = v.findViewById(R.id.img_upgallery);//image view



        b2 = v.findViewById(R.id.btimg_upgallery);//upload button for uploading image to server
        clbt=v.findViewById(R.id.imgcl_upgallery);//cancel image

        //share prefferece for staff profile detail
        sharedPreferences_staff_profile =getActivity().getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        //user id is used for fetching api of news uploading
        uid=sharedPreferences_staff_profile.getString(KEY_USERID_PROFILESTAFF,null);

        ///on image click for selecting phto photo from camera or galllery

        img.setOnClickListener(new View.OnClickListener() {
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

                img_gallery.setImageResource(R.drawable.noimagefound);
            }
        });


//upload button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "welcome", Toast.LENGTH_SHORT).show();
                uploadBitmap(bitmap);

                progress=new ProgressDialog(getContext());
                progress.setMessage("Please Wait");
                progress.show();

            }
        });
        return v;
    }



    //selection of image from camera or gallery
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
                img_gallery.setImageBitmap(bitmap);
                getFileDataFromDrawable(bitmap);
//                uploadBitmap(bitmap);
                //encodeBitmapImg(bitmap);

            } else if (requestCode == 2) {

                //getting the image Uri
                if (data.getClipData() != null) {
                    int count = data.getClipData().getItemCount();
                    int currentItem = 0;

                    Toast.makeText(getContext(), "Please select Single image", Toast.LENGTH_SHORT).show();

                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        currentItem = currentItem + 1;
                        try {
                            uri.add(imageUri);
                            images.add(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri));  // Code to handle multiple images


                        } catch (Exception e) {
//                        Log.e( "File select error", e);
                        }
                    }

                } else if (data.getData() != null) {

                    final Uri imageUri = data.getData();
                    try {
                        //getting bitmap object from uri
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                        img_gallery.setImageBitmap(bitmap);
                        //displaying selected image to imageview
                        getFileDataFromDrawable(bitmap);




                    } catch (Exception e) {
                    }



                }
            }

        }
    }


    public void getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        uploadBitmap(bitmap);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        byteimg=byteArrayOutputStream.toByteArray();
        Log.d("img", String.valueOf(byteimg));


//        return byteArrayOutputStream.toByteArray();
    }
    public void uploadBitmap(final Bitmap bitmap) {

        //getting the tag from the edittext
        String url = "https://gemstonews.in/gemstoneerp/apis/teacher/send_gallery.php";

        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                            img_gallery.setImageResource(R.drawable.noimagefound);
                            progress.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                params.put("gal_image", new DataPart(imagename + ".png", byteimg));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }

}
