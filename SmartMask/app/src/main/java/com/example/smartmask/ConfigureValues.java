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

public class ConfigureValues extends AppCompatActivity {

    private String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";
    private RequestQueue requestQueue;

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

                        String jsondataget = "{\n" +
                                "    \"user_informationid\":\"" + user_informationid +"\"\n" +
                                "}";

                       // getconfigurevalues(jsondataget);

                        String jsondata = "{\n" +
                                "    \"user_informationid\":\"" + user_informationid +"\",\n" +
                                "    \"Dioximin\":\"" + Dioximin.getText() +"\",\n" +
                                "    \"Dioximax\":\"" + Dioximax.getText() +"\",\n" +
                                "    \"Monomin\":\"" + Monomin.getText() +"\",\n" +
                                "    \"Monomax\":\"" + Monomax.getText() +"\",\n" +
                                "    \"Ammomin\":\"" + Ammomin.getText() +"\",\n" +
                                "    \"Ammomax\":\"" + Ammomax.getText() +"\",\n" +
                                "    \"Butanomin\":\"" + Butanomin.getText()+"\",\n" +
                                "    \"Butanomax\":\"" + Butanomax.getText()+"\"\n" +
                                "}";
                        Log.i("Logs", jsondata);
                      //  configurevaluesPOST(jsondata);
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

    private void configurevaluesPOST(String datajson){

        //Obtención de datos del web service utilzando Volley
        // requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.POST,URL+"",
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
                              //  Toast.makeText(ConfigureValues.this, json_transform.getString("information"), Toast.LENGTH_LONG).show();
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

    private void getconfigurevalues(String datajson){

        //Obtención de datos del web service utilzando Volley
        // requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.POST,URL+"",
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
                               /* Dioximin.setText("");
                                Dioximax.setText("");
                                Monomin.setText("");
                                Monomax.setText("");
                                Ammomin.setText("");
                                Ammomax.setText("");
                                Butanomin.setText("");
                                Butanomax.setText(""); */

                                //  Toast.makeText(ConfigureValues.this, json_transform.getString("information"), Toast.LENGTH_LONG).show();
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
}