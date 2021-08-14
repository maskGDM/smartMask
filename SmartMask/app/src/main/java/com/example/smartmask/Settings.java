package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Settings extends AppCompatActivity {

    private EditText txtMac;
    private Button buttonconnect, btnconfigure;
    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        init();
        sessionuser();
        if (user_informationid!=null && email != null) {
            buttonconnect.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(txtMac.getText() !=null){
                        String jsonmac = "{\n" +
                                "    \"id_usuario\":\"" + user_informationid +"\"\n" +
                                "    \"mac\":\"" + txtMac.getText() +"\"\n" +
                                "}";
                        Log.i("Logs", jsonmac);
                    }else{
                        Toast.makeText(Settings.this, "Empty fields", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(Settings.this, "No session", Toast.LENGTH_LONG).show();
            gologin();
        }

    }
    public void Configure(View view){
        Intent intent=new Intent(Settings.this, ConfigureValues.class);
        Bundle b=new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }

    private void init(){
        txtMac= (EditText)findViewById(R.id.txtMac);
        buttonconnect= (Button) findViewById(R.id.buttonconnect);
        btnconfigure= (Button) findViewById(R.id.btnconfigure);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
    }

    private void gologin() {
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
        Toast.makeText(Settings.this, "Closed session", Toast.LENGTH_LONG).show();
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

}