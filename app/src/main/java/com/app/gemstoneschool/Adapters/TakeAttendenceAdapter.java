package com.app.gemstoneschool.Adapters;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Activities.APINewsDetail;
import com.app.gemstoneschool.Activities.AttendenceShowStdList;
import com.app.gemstoneschool.Model.ActivityModel;
import com.app.gemstoneschool.Model.TakeAttendenceModel;
import com.app.gemstoneschool.NoticeRecycler.NewsModel;
import com.app.gemstoneschool.R;
import com.bumptech.glide.Glide;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class TakeAttendenceAdapter extends RecyclerView.Adapter<TakeAttendenceAdapter.ViewHolder> {

    List<TakeAttendenceModel> userlist = new ArrayList<>();

    Context context;
    public TakeAttendenceAdapter(Context context, List<TakeAttendenceModel>userlist){
        this.userlist=userlist;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_takeattendence_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if(userlist.get(position).getClassn()=="null"){
            holder.txtclName.setText("");
        }
        else {
            holder.txtclName.setText(userlist.get(position).getClassn());
        }
        if(userlist.get(position).getSec_name()=="null"){
            holder.txtsection.setText("");
        }
        else {
            holder.txtsection.setText(userlist.get(position).getSec_name());
        }
        if(userlist.get(position).getStart_year()=="null"){
            holder.txtyr.setText("");
        }
        else {
            holder.txtyr.setText(userlist.get(position).getStart_year());
        }
        if(userlist.get(position).getBoardname()=="null"){
            holder.txtbrd.setText("");
        }
        else {
            holder.txtbrd.setText(userlist.get(position).getBoardname());
        }
        if(userlist.get(position).getToday_att_status()=="null"){
            holder.txtstatus.setText("");
        }
        else {

            if(userlist.get(position).getToday_att_status().equals("Pending")) {
                holder.txtstatus.setText(userlist.get(position).getToday_att_status());
                holder.txtstatus.setTextColor(Color.RED);
            }

            if(userlist.get(position).getToday_att_status().equals("Done")) {
                holder.txtstatus.setText(userlist.get(position).getToday_att_status());
                holder.txtstatus.setTextColor(Color.GREEN);
            }


//            else{
//                holder.txtstatus.setText(userlist.get(position).getToday_att_status());
//                holder.txtstatus.setTextColor(Color.GREEN);
//            }
        }

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        String date = sdf.format(new Date());
        Log.d("dttt",date);
        holder.txtdate.setText(date);



        String clid,sectionId,brdId,yrId,status;

        clid=userlist.get(position).getClass_id();
        sectionId=userlist.get(position).getSec_id();
        brdId=userlist.get(position).getBoard_id();
        yrId=userlist.get(position).getAy_id();
        status=userlist.get(position).getToday_att_status();

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        String yr=String.valueOf(year);

        holder.viewclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(status.equals("Pending")) {
                    Intent intent = new Intent(v.getContext(), AttendenceShowStdList.class);
                    intent.putExtra("ClassId", clid);
                    intent.putExtra("sectionId", sectionId);
                    intent.putExtra("boardId", brdId);
                    intent.putExtra("YearId", yrId);

                    v.getContext().startActivity(intent);

                }

                else if (status.equals("Done")){

                    Toast.makeText(context, "Attendance is Already Submitted!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView txtclName,txtsection,txtyr,txtbrd,txtstatus,txtdate;
        private CardView viewclass;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            txtclName=itemView.findViewById(R.id.cnmatt);
            txtsection=itemView.findViewById(R.id.sectionatt);
            txtyr=itemView.findViewById(R.id.ayratt);
            txtbrd=itemView.findViewById(R.id.brdatt);
            txtstatus=itemView.findViewById(R.id.statt);
            txtdate=itemView.findViewById(R.id.dtatt);

            viewclass=itemView.findViewById(R.id.cardlayoutatt);

        }

    }
}
