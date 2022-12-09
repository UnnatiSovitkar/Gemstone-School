package com.app.gemstoneschool.fragments.StaffFragments;

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
import com.app.gemstoneschool.Activities.SendEnquiryActivity;
import com.app.gemstoneschool.Activities.StaffActivityActivity;
import com.app.gemstoneschool.Activities.StaffAttActivity;
import com.app.gemstoneschool.Activities.StaffChFeedbachActivity;
import com.app.gemstoneschool.Activities.StaffGalleryActivity;
import com.app.gemstoneschool.Activities.StaffHwActivity;
import com.app.gemstoneschool.Activities.StaffNoticeActivity;
import com.app.gemstoneschool.Activities.StaffSendEnqActivity;
import com.app.gemstoneschool.Activities.VideosActivity;
import com.app.gemstoneschool.R;

public class StaffHomefragment extends Fragment implements View.OnClickListener {

    CardView notice,homeW,attendence,gallery,activity,
            feedback,sendenquiry;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_staff_home,container,false);


        notice=v.findViewById(R.id.notice_staff);
        attendence=v.findViewById(R.id.attendence_staff);
        homeW=v.findViewById(R.id.hw_staff);
        gallery=v.findViewById(R.id.gallery_staff);
        activity=v.findViewById(R.id.activity_staff);
        feedback=v.findViewById(R.id.chfd_staff);
        sendenquiry=v.findViewById(R.id.chenq_staff);

        notice.setOnClickListener(this);
        attendence.setOnClickListener(this);
        homeW.setOnClickListener(this);
        gallery.setOnClickListener(this);
        activity.setOnClickListener(this);
        feedback.setOnClickListener(this);
        sendenquiry.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()){
            case R.id.notice_staff:

                i=new Intent(getActivity(), StaffNoticeActivity.class);
                startActivity(i);
                break;

            case R.id.attendence_staff:

                i=new Intent(getActivity(), StaffAttActivity.class);
                startActivity(i);
                break;

            case R.id.hw_staff:


                i=new Intent(getActivity(), StaffHwActivity.class);
                startActivity(i);
                break;


            case R.id.gallery_staff:

                i=new Intent(getActivity(), StaffGalleryActivity.class);
                startActivity(i);
                break;

            case R.id.activity_staff:

                i=new Intent(getActivity(), StaffActivityActivity.class);
                startActivity(i);
                break;

            case R.id.chfd_staff:
                i=new Intent(getActivity(), StaffChFeedbachActivity.class);
                startActivity(i);
                break;

            case R.id.chenq_staff:
                i=new Intent(getActivity(), StaffSendEnqActivity.class);
                startActivity(i);
                break;

            default:break;

        }
    }
}
