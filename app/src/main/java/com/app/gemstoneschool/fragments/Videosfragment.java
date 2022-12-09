package com.app.gemstoneschool.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.app.gemstoneschool.R;

public class Videosfragment extends Fragment {
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.drawer_fragment_video,container,false);

        WebView webView=view.findViewById(R.id.webview);
        webView.loadUrl("https://gemstonews.in/app_activity.php");
        WebSettings webSettings=webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        return view;
    }
}
