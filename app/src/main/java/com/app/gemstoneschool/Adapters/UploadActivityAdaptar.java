package com.app.gemstoneschool.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.R;

import java.util.ArrayList;

public class UploadActivityAdaptar extends RecyclerView.Adapter<UploadActivityAdaptar.ViewHolder> {

    private ArrayList<Uri> arrayList;
    private Context context;
    private OnItemClickListner listner;
    public interface OnItemClickListner{
        void onItemClick(int position);

    }
    public void setonItemSelectedListner(OnItemClickListner clickListner){
        listner=clickListner;
    }


    public UploadActivityAdaptar(ArrayList<Uri> arrayList) {

        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UploadActivityAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_activityimgup_item_design,parent,false);
        return new ViewHolder(view,listner);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadActivityAdaptar.ViewHolder holder, int position) {

        holder.img.setImageURI(arrayList.get(position));


    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView img,img_close;
        public ViewHolder(@NonNull View itemView,OnItemClickListner listner) {
            super(itemView);

            img=itemView.findViewById(R.id.image_activity);
            img_close=itemView.findViewById(R.id.close_img_act);

            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listner.onItemClick(getBindingAdapterPosition());
                }
            });


        }


    }
}
