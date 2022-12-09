package com.app.gemstoneschool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Activities.TodayHWDetails;
import com.app.gemstoneschool.Model.StdViewHwModel;
import com.app.gemstoneschool.Model.TodaysHwModel;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.interfaces.TotalListnerInterface;
import com.bumptech.glide.Glide;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StdHwdapter extends RecyclerView.Adapter<StdHwdapter.ViewHolder>{

    Context context;
    List<StdViewHwModel> list;
    private TotalListnerInterface totalListnerInterface;

    public StdHwdapter(Context context,List<StdViewHwModel> userlist,TotalListnerInterface totalListnerInterface) {
        this.context=context;
        this.list=userlist;
        this.totalListnerInterface=totalListnerInterface;


    }

    @NonNull
    @Override
    public StdHwdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_std_hw_view_item,parent,false);
        return new StdHwdapter.ViewHolder(view,totalListnerInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull StdHwdapter.ViewHolder holder, int position) {


        CleanerProperties props = new CleanerProperties();//removing html tags
        props.setPruneTags("script");//removing html tags


//        holder.id.setText(Integer.toString(todaysHwList.get(position).getSr_no()));
        holder.title.setText(list.get(position).getGet_hw_info());

        String d=list.get(position).getHw_att_cdate();


        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date = format.parse(d);
            System.out.println(date);
            holder.date.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }




        Glide.with(context)
                .load(list.get(position).getHw_att_img())
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.img);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView date, title,desc;
        ImageView img,imgdelete;

        public ViewHolder(@NonNull View itemView,TotalListnerInterface totalListnerInterface) {
            super(itemView);

            title=itemView.findViewById(R.id.stdhwtitle);
            date=itemView.findViewById(R.id.stdhwdate);
            img=itemView.findViewById(R.id.stdhwimg);
            imgdelete=itemView.findViewById(R.id.stdhwdelete);


            imgdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    totalListnerInterface.onItemClick(getAdapterPosition());
                }
            });
        }


    }
}
