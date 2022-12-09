package com.app.gemstoneschool.fragments.StudentFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
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
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Mefragment extends Fragment {

    SharedPreferences sharedPreferences;
    //create a share preferences name and keys name

    //created 03-10-2022
    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String KEY_MOBILE="mobile";
    private static final String Key_SRID="srId";
    private static final String KEY_NAME="studName";
    private static final String KEY_CLS="studcls";
    private static final String KEY_SECTION="studsection";
    private static final String KEY_BOARD="studbrd";
    private static final String KEY_YEAR="studyr";
    private static final String Key_ADMID="admissionId";
    private static final String Key_INSTID="instituteId";


    TextView txtname,txtphone,txtcls,txtsect,txtbrd,txtyr,txtadmid,txtinstid;

    String stdname,mobileN,srid,cls,section,board,year,admid,instid;
    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_me,container,false);
        txtname=v.findViewById(R.id.namestd);
        txtphone=v.findViewById(R.id.mobstd);
        txtcls=v.findViewById(R.id.clsstd);
        txtsect=v.findViewById(R.id.sectionstd);
        txtbrd=v.findViewById(R.id.boardstd);
        txtyr=v.findViewById(R.id.yearstd);
        txtadmid=v.findViewById(R.id.admidstd);
        txtinstid=v.findViewById(R.id.instidstd);


        sharedPreferences= getActivity().getSharedPreferences(SHARED_PREF_NAME_STD,Context.MODE_PRIVATE);
        srid=sharedPreferences.getString(Key_SRID,null);
        System.out.println(srid);

        stdname=sharedPreferences.getString(KEY_NAME,null);
        txtname.setText(stdname);

        mobileN=sharedPreferences.getString(KEY_MOBILE,null);
        txtphone.setText(mobileN);
        System.out.println(mobileN);

        cls=sharedPreferences.getString(KEY_CLS,null);
        txtcls.setText(cls);

        section=sharedPreferences.getString(KEY_SECTION,null);
        txtsect.setText(section);

        board=sharedPreferences.getString(KEY_BOARD,null);
        txtbrd.setText(board);

        year=sharedPreferences.getString(KEY_YEAR,null);
        txtyr.setText(year+"-"+(Integer.parseInt(year)+1));

        admid=sharedPreferences.getString(Key_ADMID,null);
        txtadmid.setText(admid);

        instid=sharedPreferences.getString(Key_INSTID,null);
        txtinstid.setText(instid);



        return v;

    }


}
