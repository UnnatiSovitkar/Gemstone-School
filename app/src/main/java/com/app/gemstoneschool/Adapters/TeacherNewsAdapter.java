package com.app.gemstoneschool.Adapters;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Activities.APINewsDetail;
import com.app.gemstoneschool.Activities.NewsUpdateTeacherActivity;
import com.app.gemstoneschool.Activities.ViewNewsDetailTeacherActivity;
import com.app.gemstoneschool.Model.TeacherNewsModel;
import com.app.gemstoneschool.NoticeRecycler.NewsModel;
import com.app.gemstoneschool.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TeacherNewsAdapter extends RecyclerView.Adapter<TeacherNewsAdapter.ViewHolder> {

   public final List<TeacherNewsModel> userlist;
    public TeacherNewsAdapter(Context context, List<TeacherNewsModel>userlist){
        this.userlist=userlist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item_teachernews_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {

//        holder.id.setText(userlist.get(position).getId());
        holder.id.setText(""+(position+1));
        holder.title.setText(userlist.get(position).getTitle());
       // userlist.get(position).getDate();
//        holder.date.setText(userlist.get(position).getDate().toString());

        String id=userlist.get(position).getId();

        Log.d("id_news",String.valueOf(userlist.get(position).getId()));
        String n=userlist.get(position).getDescription();
        String t=userlist.get(position).getTitle();
        String d=userlist.get(position).getDate();
        String i=userlist.get(position).getNews_image();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
//        String datestring=sdf.format(d);

        try {
            Date date = format.parse(d);
            holder.date.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {

                    Intent intent = new Intent(v.getContext(),ViewNewsDetailTeacherActivity.class);
                    intent.putExtra("detail",n);
                    intent.putExtra("dateformat",d);
                    intent.putExtra("newsTitle",t);
                    intent.putExtra("newsimg",i);


                v.getContext().startActivity(intent);

                /*
                AlertDialog.Builder builder=new AlertDialog.Builder(v.getContext());
                ProgressDialog progressDialog=new ProgressDialog(v.getContext());

                CharSequence[] dialogItem={"View Notice","Edit Data","Delete Data"};
                builder.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){

                            case 0:
                                Intent intent = new Intent(v.getContext(), ViewNewsDetailTeacherActivity.class);
                                intent.putExtra("detail",n);
                                intent.putExtra("dateformat",d);
                                intent.putExtra("newsTitle",t);
                                intent.putExtra("newsimg",i);


                                v.getContext().startActivity(intent);
                                break;
                            case 1:
                                Intent j = new Intent(v.getContext(),NewsUpdateTeacherActivity.class);
                                j.putExtra("id",id);
                                j.putExtra("detail",n);
                                j.putExtra("dateformat",d);
                                j.putExtra("newsTitle",t);
                                j.putExtra("newsimg",i);


                                v.getContext().startActivity(j);
                                break;
                            case 2:
                                break;
                        }
                    }
                });

                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
                */
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

            id=itemView.findViewById(R.id.srnot);
            title=itemView.findViewById(R.id.newst);
            date=itemView.findViewById(R.id.datent);
            layout=itemView.findViewById(R.id.cardlayoutt);
        }

    }
}
