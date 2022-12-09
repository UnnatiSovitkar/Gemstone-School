package com.app.gemstoneschool.fragments.StudentFragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
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
import com.app.gemstoneschool.Activities.AboutSchoolActivity;
import com.app.gemstoneschool.Activities.AttendenceActivity;
import com.app.gemstoneschool.Activities.ContactusActivity;
import com.app.gemstoneschool.Activities.FAQActivity;
import com.app.gemstoneschool.Activities.FeedbackActivity;
import com.app.gemstoneschool.Activities.FeesActivity;
import com.app.gemstoneschool.Activities.GalleryActivity;
import com.app.gemstoneschool.Activities.HomeWorkActivity;
import com.app.gemstoneschool.Activities.NoticeActivity;
import com.app.gemstoneschool.Activities.SendEnquiryActivity;
import com.app.gemstoneschool.Activities.VideosActivity;
import com.app.gemstoneschool.Adapters.StdHwdapter;
import com.app.gemstoneschool.Adapters.TeacherHwAdapter;
import com.app.gemstoneschool.Adapters.UploadActivityAdaptar;
import com.app.gemstoneschool.Model.StdViewHwModel;
import com.app.gemstoneschool.Model.TeacherHwModel;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.interfaces.TotalListnerInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewStdHwfragment extends Fragment implements TotalListnerInterface {

    RecyclerView recyclerView;
    List<StdViewHwModel> userlist = new ArrayList<>();
    StdHwdapter adapter;
    SharedPreferences sharedPreferences;
    String srid,id,hwid;


    private static final String SHARED_PREF_NAME_STD="myprefstd";
    private static final String Key_SRID="srId";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_view_std_hw,container,false);

        sharedPreferences=getActivity().getSharedPreferences(SHARED_PREF_NAME_STD, Context.MODE_PRIVATE);
        srid=sharedPreferences.getString(Key_SRID,null);
        Log.d("iiii",srid);

        recyclerView = v.findViewById(R.id.recyclerview_vhw);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        getHwData();

//        adapter=new StdHwdapter(userlist);
//        adapter.setonItemSelectedListner(new StdHwdapter.OnItemClickDeleteListner() {
//            @Override
//            public void onItemClick(int position) {
//                deleteItem();
//                userlist.remove(position);
//                adapter.notifyItemRemoved(position);
//            }
//        });

        return v;
    }

    public void getHwData() {

        ////fetching staff Profile api using mobile number
        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url = "https://gemstonews.in/gemstoneerp/apis/student/get_attach_howework.php";//Api//
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONObject Obj = new JSONObject(response);
                    JSONArray jsonArray1 = Obj.getJSONArray("todayN");
                    for (int i = 0; i < jsonArray1.length(); i++) {
                        JSONObject jsonObject = jsonArray1.getJSONObject(i);

                        String hwattid = jsonObject.getString("hw_att_id");
                        hwid = jsonObject.getString("hwa_id");
                        String hw_att_cdate = jsonObject.getString("hw_att_cdate");
                        String get_hw_info = jsonObject.getString("get_hw_info");
                        String hw_att_img = jsonObject.getString("hw_att_img");

                        userlist.add(new StdViewHwModel(hwattid,hwid,hw_att_cdate,get_hw_info,hw_att_img));
//                        Toast.makeText(TeacherDashboardActivity.this, username, Toast.LENGTH_SHORT).show();
                        //put data on shared preference

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                adapter=new StdHwdapter(getContext(),userlist,ViewStdHwfragment.this);
                recyclerView.setAdapter(adapter);
                adapter.notifyDataSetChanged();



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> parms = new HashMap<>();

                parms.put("sr_id",srid);


                return parms;
            }
        };
        //Adding the string request to the queue
        queue.add(request);

    }

    private void deleteItem() {

        RequestQueue queue = Volley.newRequestQueue(getContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/student/send_howework_delete.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONObject respObj = new JSONObject(response);
                    Toast.makeText(getContext(), "Item Deleted Successfully..!", Toast.LENGTH_SHORT).show();


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

                params.put("hw_att_id",id);

                return params;
            }
        };

        queue.add(request);


    }

    @Override
    public void onItemView(int position) {

    }

    @Override
    public void onItemClick(int position) {
        id=userlist.get(position).getHwattid();
        deleteItem();
        userlist.remove(position);
        adapter.notifyItemRemoved(position);
        adapter.notifyDataSetChanged();
    }
}
