package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
    }
    public void Configure(View view){
        Intent intent=new Intent(Settings.this, ConfigureValues.class);
        Bundle b=new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }
}