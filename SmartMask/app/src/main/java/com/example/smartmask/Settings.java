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

public class Settings extends AppCompatActivity {

    private String URL = "https://aplicaciones.uteq.edu.ec/smartmask/";
    private RequestQueue requestQueue;

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
                        updateUser(jsonmac);
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

    private void updateUser(String datajson){

        //Obtención de datos del web service utilzando Volley
        // requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.POST,URL+"maskapis/updateUser",
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
                                Log.e("resp", response);
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

}