package com.example.smartmask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private Button btn_login;
    private EditText et_mail, et_pass;
    private TextView btn_register, btn_recover;
    private RequestQueue requestQueue;
    private String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";
    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        validatesesion();
        requestQueue = Volley.newRequestQueue(this);
        //OnClickListener();
        btn_login.setOnClickListener(v -> {
            String jsonLogin = "{\n" +
                    "    \"user\": \"" + et_mail.getText().toString() + "\",\n" +
                    "    \"pass\": \"" + et_pass.getText().toString() + "\"\n" +
                    "}";
            Log.i("Logs", jsonLogin);
            stringRequestVolley(jsonLogin);
        });
    }

    private void OnClickListener() {
        btn_login.setOnClickListener(v -> {
            String jsonLogin = "{\n" +
                    "    \"user\": \"" + et_mail.getText().toString() + "\",\n" +
                    "    \"pass\": \"" + et_pass.getText().toString() + "\"\n" +
                    "}";
            Log.i("Logs", jsonLogin);
            stringRequestVolley(jsonLogin);
        });
    }
    public void Login(View view){
        Intent intent=new Intent(MainActivity.this, RegisterActivity.class);
        Bundle b=new Bundle();

        intent.putExtras(b);
        startActivity(intent);
    }

    private void stringRequestVolley(String json) {
        StringRequest request = new StringRequest(Request.Method.POST, URL + "maskapis/logIn", new com.android.volley.Response.Listener<String>() {
        @Override
            public void onResponse(String response) {
                response= fixEncoding(response);
                Log.i("Logs", response);
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

                if (jsonObject.size() > 0) {
                    JsonElement jsonElement = jsonObject.get("status");
                    if (jsonElement.getAsInt() == 2) {
                        JsonArray jsonArray = jsonObject.get("data").getAsJsonArray();
                       try {
                            JsonObject jsonObjectUser = jsonArray.get(0).getAsJsonObject();
                            if(jsonObjectUser.size() != 0){
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString("user_informationid",jsonObjectUser.get("user_informationid").toString().replace("\"", ""));
                                editor.putString("user",jsonObjectUser.get("user").toString().replace("\"", ""));
                                editor.putString("names",jsonObjectUser.get("names").toString().replace("\"", ""));
                                editor.putString("lastnames",jsonObjectUser.get("lastnames").toString().replace("\"", ""));
                                editor.putString("email",jsonObjectUser.get("email").toString().replace("\"", ""));
                                editor.putString("imguser",jsonObjectUser.get("imguser").toString().replace("\"", ""));
                                editor.putString("birthdaydate",jsonObjectUser.get("birthdaydate").toString().replace("\"", ""));
                                editor.commit();
                                // redirecciona al menu
                                goMenu();
                            }else{
                                Toast.makeText(MainActivity.this, "USUARIO O CONTRASE??A INCORRECTO", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "USUARIO O CONTRASE??A INCORRECTO", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
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
                    return json == null ? "{}".getBytes("utf-8") : json.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        };
        requestQueue.add(request);
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

    private void init(){
        et_mail = findViewById(R.id.et_mail);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_recover = findViewById(R.id.btn_recover);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
    }
    private void validatesesion(){
        sessionuser();
        if (user_informationid!=null && email != null) {
            goMenu();
        }
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
    private void goMenu(){
        Intent i = new Intent(this, Menu.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }
}