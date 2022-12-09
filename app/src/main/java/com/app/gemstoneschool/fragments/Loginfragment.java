package com.app.gemstoneschool.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Activities.TeacherDashboardActivity;
import com.app.gemstoneschool.Dashbord;
import com.app.gemstoneschool.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Loginfragment extends Fragment{

    //define //
    Button Erase;
    EditText MobNo,prno;
    RadioGroup rgb;
    RadioButton rb,rbstd,rbstaff;
    String prnNumber;

    SharedPreferences sharedPreferences;
    SharedPreferences sharedPreferences_staff;

    //SharedPreferences sp;

    //create a share preferences name and keys name for student
    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String KEY_MOBILE="mobile";
    private static final String Key_ERROR="message";
    private static final String Key_ADMID="admissionId";
    private static final String Key_SRID="srId";
    private static final String Key_INSTID="instituteId";
    private static final String KEY_NAME="studName";

    //create a share preferences name and keys name for staff
    private static final String SHARED_PREF_NAME_STAFF="myprefstaff";
    private static final String KEY_MOBILE_STAFF="mobilestaff";
    private static final String Key_ERROR_STAFF="messagestaff";



    String stdadmId,stdsrId,stdInstId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_login, container, false);

        Button Login = v.findViewById(R.id.login);
        Erase = v.findViewById(R.id.erase);
        MobNo = v.findViewById(R.id.mob_no);
        rgb = v.findViewById(R.id.loginrgb);
        rbstd=v.findViewById(R.id.studentrb);
        rbstaff=v.findViewById(R.id.teacherrb);

        prno = v.findViewById(R.id.prn_no);



        //share preffernce for student//
        sharedPreferences = getActivity().getSharedPreferences(SHARED_PREF_NAME_STD, Context.MODE_PRIVATE);

        //share prefernce for staff//
        sharedPreferences_staff = getActivity().getSharedPreferences(SHARED_PREF_NAME_STAFF, Context.MODE_PRIVATE);

        rbstd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prno.setVisibility(View.VISIBLE);
                Toast.makeText(getContext(), "welcome", Toast.LENGTH_SHORT).show();
            }
        });
        rbstaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                prno.setVisibility(View.GONE);
                Toast.makeText(getContext(), "welcome", Toast.LENGTH_SHORT).show();
            }
        });

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = MobNo.getText().toString();
                prnNumber=prno.getText().toString();
                Log.d("prnnumber",prnNumber);
                //selection of student or teacher option using radio button//
                int sid = rgb.getCheckedRadioButtonId();
                rb = (RadioButton) v.findViewById(sid);
                if (sid == -1) {
                    Toast.makeText(getActivity(), "Please select Option", Toast.LENGTH_LONG).show();
                } else {

                    boolean check = validateMobile(number);//validation of mob//
                    //checking validation//
                    if (check == true) {
                        //Toast.makeText(MainActivity.this,"Successfully Login", Toast.LENGTH_LONG).show();
                        if (rb.getText().equals("I am Student")) {//student radio button is selected

                            prno.setVisibility(View.VISIBLE);
                            //Toast.makeText(getActivity(), rb.getText(), Toast.LENGTH_SHORT).show();//showing selection


                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            String url = "https://gemstonews.in/gemstoneerp/apis/student/student_sendOtp.php";//Api//
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject respObj = new JSONObject(response);
                                        String errorMsg = respObj.getString("message");
                                        Log.d("msggg",errorMsg);
                                        //Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                                        if (errorMsg.equalsIgnoreCase("Mobile No is Valid")) {

                                            stdadmId=respObj.getString("adm_id");
                                            stdsrId=respObj.getString("sr_id");
                                            stdInstId=respObj.getString("inst_id");
                                            Log.d("srid",stdsrId);

                                            //put data on shared preference
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString(KEY_MOBILE, number);
                                            editor.putString(Key_ERROR,errorMsg);
                                            editor.putString(Key_ADMID,stdadmId);
                                            editor.putString(Key_SRID,stdsrId);
                                            editor.putString(Key_INSTID,stdInstId);
                                            Log.d("number",number);

                                            //Saving values to editor
                                            editor.commit();
                                            //Starting profile activity
                                            Intent intent = new Intent(getActivity(), Dashbord.class);
                                            Toast.makeText(getContext(), "Successfully Login", Toast.LENGTH_LONG).show();

                                            startActivity(intent);
                                        } else {
                                            //If the server response is not success
                                            //Displaying an error message on toast
                                            Toast.makeText(getContext(), "Invalid Mobile number", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException jsonException) {
                                        jsonException.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    try {

                                        JSONObject respObj = new JSONObject(String.valueOf(error));

                                        String errorMsg = respObj.getString("message");
                                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //Toast.makeText(MainActivity.this, "error.getMessage()", Toast.LENGTH_LONG).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> parms = new HashMap<>();

                                    parms.put("number", number);
                                    parms.put("prnno", prnNumber);

                                    return parms;
                                }
                            };
                            //Adding the string request to the queue
                            queue.add(request);

                            //teacher login api fetch here
                        } else if (rb.getText().equals("I am Staff")) {//teacher is selected


                            RequestQueue queue = Volley.newRequestQueue(getContext());
                            String url = "https://gemstonews.in/gemstoneerp/apis/teacher/teacher_sendOtp.php";//Api//
                            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {

                                        JSONObject respObj = new JSONObject(response);
                                        String errorMsg = respObj.getString("message");
                                        //Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();
                                        if (errorMsg.equalsIgnoreCase("Mobile No is Valid")) {

                                            //put staff data on shared preference
                                            SharedPreferences.Editor editor_staff = sharedPreferences_staff.edit();
                                            editor_staff.putString(KEY_MOBILE_STAFF, number);
                                            editor_staff.putString(Key_ERROR_STAFF,errorMsg);


                                            //Saving values to editor
                                            editor_staff.commit();
                                            //Starting profile activity
                                            Intent intent = new Intent(getActivity(), TeacherDashboardActivity.class);
                                            Toast.makeText(getContext(), "Successfully Login", Toast.LENGTH_LONG).show();

                                            startActivity(intent);
                                        } else {
                                            //If the server response is not success
                                            //Displaying an error message on toast
                                            Toast.makeText(getContext(), "Invalid Mobile number", Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (JSONException jsonException) {
                                        jsonException.printStackTrace();
                                    }


                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    try {

                                        JSONObject respObj = new JSONObject(String.valueOf(error));

                                        String errorMsg = respObj.getString("message");
                                        Toast.makeText(getContext(), errorMsg, Toast.LENGTH_LONG).show();


                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    //Toast.makeText(MainActivity.this, "error.getMessage()", Toast.LENGTH_LONG).show();
                                }
                            }) {
                                @Override
                                protected Map<String, String> getParams() throws AuthFailureError {

                                    Map<String, String> parms = new HashMap<>();

                                    parms.put("number", number);


                                    return parms;
                                }
                            };
                            //Adding the string request to the queue
                            queue.add(request);






//                            Toast.makeText(getContext(), rb.getText(), Toast.LENGTH_LONG).show();
//                            Intent intent=new Intent(getContext(), TeacherDashboardActivity.class);
//                            startActivity(intent);

                        }

                    }

                }

            }

        });
        //erasing data entered in mobile number edit text
        Erase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MobNo.getText().clear();//clear edit text
            }
        });

        return v;
    }



    //validation of mob no//
    private boolean validateMobile(String number){//validation of mob no
        if(number.length()==0){//validation of mob no
            MobNo.requestFocus();//validation of mob no
            MobNo.setError("field cannot be empty");//validation of mob no
            return false;//validation of mob no
        }else if(!number.matches("^[6-9][0-9]{9}$")) {//validation of mob no
            MobNo.requestFocus();//validation of mob no
            MobNo.setError("Invalid Mobile No");//validation of mob no
            return false;//validation of mob no
        }//validation of mob no
        else {//validation of mob no
            return true;//validation of mob no
        }//validation of mob no
    }



}







