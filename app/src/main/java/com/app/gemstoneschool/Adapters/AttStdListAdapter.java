package com.app.gemstoneschool.Adapters;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Activities.AttendenceShowStdList;
import com.app.gemstoneschool.Model.AttStdListModel;
import com.app.gemstoneschool.Model.TakeAttendenceModel;
import com.app.gemstoneschool.R;
import com.google.android.material.internal.CheckableImageButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AttStdListAdapter extends RecyclerView.Adapter<AttStdListAdapter.ViewHolder> {
    private LayoutInflater inflater;
    public static ArrayList<AttStdListModel> userlist;


    Context context;
    public AttStdListAdapter(Context context, ArrayList<AttStdListModel>userlist){
        inflater = LayoutInflater.from(context);
        this.userlist=userlist;
        this.context=context;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_takeattstdlist_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

//        Log.d("adptersrid",userlist.get(position).getSr_id());

        holder.srid.setText(userlist.get(position).getSr_id());
        holder.check.setChecked(userlist.get(position).getSelected());
        holder.stddname.setText(userlist.get(position).getStud_name());


        holder.check.setTag(position);
        holder.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.check.getTag();
                Toast.makeText(context, userlist.get(pos).getStud_name() + " clicked!", Toast.LENGTH_SHORT).show();

                if (userlist.get(pos).getSelected()) {
                    userlist.get(pos).setSelected(false);
                } else {
                    userlist.get(pos).setSelected(true);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView stddname,srid;
        private CheckBox check;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            stddname=itemView.findViewById(R.id.tkattstdnm);
            srid=itemView.findViewById(R.id.tkattstdsrid);
            check=itemView.findViewById(R.id.attstatus);

        }

    }
}
