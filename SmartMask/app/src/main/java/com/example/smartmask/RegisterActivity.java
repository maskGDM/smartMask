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

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity  {

    private String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";
    private RequestQueue requestQueue;

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
                        "    \"lastname\": \"" + et_lastname.getText().toString() + "\",\n" +
                        "    \"email\": \"" + et_mail.getText().toString() + "\",\n" +
                        "    \"user\": \"" + et_user.getText().toString() + "\",\n" +
                        "    \"pass\": \"" + et_pass.getText().toString() + "\",\n" +
                        "    \"confirmpass\": \"" + et_pass2.getText().toString() + "\"\n" +
                        "}";
                Log.i("Logs", jsonLogin);
                registryuser(jsonLogin);
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

    private void registryuser(String datajson){

        //Obtención de datos del web service utilzando Volley
        StringRequest request = new StringRequest(
                Request.Method.POST,URL+"maskapis/sigIn", //
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        response = fixEncoding(response);
                        JSONObject json_transform = null;
                        try {
                            if (size > 0)
                            {
                                json_transform = new JSONObject(response);
                                Toast.makeText(RegisterActivity.this, json_transform.getString("information"), Toast.LENGTH_LONG).show();
                                gologin();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response", String.valueOf(error));
                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("Content-Type", "application/json; charset=utf-8");
                params.put("Accept", "application/json");
                return params;
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return datajson == null ? "{}".getBytes("utf-8") : datajson.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {

                    return null;
                }
            }
        };
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(request);
        } else {
            requestQueue.add(request);
        }
    }

    public static String fixEncoding(String response) {
        try {
            byte[] u = response.toString().getBytes(
                    "ISO-8859-1");
            response = new String(u, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return null;
        }
        return response;
    }

    public void gologin() {
        Intent i = new Intent(this, MainActivity.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}