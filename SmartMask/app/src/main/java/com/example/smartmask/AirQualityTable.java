package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

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

public class AirQualityTable extends AppCompatActivity {
    String jsonDataUser = "{}";
    RequestQueue requestQueue;
    String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_quality_table);
        String result=getIntent().getExtras().getString("Session");
        System.out.println("valor "+result);
        jsonDataUser = (result);
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonDataUser).getAsJsonObject();
        requestQueue = Volley.newRequestQueue(this);
        String jsonLogin = "{\n" +
                "    \"id_usuario\":\"" + jsonObject.get("user_informationid").toString() +"\"\n" +
                "}";
        Log.i("Logs", jsonLogin);
        stringRequestVolley(jsonLogin);

}

    private void stringRequestVolley(String json) {
        StringRequest request = new StringRequest(Request.Method.POST, URL + "maskapis/maskRecords", new com.android.volley.Response.Listener<String>() {
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

                            System.out.println("Datos AirQ "+jsonArray);


                        } catch (Exception e) {
//                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(AirQualityTable.this, "Datos no capturados", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(AirQualityTable.this, "ERROR", Toast.LENGTH_LONG).show();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AirQualityTable.this, "ERROR", Toast.LENGTH_LONG).show();
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
                    Toast.makeText(AirQualityTable.this, "ERROR", Toast.LENGTH_LONG).show();
                    return null;
                }
            }
        };
        requestQueue.add(request);
    }
}