package com.app.gemstoneschool.fragments.StudentFragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.app.gemstoneschool.Activities.AboutSchoolActivity;
import com.app.gemstoneschool.Activities.AttendenceActivity;
import com.app.gemstoneschool.Activities.ContactusActivity;
import com.app.gemstoneschool.Activities.FAQActivity;
import com.app.gemstoneschool.Activities.FeedbackActivity;
import com.app.gemstoneschool.Activities.FeesActivity;
import com.app.gemstoneschool.Activities.GalleryActivity;
import com.app.gemstoneschool.Activities.HomeWorkActivity;
import com.app.gemstoneschool.Activities.NoticeActivity;
import com.app.gemstoneschool.R;
import com.app.gemstoneschool.Activities.SendEnquiryActivity;
import com.app.gemstoneschool.Activities.VideosActivity;

public class Homefragment extends Fragment implements View.OnClickListener {

    CardView notice,homeW,sattendence,fees,gallery,videos,aboutschool,
            feedback,sendenquiry,contactus,whatsapp,FAQ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_home,container,false);



        notice=v.findViewById(R.id.notice);
        sattendence=v.findViewById(R.id.att);
        fees=v.findViewById(R.id.fees);
        homeW=v.findViewById(R.id.hw);
        gallery=v.findViewById(R.id.gallery);
        videos=v.findViewById(R.id.video);
        aboutschool=v.findViewById(R.id.aboutschool);
        feedback=v.findViewById(R.id.feedback);
        sendenquiry=v.findViewById(R.id.sendenquiry);
        contactus=v.findViewById(R.id.contactus);
        whatsapp=v.findViewById(R.id.whatsapp);
        FAQ=v.findViewById(R.id.faq);

        notice.setOnClickListener(this);
        sattendence.setOnClickListener(this);
        fees.setOnClickListener(this);
        homeW.setOnClickListener(this);
        gallery.setOnClickListener(this);
        videos.setOnClickListener(this);
        aboutschool.setOnClickListener(this);
        feedback.setOnClickListener(this);
        sendenquiry.setOnClickListener(this);
        contactus.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        FAQ.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.notice:
                //progress dialogue

                i=new Intent(getActivity(),NoticeActivity.class);
                startActivity(i);
                break;

            case R.id.att:

                i=new Intent(getActivity(),AttendenceActivity.class);
                startActivity(i);
                break;

            case R.id.fees:

                i=new Intent(getActivity(), FeesActivity.class);
                startActivity(i);
                break;

            case R.id.hw:
                //progress dialogue

                i=new Intent(getActivity(), HomeWorkActivity.class);
                startActivity(i);
                break;


            case R.id.gallery:

                i=new Intent(getActivity(), GalleryActivity.class);
                startActivity(i);
                break;

            case R.id.video:

                i=new Intent(getActivity(), VideosActivity.class);
                startActivity(i);
                break;

            case R.id.aboutschool:

                i=new Intent(getActivity(), AboutSchoolActivity.class);
                startActivity(i);
                break;

            case R.id.feedback:
                i=new Intent(getActivity(), FeedbackActivity.class);
                startActivity(i);
                break;

            case R.id.sendenquiry:
                i=new Intent(getActivity(), SendEnquiryActivity.class);
                startActivity(i);
                break;

            case R.id.contactus:
                i=new Intent(getActivity(), ContactusActivity.class);
                startActivity(i);
                break;

            case R.id.whatsapp:
                String wam="https://wa.me/+919405812878?text=";
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(wam));
                startActivity(intent);
                break;

            case R.id.faq:
                i=new Intent(getActivity(), FAQActivity.class);
                startActivity(i);
                break;

            default:break;

        }
    }
}
