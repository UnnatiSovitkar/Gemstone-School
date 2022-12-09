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
import com.app.gemstoneschool.Model.TodaysHwModel;
import com.app.gemstoneschool.R;
import com.bumptech.glide.Glide;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import java.util.List;

public class THWAdapter extends RecyclerView.Adapter<THWAdapter.ViewHolder>{

    Context context;
    List<TodaysHwModel> todaysHwList;
    public THWAdapter(Context context, List<TodaysHwModel>todaysHwList){
        this.context=context;
        this.todaysHwList=todaysHwList;
    }
    @NonNull
    @Override
    public THWAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.thw_recycler_item_design,parent,false);
        return new THWAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull THWAdapter.ViewHolder holder, int position) {


        CleanerProperties props = new CleanerProperties();//removing html tags
        props.setPruneTags("script");//removing html tags
        String result = new HtmlCleaner(props).clean(todaysHwList.get(position).getThw_desc()).getText().toString();//removing html tags



//        holder.id.setText(Integer.toString(todaysHwList.get(position).getSr_no()));
        holder.title.setText(todaysHwList.get(position).getThw_title());
        holder.status.setText(todaysHwList.get(position).getThw_status());
        holder.desc.setText(result);



        Glide.with(context)
                .load(todaysHwList.get(position).getThw_material_file())
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.img);
//        holder.title.setText(todaysHwList.get(position).getThw_title());

        // userlist.get(position).getDate();
        // holder.date.setText(userlist.get(position).getDate().toString());

        String thwdescTxt=todaysHwList.get(position).getThw_desc();
        String thwtitileTxt=todaysHwList.get(position).getThw_title();
        String thwmaterialTxt=todaysHwList.get(position).getThw_material_file();
        String thwstatusTxt=todaysHwList.get(position).getThw_status();

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(v.getContext(), TodayHWDetails.class);
                intent.putExtra("detaildesc",thwdescTxt);
                intent.putExtra("title",thwtitileTxt);
                intent.putExtra("thwimage",thwmaterialTxt);
                intent.putExtra("status",thwstatusTxt);


                v.getContext().startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return todaysHwList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView id, title,status,desc;
        ImageView img;
        private CardView layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.dthwtitle);
            status=itemView.findViewById(R.id.thwdstatus);
            desc=itemView.findViewById(R.id.thwdesc);
            img=itemView.findViewById(R.id.thwimg);
            layout=itemView.findViewById(R.id.thwcardlayout);
        }

    }
}
