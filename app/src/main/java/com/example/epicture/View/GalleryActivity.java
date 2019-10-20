package com.example.epicture.View;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.epicture.Controller.Login;
import com.example.epicture.R;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Login login = new Login(this);
        login.login();
    }

}
