package com.example.smartmask;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity {
    Button btn_login;
    EditText et_mail, et_pass;
    TextView btn_register, btn_recover;
    RequestQueue requestQueue;
    String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_mail = findViewById(R.id.et_mail);
        et_pass = findViewById(R.id.et_pass);
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        btn_recover = findViewById(R.id.btn_recover);
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
                Log.i("Logs", response);

                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();

                if (jsonObject.size() > 0) {
                    JsonElement jsonElement = jsonObject.get("status");
                    if (jsonElement.getAsInt() == 2) {
                        JsonArray jsonArray = jsonObject.get("data").getAsJsonArray();
                        try {
                            JsonObject jsonObjectUser = jsonArray.get(0).getAsJsonObject();
                            Intent intent = new Intent(MainActivity.this, Menu.class);
                            Bundle b = new Bundle();
                            intent.putExtra("Session",jsonObjectUser.toString());
                           // b.putString("Session", jsonObjectUser.toString());
                           // intent.putExtras(b);
                            startActivity(intent);

                        } catch (Exception e) {
//                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(MainActivity.this, "USUARIO O CONTRASEÑA INCORRECTO", Toast.LENGTH_LONG).show();
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
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
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
}