package com.app.gemstoneschool.Adapters;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Activities.AttendenceShowStdList;
import com.app.gemstoneschool.Model.ViewAttendenceModel;
import com.app.gemstoneschool.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ViewAttendenceAdapter extends RecyclerView.Adapter<ViewAttendenceAdapter.ViewHolder> {

    List<ViewAttendenceModel> userlist = new ArrayList<>();

    Context context;
    public ViewAttendenceAdapter(Context context, List<ViewAttendenceModel>userlist){
        this.userlist=userlist;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_viewattendence_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(userlist.get(position).getStud_name()=="null"){
            holder.txtclName.setText("");
        }
        else {
            holder.txtclName.setText(userlist.get(position).getStud_name());
        }
        if(userlist.get(position).getSrid()=="null"){
            holder.txtsrid.setText("");
        }
        else {
            holder.txtsrid.setText(userlist.get(position).getSrid());
        }
        if(userlist.get(position).getAtt_status()=="null"){
            holder.txtstatus.setText("");
        }
        else {

            if(userlist.get(position).getAtt_status().equals("p")){
                holder.txtstatus.setText(userlist.get(position).getAtt_status());
                holder.txtstatus.setTextColor(Color.GREEN);

            }

            else if (userlist.get(position).getAtt_status().equals("AB")){
                holder.txtstatus.setText(userlist.get(position).getAtt_status());
                holder.txtstatus.setTextColor(Color.RED);

            }
        }

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtclName,txtsrid,txtstatus;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtclName=itemView.findViewById(R.id.viewname);
            txtsrid=itemView.findViewById(R.id.viewsrid);
            txtstatus=itemView.findViewById(R.id.viewst);

        }

    }
}
