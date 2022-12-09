package com.app.gemstoneschool.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.app.gemstoneschool.Activities.AboutSchoolActivity;
import com.app.gemstoneschool.Activities.ContactusActivity;
import com.app.gemstoneschool.Activities.FAQActivity;
import com.app.gemstoneschool.Activities.GalleryActivity;
import com.app.gemstoneschool.Activities.SendEnquiryActivity;
import com.app.gemstoneschool.Activities.VideosActivity;
import com.app.gemstoneschool.R;

public class Homefragmentmain extends Fragment implements View.OnClickListener {

    CardView galleryMain,activityMain,aboutusMain,contactusMain,Sendenq,faqMain1;
    ViewFlipper flipper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_homemain,container,false);

        //auto sliding images
        flipper=v.findViewById(R.id.flipper);
        int imgarray[]={R.drawable.imageschoolgallery,R.drawable.imageschoolgallery};

        for(int i=0;i<imgarray.length;i++)
            showimage(imgarray[i]);




        galleryMain=v.findViewById(R.id.gallerymain);
        activityMain=v.findViewById(R.id.activitymain);
        aboutusMain=v.findViewById(R.id.aboutusmain);
        faqMain1=v.findViewById(R.id.faqmain);
        contactusMain=v.findViewById(R.id.contactusmain);
        Sendenq=v.findViewById(R.id.sendenquirymain);

        galleryMain.setOnClickListener(this);
        activityMain.setOnClickListener(this);
        aboutusMain.setOnClickListener(this);
        faqMain1.setOnClickListener(this);
        contactusMain.setOnClickListener(this);
        Sendenq.setOnClickListener(this);
        return v;
    }

    private void showimage(int img) {

        ImageView imageView=new ImageView(getContext());
        imageView.setBackgroundResource(img);

        flipper.addView(imageView);
        flipper.setFlipInterval(2000);
        flipper.setAutoStart(true);

        flipper.setOutAnimation(getContext(), android.R.anim.slide_in_left);
        flipper.setInAnimation(getContext(), android.R.anim.slide_out_right);

    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.gallerymain:
                i=new Intent(getActivity(), GalleryActivity.class);
                startActivity(i);
                break;

            case R.id.activitymain:

                i=new Intent(getActivity(), VideosActivity.class);
                startActivity(i);
                break;

            case R.id.aboutusmain:

                i=new Intent(getActivity(), AboutSchoolActivity.class);
                startActivity(i);
                break;

            case R.id.contactusmain:
                i=new Intent(getActivity(), ContactusActivity.class);
                startActivity(i);
                break;

            case R.id.sendenquirymain:
                i=new Intent(getActivity(), SendEnquiryActivity.class);
                startActivity(i);
                break;

            case R.id.faqmain:

                i=new Intent(getActivity(), FAQActivity.class);
                startActivity(i);
                break;


            default:break;

        }
    }

}
