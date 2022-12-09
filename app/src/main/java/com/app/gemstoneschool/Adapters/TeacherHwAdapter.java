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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Activities.APINewsDetail;
import com.app.gemstoneschool.Activities.DetailHomeWork;
import com.app.gemstoneschool.Activities.NewsUpdateTeacherActivity;
import com.app.gemstoneschool.Activities.ViewNewsDetailTeacherActivity;
import com.app.gemstoneschool.Model.TeacherHwModel;
import com.app.gemstoneschool.Model.TeacherNewsModel;
import com.app.gemstoneschool.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class TeacherHwAdapter extends RecyclerView.Adapter<TeacherHwAdapter.ViewHolder> {

   public final List<TeacherHwModel> userlist;
    public TeacherHwAdapter(Context context, List<TeacherHwModel>userlist){
        this.userlist=userlist;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hwteach_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,int position) {

        holder.id.setText(""+(position+1));
        holder.yr.setText(userlist.get(position).getYear());
        holder.board.setText(userlist.get(position).getBoardn());
        holder.cl.setText(userlist.get(position).getClname());
        holder.section.setText(userlist.get(position).getSecname());
        holder.sub.setText(userlist.get(position).getSubname());
        holder.title.setText(userlist.get(position).getTitle());
        holder.desc.setText(userlist.get(position).getDescription());
        holder.date.setText(userlist.get(position).getDate());
       // userlist.get(position).getDate();

        String yr,brd,cls,sec,sub,tit,des,date,img;
        yr=userlist.get(position).getYear();
        brd=userlist.get(position).getBoardn();
        cls=userlist.get(position).getClname();
        sec=userlist.get(position).getSecname();
        sub=userlist.get(position).getSubname();

        Log.d("sub",sub);
        tit=userlist.get(position).getTitle();
        des=userlist.get(position).getDescription();
        date=userlist.get(position).getDate();
        img=userlist.get(position).getImg();



//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
//        //String datestring=sdf.format(d);
//
//        try {
//            Date date = format.parse(d);
//            holder.date.setText(sdf.format(date));
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

        holder.detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), DetailHomeWork.class);
                intent.putExtra("year",yr);
                intent.putExtra("board",brd);
                intent.putExtra("clas",cls);
                intent.putExtra("section",sec);
                intent.putExtra("sub",sub);
                intent.putExtra("title",tit);
                intent.putExtra("desc",des);
                intent.putExtra("date",date);
                intent.putExtra("img",img);



                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView id,yr,board,cl,section,sub,title,desc,date;
        private Button detail;
        private ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id=itemView.findViewById(R.id.hwaid);
            yr=itemView.findViewById(R.id.yr);
            board=itemView.findViewById(R.id.board);
            cl=itemView.findViewById(R.id.cls);
            section=itemView.findViewById(R.id.sec);
            sub=itemView.findViewById(R.id.sub);
            title=itemView.findViewById(R.id.ttl);
            desc=itemView.findViewById(R.id.desc);
            date=itemView.findViewById(R.id.dt);
            detail=itemView.findViewById(R.id.hwdetail);
            img=itemView.findViewById(R.id.imghwdetail);

        }

    }
}
