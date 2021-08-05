package com.example.smartmask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Menu extends AppCompatActivity {
    private CardView profile,settings,options,report,btnReportAir, logoff;
    String jsonDataUser ="";

    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
        sessionuser();
        Log.i("Logs", "SMART MASK");
        if (user_informationid!=null && email != null) {
            Log.i("User-Menu", email);
            logoff.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    logoff();
                }
            });

        } else {
            Toast.makeText(Menu.this, "No session", Toast.LENGTH_LONG).show();
            gologin();
        }
     //   Bundle bundle = this.getIntent().getExtras();
      //  jsonDataUser=getIntent().getExtras().getString("Session");
    }
    public void init() {
        profile=findViewById(R.id.btnProfile);
        settings=findViewById(R.id.btnSettings);
        btnReportAir = findViewById(R.id.btnReportAir);
        options=findViewById(R.id.btnOptions);
        logoff = findViewById(R.id.btnlog_off);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
    }
    public void sessionuser(){
        user_informationid = preferences.getString("user_informationid",null);
        user= preferences.getString("user",null);
        names= preferences.getString("names",null);
        lastnames= preferences.getString("lastnames",null);
        email= preferences.getString("email",null);
        imguser= preferences.getString("imguser",null);
        birthdaydate= preferences.getString("birthdaydate",null);
    }
    public void gologin() {
        Intent i = new Intent(this, MainActivity.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
    private void logoff(){
        preferences.edit().clear().apply();
        gologin();
        Toast.makeText(Menu.this, "Closed session", Toast.LENGTH_LONG).show();
    }
    // Se controla la pulsación del botón atrás
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit SmartMask?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void Profile(View view){
        Intent intent=new Intent(Menu.this, Profile1.class);
        //Bundle b=new Bundle();
      //  intent.putExtra("Session",jsonDataUser);
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
    public void AirQualityTable(View view){
        Intent intent=new Intent(Menu.this, AirQualityTable.class);
        //Bundle b=new Bundle();
      //  intent.putExtra("Session",jsonDataUser);
        //intent.putExtras(b);
        startActivity(intent);
//
//       Intent intent=new Intent(Menu.this, AirQuiality.class);
//        Bundle b=new Bundle();
//        intent.putExtras(b);
//        startActivity(intent);
    }
}