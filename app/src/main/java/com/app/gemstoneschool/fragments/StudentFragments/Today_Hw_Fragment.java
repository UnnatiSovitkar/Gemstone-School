package com.app.gemstoneschool.fragments.StudentFragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Adapters.THWAdapter;
import com.app.gemstoneschool.Model.TodaysHwModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Today_Hw_Fragment extends Fragment {

    SharedPreferences sp;
    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String KEY_SERIALID="serialId";
    private static final String Key_SRID="srId";

    String srid;

    //api fetch data
    RecyclerView recyclerView;
    List<TodaysHwModel> t_hw_list = new ArrayList<>();
    THWAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.today_hw_fragment,container,false);

        Toast.makeText(getContext(), "welcome", Toast.LENGTH_SHORT).show();
        sp=getActivity().getSharedPreferences(SHARED_PREF_NAME_STD, Context.MODE_PRIVATE);//initialization of shareprefference1 object
        srid=sp.getString(Key_SRID,null);//save the content of KEY_name into string
//        t.setText(srid);

        Log.d("srid",srid);
        recyclerView = v.findViewById(R.id.rvtodhw);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getData();
        return v;
    }

    private void getData() {

        ////fetching homework data using api//
        RequestQueue queue= Volley.newRequestQueue(getContext());
        String url="https://gemstonews.in/gemstoneerp/apis/student/get_studtodayhwapi.php";


        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject jsonObject1=new JSONObject(response);
                    String message=jsonObject1.getString("todayhwmessage");

                    if(!(message.equalsIgnoreCase("No Todays Homework Record Found...!"))){
                    JSONArray jsonArray1=jsonObject1.getJSONArray("todayhw");
                        for(int i=0;i<jsonArray1.length();i++) {
                        JSONObject jsonObject_today = jsonArray1.getJSONObject(i);
                        t_hw_list.add(new TodaysHwModel(jsonObject_today.getInt("sr_no"),
                                jsonObject_today.getString("hw_subject"),
                                jsonObject_today.getString("hw_title"),
                                jsonObject_today.getString("hw_description"),
                                jsonObject_today.getString("hw_status"),
                                jsonObject_today.getString("hw_material_file")));

//                        Toast.makeText(getContext(), response.toString(), Toast.LENGTH_SHORT).show();
                    }


                    }else {
                        Toast.makeText(getContext(), "No Record Found...!", Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                adapter=new THWAdapter(getContext(),t_hw_list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();

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
                parms.put("sr_id",srid);

                return parms;
            }
        };

        queue.add(request);

    }
}
