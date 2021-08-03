package com.example.smartmask;


import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity  {
    EditText et_name,et_lastname,et_mail,et_user, et_pass,et_pass2;
    Button  btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      setContentView(R.layout.activity_register);


        et_name = findViewById(R.id.et_name);
        et_lastname = findViewById(R.id.et_lastname);
        et_mail = findViewById(R.id.et_mail);
        et_user = findViewById(R.id.et_user);
        et_pass = findViewById(R.id.et_pass);
        et_pass2 = findViewById(R.id.et_pass2);
        btnSignUp = findViewById(R.id.btnSignUp);


      //  Bundle bundle = this.getIntent().getExtras();

    }

}