package com.app.gemstoneschool.fragments.StaffFragments.Feedback;

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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Adapters.EqCloseAdapter;
import com.app.gemstoneschool.Adapters.FdNewAdapter;
import com.app.gemstoneschool.Adapters.FdOldAdapter;
import com.app.gemstoneschool.Model.EqCloseModel;
import com.app.gemstoneschool.Model.FdNewModel;
import com.app.gemstoneschool.Model.FdOldModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class OldFdFragment extends Fragment {
    RecyclerView recyclerView;
    List<FdOldModel> list = new ArrayList<>();
    FdOldAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.old_feedback_fragment_staff,container,false);


        //api fetch data

        recyclerView = view.findViewById(R.id.recycler_staff_old_feedback);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getData();
        return view;
    }




    private void getData() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://gemstonews.in/gemstoneerp/apis/teacher/get_feedbacklist.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    if(response.getString("todayhwmessage").equals("Success")) {
                        Log.d("msgr", response.getString("todayhwmessage"));
                        JSONArray jsonArray = response.getJSONArray("todayhw_test");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            list.add(new FdOldModel(jsonObject.getString("testimonial_id"),
                                    jsonObject.getString("testimonial_name"),
                                    jsonObject.getString("testimonial_desc"),
                                    jsonObject.getString("testimonial_date"),
                                    jsonObject.getString("testimonial_time")));

                        }
                    }else {
                        Toast.makeText(getContext(), "No Record Available..!", Toast.LENGTH_LONG).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapter = new FdOldAdapter(getContext(), list);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        requestQueue.add(jsonObjectRequest);

    }
        //end of api fetch data

    }


