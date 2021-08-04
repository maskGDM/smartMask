package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Menu extends AppCompatActivity {
    CardView profile,settings,options,report;
    String jsonDataUser ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        Bundle bundle = this.getIntent().getExtras();
        profile=findViewById(R.id.btnProfile);
        settings=findViewById(R.id.btnSettings);
<<<<<<< Updated upstream
        options=findViewById(R.id.btnOptions);
=======
        jsonDataUser=getIntent().getExtras().getString("Session");
>>>>>>> Stashed changes
    }
    public void Profile(View view){
        Intent intent=new Intent(Menu.this, Profile.class);
        //Bundle b=new Bundle();
        intent.putExtra("Session",jsonDataUser);
        //intent.putExtras(b);
        startActivity(intent);
    }
    public void Settings(View view){
        Intent intent=new Intent(Menu.this, Settings.class);
        Bundle b=new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }
    public void Options(View view){
        Intent intent=new Intent(Menu.this, Options.class);
        Bundle b=new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }
}