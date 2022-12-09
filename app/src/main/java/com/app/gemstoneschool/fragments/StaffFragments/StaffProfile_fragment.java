package com.app.gemstoneschool.fragments.StaffFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class StaffProfile_fragment extends Fragment {

    SharedPreferences sp;
    //create a share preferences name and keys name

    SharedPreferences sharedPreferences_staff_profile;//staff shareprefferece

    //create a share preferences name and keys name for staff profile details
    private static final String SHARED_PREF_NAME_PROFILESTAFF="myprefstaff_profile";
    private static final String KEY_NAME_PROFILESTAFF="namestaff_profile";
    private static final String KEY_INSTID_PROFILESTAFF="instidstaff_profile";
    private static final String KEY_USERID_PROFILESTAFF="useridstaff_profile";
    private static final String KEY_MOBILE_PROFILESTAFF="mobilestaff_profile";
    private static final String KEY_ADDRESS_PROFILESTAFF="addressstaff_profile";

    @Nullable
    @Override

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_staffprofile,container,false);



//        sp= getActivity().getSharedPreferences(SHARED_PREF_NAME1,Context.MODE_PRIVATE);

        TextView mobile=v.findViewById(R.id.staff_phn);
        TextView insdn=v.findViewById(R.id.id_staff);
        TextView userid=v.findViewById(R.id.id_staffid);
        TextView addr=v.findViewById(R.id.staff_adr);
        TextView name=v.findViewById(R.id.staff_n);


        sharedPreferences_staff_profile =getActivity().getSharedPreferences(SHARED_PREF_NAME_PROFILESTAFF, Context.MODE_PRIVATE);
        name.setText(sharedPreferences_staff_profile.getString(KEY_NAME_PROFILESTAFF,null));
        insdn.setText(sharedPreferences_staff_profile.getString(KEY_INSTID_PROFILESTAFF,null));
        userid.setText(sharedPreferences_staff_profile.getString(KEY_USERID_PROFILESTAFF,null));
        mobile.setText(sharedPreferences_staff_profile.getString(KEY_MOBILE_PROFILESTAFF,null));
        addr.setText(sharedPreferences_staff_profile.getString(KEY_ADDRESS_PROFILESTAFF,null));

        return v;

    }


}
