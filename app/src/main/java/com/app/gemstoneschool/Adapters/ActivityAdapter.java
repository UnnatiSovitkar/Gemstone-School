package com.app.gemstoneschool.Adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Activities.APINewsDetail;
import com.app.gemstoneschool.Model.ActivityModel;
import com.app.gemstoneschool.NoticeRecycler.NewsModel;
import com.app.gemstoneschool.R;
import com.bumptech.glide.Glide;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ActivityAdapter extends RecyclerView.Adapter<ActivityAdapter.ViewHolder> {

    List<ActivityModel> userlist;
    Context context;
    public ActivityAdapter(Context context, List<ActivityModel>userlist){
        this.userlist=userlist;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_activity_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.srn.setText(""+(position+1));
        holder.title.setText(Html.fromHtml(userlist.get(position).getTitle()));
//        holder.date.setText(userlist.get(position).getDate());


        holder.desc.setText(Html.fromHtml(userlist.get(position).getDescription()));
        holder.desc.setMovementMethod(LinkMovementMethod.getInstance());//used for clickable link



        Glide.with(context)
                .load(userlist.get(position).getImage())
                .placeholder(R.drawable.noimagefound)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.imageView);
        // userlist.get(position).getDate();
       String d=userlist.get(position).getDate().toString();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        //String datestring=sdf.format(d);

        try {
            Date date = format.parse(d);
            holder.date.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView srn,title,date,desc;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            srn=itemView.findViewById(R.id.srn_act);
            title=itemView.findViewById(R.id.act_title);
            desc=itemView.findViewById(R.id.act_desc);
            date=itemView.findViewById(R.id.act_date);
            imageView=itemView.findViewById(R.id.acti_img);
        }

    }
}
