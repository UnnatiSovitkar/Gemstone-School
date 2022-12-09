package com.app.gemstoneschool.Adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gemstoneschool.Activities.PreviousHwDetails;
import com.app.gemstoneschool.Activities.TodayHWDetails;
import com.app.gemstoneschool.Model.PrevHwModel;
import com.app.gemstoneschool.Model.TodaysHwModel;
import com.app.gemstoneschool.R;
import com.bumptech.glide.Glide;

import org.htmlcleaner.CleanerProperties;
import org.htmlcleaner.HtmlCleaner;

import java.util.List;

public class PHWAdapter extends RecyclerView.Adapter<PHWAdapter.ViewHolder>{

    Context context;
    List<PrevHwModel> prevHwList;
    public PHWAdapter(Context context, List<PrevHwModel>prevHwList){
        this.context=context;
        this.prevHwList=prevHwList;
    }
    @NonNull
    @Override
    public PHWAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.phw_recycler_item_design,parent,false);
        return new PHWAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PHWAdapter.ViewHolder holder, int position) {

//        holder.id.setText(Integer.toString(todaysHwList.get(position).getSr_no()));
        holder.title.setText(prevHwList.get(position).getPhw_title());
        holder.status.setText(prevHwList.get(position).getPhw_status());

        CleanerProperties props = new CleanerProperties();//removing html tags
        props.setPruneTags("script");//removing html tags
        String result = new HtmlCleaner(props).clean(prevHwList.get(position).getPhw_desc()).getText().toString();//removing html tags

        holder.desc.setText(result);

        Glide.with(context)
                .load(prevHwList.get(position).getHw_material_file())
                .placeholder(android.R.drawable.ic_menu_upload_you_tube)
                .error(android.R.drawable.stat_notify_error)
                .into(holder.img);
        String id=prevHwList.get(position).getSr_no();
        String t=prevHwList.get(position).getPhw_title();
        String s=prevHwList.get(position).getPhw_status();
        String d=prevHwList.get(position).getPhw_desc();
        String i=prevHwList.get(position).getHw_material_file();
        Log.d("materialfile",i);
        Log.d("ii", String.valueOf(prevHwList.size()));


//        holder.title.setText(todaysHwList.get(position).getThw_title());

        // userlist.get(position).getDate();
        // holder.date.setText(userlist.get(position).getDate().toString());

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "welcome", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), PreviousHwDetails.class);
                intent.putExtra("hwid",id);
                intent.putExtra("detaildesc",d);
                intent.putExtra("title",t);
                intent.putExtra("thwimage",i);
                intent.putExtra("status",s);

                v.getContext().startActivity(intent);


            }
        });


    }

    @Override
    public int getItemCount() {
        return prevHwList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView id, title,status,desc;
        ImageView img;
        private LinearLayout layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.phwtitle);
            status=itemView.findViewById(R.id.phwdstatus);
            desc=itemView.findViewById(R.id.phwdesc);
            img=itemView.findViewById(R.id.phwimg);
            layout=itemView.findViewById(R.id.phwcardlayout);
        }

    }
}
