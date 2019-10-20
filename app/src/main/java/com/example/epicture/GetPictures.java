package com.example.epicture;

import android.app.Activity;

import okhttp3.*;

public class GetPictures {

    public Activity activity;
    private OkHttpClient httpClient;
    public Login login;
    callbackGetPictures _callbackGetPictures;

    public GetPictures(Activity _activity, Login _login) {
        activity = _activity;
        _callbackGetPictures = new callbackGetPictures(activity);
        httpClient = new OkHttpClient.Builder().build();
        login = _login;
    }

    public void requestPictures(String url) {
//        String url = "https://api.imgur.com/3/gallery/top/viral/1.json";
//        String url = "https://api.imgur.com/3/account/" + login.username + "/images";
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization","Bearer " + login.accessToken)
                .build();

        httpClient.newCall(request).enqueue(_callbackGetPictures);
    }
}