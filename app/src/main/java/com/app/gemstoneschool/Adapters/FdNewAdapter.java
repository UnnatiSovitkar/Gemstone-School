package com.app.gemstoneschool.Adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Model.EqNewModel;
import com.app.gemstoneschool.Model.FdNewModel;
import com.app.gemstoneschool.R;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FdNewAdapter extends RecyclerView.Adapter<FdNewAdapter.ViewHolder> {

    List<FdNewModel> list;
    public FdNewAdapter(Context context, List<FdNewModel>list){
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_fdnew_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.srn.setText(""+(position+1));
        holder.name.setText(list.get(position).getName());

        CleanerProperties props = new CleanerProperties();//removing html tags
        props.setPruneTags("script");//removing html tags
        String result = new HtmlCleaner(props).clean(list.get(position).getDesc()).getText().toString();//removing html tags

        holder.desc.setText(result);
        holder.date.setText(list.get(position).getDate());
        holder.time.setText(list.get(position).getTime());
//        holder.date.setText(list.get(position).getDate());

        // userlist.get(position).getDate();
       // holder.date.setText(userlist.get(position).getDate().toString());

//        String n=list.get(position).getName();
//        String t=list.get(position).getDesc();
        String dat=list.get(position).getDate();
//        String i=list.get(position).getTime();

        Log.d("date2",dat);

        SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");

        try {
            Date date = format.parse(dat);
            System.out.println(date);
            holder.date.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }


//        holder.layout.setOnClickListener(new View.OnClickListener() {
//            @Override
//           public void onClick(View v) {
//
//                    Intent intent = new Intent(v.getContext(), APINewsDetail.class);
//                    intent.putExtra("detail",n);
//                    intent.putExtra("dateformat",d);
//                    intent.putExtra("newsTitle",t);
//                    intent.putExtra("newsimg",i);
//
//
//                v.getContext().startActivity(intent);
//            }
//        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name,desc,date,time,srn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.fdnew_name);
            desc=itemView.findViewById(R.id.fdnew_desc);
            date=itemView.findViewById(R.id.fdnew_dt);
            time=itemView.findViewById(R.id.fdnew_time);
            srn=itemView.findViewById(R.id.srn_new_fd);

        }

    }
}
