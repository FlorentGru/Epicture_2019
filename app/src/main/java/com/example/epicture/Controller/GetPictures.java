package com.example.epicture.Controller;

import android.app.Activity;

import com.example.epicture.View.callbackGetPictures;

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
        Request request = new Request.Builder()
                .url(url)
                .header("Authorization","Bearer " + login.accessToken)
                .build();

        httpClient.newCall(request).enqueue(_callbackGetPictures);
    }
}