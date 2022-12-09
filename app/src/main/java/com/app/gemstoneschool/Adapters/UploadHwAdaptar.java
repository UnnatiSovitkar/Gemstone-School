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

public class UploadHwAdaptar extends RecyclerView.Adapter<UploadHwAdaptar.ViewHolder> {

    private ArrayList<Uri> arrayList;
    Context context;

    public UploadHwAdaptar(ArrayList<Uri> arrayList) {

        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public UploadHwAdaptar.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View view=inflater.inflate(R.layout.recycler_hwimgup_item_design,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UploadHwAdaptar.ViewHolder holder, int position) {

        holder.img.setImageURI(arrayList.get(position));

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView img;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.image_hw);
        }
    }
}
