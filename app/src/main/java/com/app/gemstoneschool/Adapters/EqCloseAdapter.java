package com.app.gemstoneschool.Adapters;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Model.EqCloseModel;
import com.app.gemstoneschool.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EqCloseAdapter extends RecyclerView.Adapter<EqCloseAdapter.ViewHolder> {

    List<EqCloseModel> list;
    public EqCloseAdapter(Context context, List<EqCloseModel>list){
        this.list=list;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_eqclose_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.srn.setText(""+(position+1));
        holder.name.setText(list.get(position).getName());
        holder.stdname.setText(list.get(position).getStdname());
        holder.mobile.setText(list.get(position).getMobile());

        holder.emailid.setText(list.get(position).getEmailid());
        holder.standard.setText(list.get(position).getPstd());
        holder.msg.setText(list.get(position).getMsg());

        holder.ptype.setText(list.get(position).getPtype());
        holder.schname.setText(list.get(position).getSchname());
        holder.yr.setText(list.get(position).getYear());
//        holder.date.setText(list.get(position).getDate());

        // userlist.get(position).getDate();
       // holder.date.setText(userlist.get(position).getDate().toString());

//        String n=list.get(position).getName();
//        String t=list.get(position).getDesc();
        String dat=list.get(position).getDate();
        Log.d("date",dat);
//        String i=list.get(position).getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-dd-MMM");
        SimpleDateFormat sdf=new SimpleDateFormat("dd-MM-yyyy");
/*
        try {
            Date date = format.parse(dat);
            System.out.println(date);
            holder.date.setText(sdf.format(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

 */

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView name,stdname,mobile,emailid,standard,msg,ptype,schname,yr,date,srn;
//        private CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name=itemView.findViewById(R.id.eq_name);
            stdname=itemView.findViewById(R.id.eq_std);
            mobile=itemView.findViewById(R.id.eq_mob);
            emailid=itemView.findViewById(R.id.eq_emqil);
            standard=itemView.findViewById(R.id.eq_std);
            msg=itemView.findViewById(R.id.eq_msg);
            ptype=itemView.findViewById(R.id.eq_type);
            schname=itemView.findViewById(R.id.eq_sn);
            yr=itemView.findViewById(R.id.eq_year);
//            date=itemView.findViewById(R.id.eq_date);
            srn=itemView.findViewById(R.id.srn);
//            layout=itemView.findViewById(R.id.cardlayout);
        }

    }
}
