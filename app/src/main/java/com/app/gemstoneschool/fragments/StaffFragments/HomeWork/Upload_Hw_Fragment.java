package com.app.gemstoneschool.fragments.StaffFragments.HomeWork;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Activities.StaffHwActivity;
import com.app.gemstoneschool.Activities.TeacherDashboardActivity;
import com.app.gemstoneschool.Activities.VolleyMultipartRequest;
import com.app.gemstoneschool.Adapters.UploadHwAdaptar;
import com.app.gemstoneschool.Model.SelectbrdModel;
import com.app.gemstoneschool.Model.SelectclModel;
import com.app.gemstoneschool.Model.SelectyrModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Upload_Hw_Fragment extends Fragment {

    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece for profile fetch

    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_USERID_PROFILESTAFF="useridstaff_profile";
    String uid;

    private DatePickerDialog datePickerDialog;


    Spinner spincls,spinsub,spinbrd,spinyr;
    List<SelectclModel> cllist=new ArrayList<>();
    List<SelectbrdModel> brdlist=new ArrayList<>();
    List<SelectyrModel> yrlist=new ArrayList<>();


    ArrayAdapter<SelectclModel> clAdapter;
    ArrayAdapter<SelectbrdModel> brdAdapter;
    ArrayAdapter<SelectyrModel> yrAdapter;

    RequestQueue rq;

    ImageView up_image,simg,close_img;
    ArrayList<Bitmap> images;
    Bitmap bitmap;
    byte[] byteArray=null;
    UploadHwAdaptar adaptar;
    RelativeLayout rl;
    RecyclerView rv;
    EditText e1, e2,sub;
    Button uploadB,datecal;
    String clas,board,year;
    ProgressDialog progress;

    ArrayList<Uri> uri = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.upload_hw_fragment, container, false);



        rq=Volley.newRequestQueue(getContext());
        // Take the instance of Spinner and
        // apply OnItemSelectedListener on it which
        // tells which item of spinner is clicked
        spincls = v.findViewById(R.id.classspinner);
        spinbrd = v.findViewById(R.id.boardspinner);
        spinyr = v.findViewById(R.id.yrspinner);
        sub = v.findViewById(R.id.uphmsub);

        up_image = v.findViewById(R.id.upload_hw);
        uploadB = v.findViewById(R.id.uploadhw);
        datecal=v.findViewById(R.id.date_uphw);//date picker button
        simg = v.findViewById(R.id.img_uphw);
        close_img = v.findViewById(R.id.imgcl_uphw);


        rv=v.findViewById(R.id.multiimg_rv);
        rl=v.findViewById(R.id.rlayouthw);

        e1 = v.findViewById(R.id.uphmtitle);//news title
        e2 = v.findViewById(R.id.uphmdesc);//news description



//        /share prefferece for staff profile detail
        sharedPreferences_staff_profile =getActivity().getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        //user id is used for fetching api of news uploading
        uid=sharedPreferences_staff_profile.getString(KEY_USERID_PROFILESTAFF,null);


        //datepicker function for selection of date
        initDatepicker();

        adaptar=new UploadHwAdaptar(uri);
        rv.setLayoutManager(new GridLayoutManager(getContext(),3));

        // for class selection spinner
        spincls.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                SelectclModel selectclModel=(SelectclModel) spincls.getSelectedItem();
                clas=selectclModel.getId();
                Log.d("id", selectclModel.getId());

                Log.d("class", String.valueOf(selectclModel.getName()));

//                Toast.makeText(getContext(), spinc.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // for subject selection spinner




        // for board selection spinner

        spinbrd.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                SelectbrdModel selectbrdModel=(SelectbrdModel) spinbrd.getSelectedItem();
                board=selectbrdModel.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        // for year selection spinner

        spinyr.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {


                SelectyrModel selectyrModel=(SelectyrModel) spinyr.getSelectedItem();
                board=selectyrModel.getId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        up_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                selectImage();
                rl.setVisibility(View.GONE);
                simg.setImageBitmap(null);

            }


        });


        close_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                simg.setImageResource(R.drawable.noimagefound);
                rl.setVisibility(View.GONE);



            }


        });


        uploadB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    uploadBitmap();
                    progress=new ProgressDialog(getContext());
                    progress.setMessage("Please Wait");
                    progress.show();

            }
        });

        //date picker button
        datecal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.show();

            }
        });

        // get data form api
        getData();//for spinner api fetching

        //check permission for selection of multiple photos from gallery
        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);

        }
        return v;
    }

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
        builder.show(); }

    // get spinner data from api 03/09/2022
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
                    for(int j=0;j<jsonArray2.length();j++){
                        JSONObject jsonObject=jsonArray2.getJSONObject(j);
                        String boardn=jsonObject.optString("board_name");
                        String brdid=jsonObject.optString("board_id");

                        brdlist.add(new SelectbrdModel(boardn,brdid));
                        brdAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,brdlist);
                        brdAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinbrd.setAdapter(brdAdapter);

                    }

                    for(int k=0;k<jsonArray4.length();k++){
                        JSONObject jsonObject=jsonArray4.getJSONObject(k);
                        String yr=jsonObject.optString("ay_year");
                        String yrid=jsonObject.optString("ay_id");

                        yrlist.add(new SelectyrModel(yr,yrid));
                        yrAdapter=new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item,yrlist);
                        yrAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        spinyr.setAdapter(yrAdapter);

                    }


                        Log.d("ClassName",respObj.getString("class_name"));


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



        String date=makeDateString(day,month+1,year);
        Log.d("dtttt",date);
        datecal.setText(date);




        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


                month=month+1;
                String date=makeDateString(dayOfMonth,month,year);
                datecal.setText(date);
            }

        };
        /*
        Calendar cal=Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);


         */
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {

                Bitmap bitmap = (Bitmap) (data.getExtras().get("data"));
                rl.setVisibility(View.VISIBLE);
                simg.setImageBitmap(bitmap);
                getFileDataFromDrawableData(bitmap);
//                uploadBitmap(bitmap);
                //encodeBitmapImg(bitmap);
            } else if (requestCode == 2) {
                if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                int currentItem = 0;

                    Toast.makeText(getContext(), "Please select Single image", Toast.LENGTH_SHORT).show();

                    while (currentItem < count) {
                        Uri imageUri = data.getClipData().getItemAt(currentItem).getUri();
                        currentItem = currentItem + 1;
                        try {
                            uri.add(imageUri);
                            Log.d("FilePathURI", String.valueOf(imageUri));
                            Log.d("FilePathBitmap", String.valueOf(bitmap));

                            images.add(MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri));  // Code to handle multiple images
                            getFileDataFromDrawableData(bitmap);

                        } catch (Exception e) {
//                        Log.e( "File select error", e);
                        }
                    }

                rl.setVisibility(View.GONE);
                simg.setImageBitmap(null);
                rv.setAdapter(adaptar);
                adaptar.notifyDataSetChanged();

            } else if (data.getData() != null) {

                    final Uri uri = data.getData();

                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), uri);

                        Log.d("FilePathURI", String.valueOf(uri));
                        Log.d("FilePathBitmap", String.valueOf(bitmap));
                        getFileDataFromDrawableData(bitmap);
                        rl.setVisibility(View.VISIBLE);
                        simg.setImageBitmap(bitmap);


                    } catch (Exception e) {
//                    Log.e(TAG, "File select error", e);
                    }

                    rv.setAdapter(adaptar);
                    adaptar.notifyDataSetChanged();

                }
            }
        }

    }


    public void getFileDataFromDrawableData(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(bitmap.getByteCount());
        bitmap.compress(Bitmap.CompressFormat.JPEG, 60, byteArrayOutputStream);
        byteArray = byteArrayOutputStream.toByteArray();
//        bitmap.recycle();
    }



        public void uploadBitmap() {

            //getting the tag from the edittext
//        final String tags = editTextTags.getText().toString().trim();
            String url = "https://gemstonews.in/gemstoneerp/apis/teacher/send_homework.php";

            //our custom volley request
            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            try {
                                JSONObject obj = new JSONObject(new String(response.data));
                                Toast.makeText(getContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                                e1.setText("");
                                e2.setText("");
                                simg.setImageResource(R.drawable.noimagefound);
                                progress.dismiss();

                                Intent i=new Intent(getContext(), TeacherDashboardActivity.class);
                                startActivity(i);




                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
//                            Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    params.put("hw_title",e1.getText().toString().trim());
                    params.put("hw_description",e2.getText().toString().trim());
                    params.put("class_id", String.valueOf(clas));
                    params.put("hw_post_date",datecal.getText().toString().trim());
                    params.put("sub_id", sub.getText().toString().trim());
                    params.put("board_id", String.valueOf(board));
                    params.put("ay_id", String.valueOf(year));

//                    params.put("hw_material_file",datecal.getText().toString().trim());
                    return params;
                }

                /*
                 * Here we are passing image by renaming it with a unique name
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                    long imagename = System.currentTimeMillis();

                    /*
                    for (int i = 0; i < images.size(); i++) {

                        long imageName2 = System.currentTimeMillis();
                        params.put("images["+i+"]", new DataPart(imageName2 + ".jpg", getFileDataFromDrawableData(images.get(i))));
                    }
                    */
                    params.put("hw_material_file", new DataPart(imagename + ".png", byteArray));

                    return params;
                }
            };

            //adding the request to volley
            Volley.newRequestQueue(getContext()).add(volleyMultipartRequest);
        }
    }
