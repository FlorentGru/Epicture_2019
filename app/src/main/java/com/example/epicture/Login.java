package com.example.epicture;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Login {

    public Activity activity;
    public String username = null;
    public String accessToken = null;
    public String refreshToken = null;

    public Login(Activity _activity) {
        activity = _activity;
   }

    public void login() {
        final WebView imgurWebView = (WebView) activity.findViewById(R.id.LoginWebView);
        imgurWebView.setBackgroundColor(Color.TRANSPARENT);
        imgurWebView.loadUrl("https://api.imgur.com/oauth2/authorize?client_id=a05318496e37d6e&response_type=token&state=APPLICATION_STATE");
        imgurWebView.getSettings().setJavaScriptEnabled(true);
        imgurWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.contains("https://www.getpostman.com/oauth2/")) {
                    getInformation(url, view);
                    imgurWebView.destroy();
                    test();
                } else {
                    view.loadUrl(url);
                }
                return true;
            }
        });
    }

    public void test() {
        GetPictures getpictures = new GetPictures(activity,this);
        String url = "https://api.imgur.com/3/gallery/hot/viral/3.json";
        getpictures.requestPictures(url);
    }

    private void getInformation(String url, WebView view) {
        String[] urlSplit = url.split("\\#")[1].split("\\&");
        Log.i("DEBUG url", url);
        int nb = 0;
        for (String s : urlSplit) {
            String[] info = s.split("\\=");
            if (nb == 0)
                accessToken = info[1];
            if (nb == 3)
                refreshToken = info[1];
            if (nb == 4)
                username = info[1];
            nb++;
        }
    }
}
