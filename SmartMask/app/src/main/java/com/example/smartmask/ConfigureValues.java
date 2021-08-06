package com.example.smartmask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ConfigureValues extends AppCompatActivity {
    private EditText Dioximin, Dioximax, Monomin, Monomax, Ammomin, Ammomax,Butanomin, Butanomax;
    private Button btnsave;
    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configure_values);
        init();
        sessionuser();
        Log.i("Logs", "SMART MASK");
        if (user_informationid!=null && email != null) {
            Log.i("User", email);
            btnsave.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(Dioximin !=null && Dioximax !=null && Monomin !=null && Monomax !=null
                            && Ammomin !=null && Ammomax !=null && Butanomin !=null
                            && Butanomax !=null){
                        String jsondata = "{\n" +
                                "    \"Dioximin\":\"" + Dioximin.getText() +"\"\n" +
                                "    \"Dioximax\":\"" + Dioximax.getText() +"\"\n" +
                                "    \"Monomin\":\"" + Monomin.getText() +"\"\n" +
                                "    \"Monomax\":\"" + Monomax.getText() +"\"\n" +
                                "    \"Ammomin\":\"" + Ammomin.getText() +"\"\n" +
                                "    \"Ammomax\":\"" + Ammomax.getText() +"\"\n" +
                                "    \"Butanomin\":\"" + Butanomin.getText()+"\"\n" +
                                "    \"Butanomax\":\"" + Butanomax.getText()+"\"\n" +
                                "}";
                        Log.i("Logs", jsondata);
                    }else{
                        Toast.makeText(ConfigureValues.this, "Empty fields", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(ConfigureValues.this, "No session", Toast.LENGTH_LONG).show();
            gologin();
        }
    }

    private void init(){
        Dioximin=findViewById(R.id.txtMinCO21);
        Dioximax=findViewById(R.id.txtMaxCO21);
        Monomin=findViewById(R.id.txtMinCO1);
        Monomax=findViewById(R.id.txtMaxCO1);
        Ammomin=findViewById(R.id.txtMinAm1);
        Ammomax=findViewById(R.id.txtMaxAm1);
        Butanomin=findViewById(R.id.txtMinBu1);
        Butanomax=findViewById(R.id.txtMaxBu1);
        btnsave=findViewById(R.id.btnValores);
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