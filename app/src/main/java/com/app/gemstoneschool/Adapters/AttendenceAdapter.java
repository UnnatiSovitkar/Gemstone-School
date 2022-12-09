package com.app.gemstoneschool.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Model.AttendenceModel;
import com.app.gemstoneschool.Model.TodaysHwModel;
import com.app.gemstoneschool.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AttendenceAdapter extends RecyclerView.Adapter<AttendenceAdapter.ViewHolder>{

    Context context;
    List<AttendenceModel> attendenceList;
    public AttendenceAdapter(Context context, List<AttendenceModel>attendenceList){
        this.context=context;
        this.attendenceList=attendenceList;
    }
    @NonNull
    @Override
    public AttendenceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.attendence_recycler_item_design,parent,false);
        return new AttendenceAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendenceAdapter.ViewHolder holder, int position) {
//        holder.sdatetxt.setText(attendenceList.get(position).getStud_att_date());
        holder.attstatus.setText(attendenceList.get(position).getAtt_status());
        holder.reasontxt.setText(attendenceList.get(position).getLeave_reason());

        String d= attendenceList.get(position).getStud_att_date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        //String datestring=sdf.format(d);

        try {
            Date date = format.parse(d);
            holder.sdatetxt.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String st=attendenceList.get(position).getAtt_status();
        if(st.equals("p")){

            holder.attstatus.setTextColor(Color.GREEN);
        }
        if(st.equals("AB")){

            holder.attstatus.setTextColor(Color.RED);
        }
    }

    @Override
    public int getItemCount() {
        return attendenceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView sdatetxt,attstatus,reasontxt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            sdatetxt=itemView.findViewById(R.id.att_date);
            attstatus=itemView.findViewById(R.id.att_status);
            reasontxt=itemView.findViewById(R.id.att_reason);

        }

    }
}
