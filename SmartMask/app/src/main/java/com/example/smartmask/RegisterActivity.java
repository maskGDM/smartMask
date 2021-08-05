package com.example.smartmask;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity  {
    private EditText et_name,et_lastname,et_mail,et_user, et_pass,et_pass2;
    private Button  btnSignUp;
    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_register);
      init();

      btnSignUp.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String jsonLogin = "{\n" +
                        "    \"name\": \"" + et_name.getText().toString() + "\",\n" +
                        "    \"lastname\": \"" + et_lastname.getText().toString() + "\"\n" +
                        "    \"email\": \"" + et_mail.getText().toString() + "\"\n" +
                        "    \"user\": \"" + et_user.getText().toString() + "\"\n" +
                        "    \"pass\": \"" + et_pass.getText().toString() + "\"\n" +
                        "    \"confirmpass\": \"" + et_pass2.getText().toString() + "\"\n" +
                        "}";
                Log.i("Logs", jsonLogin);
            }
      });
    }

    public void init() {
        et_name = findViewById(R.id.et_name);
        et_lastname = findViewById(R.id.et_lastname);
        et_mail = findViewById(R.id.et_mail);
        et_user = findViewById(R.id.et_user);
        et_pass = findViewById(R.id.et_pass);
        et_pass2 = findViewById(R.id.et_pass2);
        btnSignUp = findViewById(R.id.btnSignUp);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
    }

}