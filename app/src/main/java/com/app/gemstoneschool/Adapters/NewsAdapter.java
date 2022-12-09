package com.app.gemstoneschool.Adapters;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Activities.APINewsDetail;
import com.app.gemstoneschool.NoticeRecycler.NewsModel;
import com.app.gemstoneschool.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    List<NewsModel> userlist;
    public NewsAdapter(Context context,List<NewsModel>userlist){
        this.userlist=userlist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.id.setText(""+(position+1));
        holder.title.setText(userlist.get(position).getTitle());
       // userlist.get(position).getDate();
//        holder.date.setText(userlist.get(position).getDate().toString());

        String n=userlist.get(position).getDescription();
        String t=userlist.get(position).getTitle();
        String d=userlist.get(position).getDate();
        String i=userlist.get(position).getNews_image();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
        //String datestring=sdf.format(d);

        Log.d("oooo",userlist.get(position).getDate().toString());


        try {
            Date date = format.parse(d);
            holder.date.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }



        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(), APINewsDetail.class);
                    intent.putExtra("detail",n);
                    intent.putExtra("dateformat",d);
                    intent.putExtra("newsTitle",t);
                    intent.putExtra("newsimg",i);


                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView id,title,date;
        private CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.srno);
            title=itemView.findViewById(R.id.news);
            date=itemView.findViewById(R.id.daten);
            layout=itemView.findViewById(R.id.cardlayout);
        }

    }
}
