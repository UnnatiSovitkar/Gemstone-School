package com.app.gemstoneschool.fragments.StaffFragments.Enquiry;

import android.os.Bundle;
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
import com.app.gemstoneschool.Adapters.EqNewAdapter;
import com.app.gemstoneschool.Model.EqNewModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NewEnquiryFragment extends Fragment {

    RecyclerView recyclerView;
    List<EqNewModel> list = new ArrayList<>();
    EqNewAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_enquiry_fragment_staff, container, false);

        recyclerView = view.findViewById(R.id.recycler_staff_new_enquiry);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getData();

        return view;
    }


    private void getData() {

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String url = "https://gemstonews.in/gemstoneerp/apis/teacher/get_enquerylist_new.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {


                try {
                    JSONArray jsonArray = response.getJSONArray("todayhw_test");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        list.add(new EqNewModel(jsonObject.getString("uid"),
                                jsonObject.getString("fullname"),
                                jsonObject.getString("students"),
                                jsonObject.getString("mobile"),
                                jsonObject.getString("emailid"),
                                jsonObject.getString("pstd"),
                                jsonObject.getString("pmsg"),
                                jsonObject.getString("ptype"),
                                jsonObject.getString("SchoolName"),
                                jsonObject.getString("year"),
                                jsonObject.getString("dateortime"),
                                jsonObject.getString("regstatus")));

                    }

                } catch (JSONException e) {
                    e.printStackTrace();

                }
                adapter = new EqNewAdapter(getContext(), list);
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