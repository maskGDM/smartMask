package com.example.smartmask;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartmask.adapters.ListAdapterMask;
import com.example.smartmask.models.MaskModel;
import com.github.mikephil.charting.data.BarEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListMaskActivity extends AppCompatActivity {

    private String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";
    private RequestQueue requestQueue;

    private List<MaskModel> elements;

    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_mask);
        init();
        sessionuser();

        if (user_informationid!=null && email != null) {
            Log.i("User-Menu", email);
            String datajson = "{\n" +
                    "    \"user_informationid\":\"" + user_informationid +"\"" +
                    "}";
            Log.i("Logs", datajson);
            getdata(datajson);

        } else {
            Toast.makeText(ListMaskActivity.this, "No session", Toast.LENGTH_LONG).show();
            gologin();
        }

    }

    public void init() {
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
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
    public void gologin() {
        Intent i = new Intent(this, MainActivity.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
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

    private void getdata(String datajson) {
        elements = new ArrayList<>();
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL + "maskapis/selectMask",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        boolean band = false;
                        response = fixEncoding(response);
                        //  Log.d("Respuesta", response);
                        try {
                            JsonParser jsonParser = new JsonParser();
                            JsonObject jsonObject = jsonParser.parse(response).getAsJsonObject();
                            if (jsonObject.size() > 0) {
                                JsonElement jsonElement = jsonObject.get("status");
                                if (jsonElement.getAsInt() == 2) {
                                    JsonArray jsonArray = jsonObject.get("data").getAsJsonArray();
                                    try {
                                        for(int f=0;f<jsonArray.size();f++)
                                        {
                                            try {
                                                JsonObject jsonAirQuality=jsonArray.get(f).getAsJsonObject();
                                                //   Log.d("ppminternal", jsonAirQuality.get("ppminternal").toString());
                                                Log.d("mask_code",jsonAirQuality.get("mask_code").toString());
                                                Log.d("dateadded",jsonAirQuality.get("dateadded").toString());
                                                elements.add(new MaskModel(jsonAirQuality.get("mask_code").toString(),jsonAirQuality.get("dateadded").toString()));

                                            } catch (JsonIOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                        ListAdapterMask listAdapter = new ListAdapterMask(elements, getApplicationContext());
                                        RecyclerView recyclerView = findViewById(R.id.ListRecyclerView);
                                        recyclerView.setHasFixedSize(true);
                                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                                        recyclerView.setAdapter(listAdapter);
                                    }catch (Exception e) {
                                        Log.e("Error",e.getMessage());
                                    }
                                } else {
                                    Toast.makeText(ListMaskActivity.this, "Datos no capturados", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(ListMaskActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                            }


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
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
