package com.app.gemstoneschool.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.gemstoneschool.Adapters.AttStdListAdapter;
import com.app.gemstoneschool.Model.AttStdListModel;
import com.app.gemstoneschool.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SubmitAttendenceActivity extends AppCompatActivity {
    private TextView tv;
    int i;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_attendence);

        tv = (TextView) findViewById(R.id.tv);

        for (i = 0; i < AttStdListAdapter.userlist.size(); i++){
            if(AttStdListAdapter.userlist.get(i).getSelected()) {
                tv.setText(tv.getText() + " " + AttStdListAdapter.userlist.get(i).getStud_name()+" "
                +AttStdListAdapter.userlist.get(i).getSr_id());
                getData();
            }
        }
    }
    private void getData() {
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String url1="https://gemstonews.in/gemstoneerp/apis/teacher/take_studteacher_att.php";


        StringRequest request = new StringRequest(Request.Method.POST, url1, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("ClassDetails",response);

                //  Toast.makeText(getContext(), "Data added to API", Toast.LENGTH_SHORT).show();
                try {

                    JSONObject respObj = new JSONObject(response);

                    JSONArray jsonArray5=respObj.getJSONArray("studatt_details");





                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getApplicationContext(), "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<String, String>();


                params.put("student_attend_submit","1");
                params.put("stud_id",AttStdListAdapter.userlist.get(i).getSr_id());

                return params;
            }
        };

        queue.add(request);
    }

}