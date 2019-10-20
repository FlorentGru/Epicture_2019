package com.example.epicture;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class GalleryActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        Login login = new Login(this);
        login.login();
    }

}
