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
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ConfigureValues extends AppCompatActivity {

    private String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";
    private RequestQueue requestQueue;

    private EditText Dioximin, Dioximax, Monomin, Monomax, Ammomin, Ammomax,Butanomin, Butanomax;
    private Button btnsave, btnrestValores;
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
            String jsondataget = "{\n" +
                    "    \"user_informationid\":\"" + user_informationid +"\"\n" +
                    "}";
            getconfigurevalues(jsondataget);
            btnsave.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(Dioximin !=null && Dioximax !=null && Monomin !=null && Monomax !=null
                            && Ammomin !=null && Ammomax !=null && Butanomin !=null
                            && Butanomax !=null){

                        String jsondata = "{\n" +
                                "    \"user_informationid\":\"" + user_informationid +"\",\n" +
                                "    \"minvalueco2\":\"" + Dioximin.getText() +"\",\n" +
                                "    \"maxvalueco2\":\"" + Dioximax.getText() +"\",\n" +
                                "    \"minvalueco\":\"" + Monomin.getText() +"\",\n" +
                                "    \"maxvalueco\":\"" + Monomax.getText() +"\",\n" +
                                "    \"minvaluenh3\":\"" + Ammomin.getText() +"\",\n" +
                                "    \"maxvaluenh3\":\"" + Ammomax.getText() +"\",\n" +
                                "    \"mainvaluec4h10\":\"" + Butanomin.getText()+"\",\n" +
                                "    \"maxvaluec4h10\":\"" + Butanomax.getText()+"\"\n" +
                                "}";
                        Log.i("Logs", jsondata);
                        configurevaluesPOST(jsondata);
                    }else{
                        Toast.makeText(ConfigureValues.this, "Empty fields", Toast.LENGTH_LONG).show();
                    }
                }
            });
            btnrestValores.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    restevalues(jsondataget);
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
        btnrestValores = findViewById(R.id.btnrestValores);
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
                Request.Method.POST,URL+"maskapis/saveData/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        response = fixEncoding(response);
                        JSONObject json_transform = null;
                        try {
                            Log.d("Response",response);
                         //   json_transform = new JSONObject(response);
                            gosettings();
                            Toast.makeText(ConfigureValues.this, "Configured values", Toast.LENGTH_LONG).show();
                        } catch (Exception e) {
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

    public void gosettings() {
        Intent i = new Intent(this, Settings.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    private void getconfigurevalues(String datajson){

        //Obtención de datos del web service utilzando Volley
        // requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.POST,URL+"maskapis/selectData/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        response = fixEncoding(response);
                        Log.d("selectData",response);
                        JSONObject json_transform = null;
                        try {
                            JsonParser jsonParser = new JsonParser();
                            JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                            if (size > 0)
                            {
                                JsonElement jsonElement = jsonObject.get("status");
                                if (jsonElement.getAsInt() == 2) {
                                    JsonArray jsonArray = jsonObject.get("data").getAsJsonArray();
                                        for(int f=0;f<jsonArray.size();f++)
                                        {
                                            try {
                                                JsonObject jsonAirQuality=jsonArray.get(f).getAsJsonObject();

                                                Dioximin.setText(jsonAirQuality.get("minvalueco2").toString());
                                                Dioximax.setText(jsonAirQuality.get("maxvalueco2").toString());
                                                Monomin.setText(jsonAirQuality.get("minvalueco").toString());
                                                Monomax.setText(jsonAirQuality.get("maxvalueco").toString());
                                                Ammomin.setText(jsonAirQuality.get("minvaluenh3").toString());
                                                Ammomax.setText(jsonAirQuality.get("maxvaluenh3").toString());
                                                Butanomin.setText(jsonAirQuality.get("minvaluec4h10").toString());
                                                Butanomax.setText(jsonAirQuality.get("maxvaluec4h10").toString());


                                            } catch (JsonIOException e) {
                                                e.printStackTrace();
                                            }
                                    }
                                }else {
                                    Toast.makeText(ConfigureValues.this, "Datos no capturados", Toast.LENGTH_LONG).show();
                                }

                            }else {
                                Toast.makeText(ConfigureValues.this, "ERROR", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
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

    private void restevalues(String datajson){

        //Obtención de datos del web service utilzando Volley
        // requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(
                Request.Method.POST,URL+"maskapis/restoreData/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        response = fixEncoding(response);
                        Log.d("restData",response);
                        JSONObject json_transform = null;
                        try {
                            if (size > 0)
                            {
                                gosettings();
                                Toast.makeText(ConfigureValues.this, "Reset values", Toast.LENGTH_LONG).show();
                            }else {
                                Toast.makeText(ConfigureValues.this, "ERROR", Toast.LENGTH_LONG).show();
                            }
                        } catch (Exception e) {
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