package com.app.gemstoneschool.fragments.StaffFragments.Notice;

import static android.app.Activity.RESULT_OK;

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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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
import com.app.gemstoneschool.Activities.StaffNoticeActivity;
import com.app.gemstoneschool.Activities.TeacherDashboardActivity;
import com.app.gemstoneschool.Activities.VolleyMultipartRequest;
import com.app.gemstoneschool.Model.SelectbrdModel;
import com.app.gemstoneschool.Model.SelectclModel;
import com.app.gemstoneschool.Model.SelectyrModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Upload_Notice_Fragment extends Fragment {

    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece for profile fetch

    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_USERID_PROFILESTAFF="useridstaff_profile";
    private static final String KEY_INSTID_PROFILESTAFF="instidstaff_profile";

    private DatePickerDialog datePickerDialog;
    ProgressDialog progress;

    int angle = 0;

    EditText e1, e2, e3;
    ImageView img,clbt,rotimg,img_notice;
    Button b1, b2,b3,datecal;
    String encodeimg,cls,instId;
    Bitmap bitmap;
    Bitmap reducedbitmap;
    byte[] byteimg=null;
    String uid;

    ArrayList<Uri> uri = new ArrayList<>();
    ArrayList<Bitmap> images;

    RelativeLayout rl;

    List<SelectclModel> cllist=new ArrayList<>();
    ArrayAdapter<SelectclModel> clAdapter;
    Spinner spincls;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.upload_notice_fragment, container, false);

        e1 = v.findViewById(R.id.t_upnotice);//news title
        e2 = v.findViewById(R.id.des_upnotice);//news description
        datecal=v.findViewById(R.id.date_upnotice);//date picker button
        img = v.findViewById(R.id.img_upnotice);//image view
        img_notice = v.findViewById(R.id.upload_notice);//image view
        rl=v.findViewById(R.id.deltaRelative_notice);
        spincls = v.findViewById(R.id.classspinnernotice);

        b2 = v.findViewById(R.id.bt_upnotice);//upload button for uploading image to server
        clbt=v.findViewById(R.id.imgcl_upnotice);//cancel image

        //datepicker function for selection of date
        initDatepicker();


        //spinner for selecting class
        // for board selection spinner

        spincls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                SelectclModel selectclModel=(SelectclModel) spincls.getSelectedItem();
                cls=selectclModel.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //share prefferece for staff profile detail
        sharedPreferences_staff_profile =getActivity().getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        //user id is used for fetching api of news uploading
        uid=sharedPreferences_staff_profile.getString(KEY_USERID_PROFILESTAFF,null);
        instId=sharedPreferences_staff_profile.getString(KEY_INSTID_PROFILESTAFF,null);



//        Toast.makeText(getContext(), uid, Toast.LENGTH_SHORT).show();

        ///on image click for selecting phto photo from camera or galllery

        img_notice.setOnClickListener(new View.OnClickListener() {
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

                img.setImageDrawable(null);
                rl.setVisibility(View.GONE);
            }
        });


//upload button
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getContext(), "welcome", Toast.LENGTH_SHORT).show();


                if(img.getDrawable()==null){
                    uploadData();
                    progress=new ProgressDialog(getContext());
                    progress.setMessage("Please Wait");
                    progress.show();
                }
                else {
                    uploadBitmap(bitmap);
                    progress=new ProgressDialog(getContext());
                    progress.setMessage("Please Wait");
                    progress.show();

                }


            }
        });
////date picker button
        datecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });

//        rotimg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                roateImage();
//            }
//        });
        getData();//for spinner api fetching

        return v;
    }

    //spring date
    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/teacher/get_coursedetails.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);
                    JSONArray jsonArray=respObj.getJSONArray("class_details");
                    JSONArray jsonArray2=respObj.getJSONArray("board_details");
                    JSONArray jsonArray3=respObj.getJSONArray("sec_details");
                    JSONArray jsonArray4=respObj.getJSONArray("ay_details");

                    Log.d("class details",jsonArray.toString());
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject=jsonArray.getJSONObject(i);
                        String classn=jsonObject.optString("class_name");
                        String clid=jsonObject.optString("class_id");

                        cllist.add(new SelectclModel(classn,clid));
//                        Log.d("s", String.valueOf(cllist));
                        clAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,cllist);
                        clAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spincls.setAdapter(clAdapter);


                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("get_course","0");



                return params;
            }
        };

        queue.add(request);
    }

    ///date picker function
    private void initDatepicker() {

        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);

        String date=makeDateString(day,month,year);
        datecal.setText(date);

        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month=month+1;
                String date=makeDateString(dayOfMonth,month,year);
                datecal.setText(date);
            }

        };

        int style= AlertDialog.THEME_DEVICE_DEFAULT_LIGHT;
        datePickerDialog=new DatePickerDialog(getContext(),style,dateSetListener,year,month,day);
    }
///date picker function

    private String makeDateString(int dayOfMonth, int month, int year) {


        int mth= month;
        String fm=""+mth;
        String fd=""+dayOfMonth;
        if(mth<10){
            fm ="0"+mth;
        }
        if (dayOfMonth<10){
            fd="0"+dayOfMonth;
        }
        String date= fd+"-"+fm+"-"+year;
        return date;
    }
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
    ///this function is used for converting bitmap image to encoded form for sending to server




    public void getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
//        uploadBitmap(bitmap);
        Bitmap decoded = BitmapFactory.decodeStream(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
        byteimg=byteArrayOutputStream.toByteArray();
        Log.d("img", String.valueOf(byteimg));


//        return byteArrayOutputStream.toByteArray();
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
                rl.setVisibility(View.VISIBLE);
                img.setImageBitmap(bitmap);
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

                    /*
                    rl.setVisibility(View.GONE);
                    simg.setImageBitmap(null);
                    rv.setAdapter(adaptar);
                    adaptar.notifyDataSetChanged();

                     */
                } else if (data.getData() != null) {

                    final Uri imageUri = data.getData();
                    try {
                        //getting bitmap object from uri
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), imageUri);
                        img.setImageBitmap(bitmap);
                        rl.setVisibility(View.VISIBLE);
                        //displaying selected image to imageview
//                    img.setImageBitmap(bitmap);
                        getFileDataFromDrawable(bitmap);

                        /*
                        rl.setVisibility(View.VISIBLE);
                        simg.setImageBitmap(bitmap);

                         */


                    } catch (Exception e) {
//                    Log.e(TAG, "File select error", e);
                    }

                    /*
                    rv.setAdapter(adaptar);
                    adaptar.notifyDataSetChanged();


                     */

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
                            Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
//                            Log.d("hhhh",obj.getString("message"));
                            e1.setText("");
                            e2.setText("");
                            datecal.setText("");
                            img.setImageDrawable(null);
                            progress.dismiss();

                            Intent i=new Intent(getContext(), StaffNoticeActivity.class);
                            startActivity(i);




                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("tttt",error.getMessage());

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
                params.put("class_id",cls);
                params.put("inst_id",instId);
                params.put("user_id",uid);
                params.put("news_title",e1.getText().toString().trim());
                params.put("news_desc",e2.getText().toString().trim());
                params.put("news_date",datecal.getText().toString().trim());
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

//                Log.d("hhhh",String msg,Throwable tr);
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
    }

//fetching api if image is not present

    private void uploadData() {

        ////fetching fees data using api//
        RequestQueue queue= Volley.newRequestQueue(getActivity());
        String url = "https://gemstonews.in/gemstoneerp/apis/teacher/send_notice.php";

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject=new JSONObject(response);
                    String msg=jsonObject.getString("message");

                    progress.dismiss();
                    Toast.makeText(getContext(), msg, Toast.LENGTH_SHORT).show();

                    Log.d("message",msg);

                    e1.setText("");
                    e2.setText("");
                    datecal.setText("");
//                            img.setImageResource(R.drawable.noimagefound);
                    progress.dismiss();

                    Intent i=new Intent(getContext(), TeacherDashboardActivity.class);
                    startActivity(i);

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

                Map<String,String> params=new HashMap<>();
                params.put("class_id",cls);
                params.put("inst_id",instId);
                params.put("user_id",uid);
                params.put("news_title",e1.getText().toString().trim());
                params.put("news_desc",e2.getText().toString().trim());
                params.put("news_date",datecal.getText().toString().trim());
                return params;
            }
        };

        queue.add(request);

    }

}



