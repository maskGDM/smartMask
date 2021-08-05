package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.json.JSONException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
//                            //recorrer json
//                            ArrayList<String[]> lista;
//                            lista = new ArrayList<>();
//                            int index=0;
//                            if (lista != null) {
//                                if (index >= 0 && index < lista.size()) {
//                                    //obtiene los items
//                                    String[] items = lista.get(index);
//                                    if (items != null) {
//                                        //agrega la traducción al vector
//                                        items[2] = translatedText;
//                                    }
//                                }
//                                if (lista.size() - 1 == index) {
//                                    //se envía la lista de datos a la tabla
//                                    tableAdapt(lista, true);
//                                }
//                            }
                            ArrayList<String[]> lista=new ArrayList<String[]>();


                            for(int f=0;f<jsonArray.size();f++)
                            {
                                try {
                                    JsonObject jsonAirQuality=jsonArray.get(f).getAsJsonObject();
                                    lista.add(new String[]{jsonAirQuality.get("alert").toString(),
                                            jsonAirQuality.get("ppminternal").toString(),
                                            jsonAirQuality.get("ppmexternal").toString(),
                                            jsonAirQuality.get("pressure").toString(),
                                            jsonAirQuality.get("temperature").toString()});

                                    tableAdapt(lista, true);
                                } catch (JsonIOException e) {
                                    e.printStackTrace();
                                }
                            }

//                            System.out.println("Datos AirQ "+jsonArray);

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

    private void tableAdapt(ArrayList<String[]> lista, boolean dataAir) {
        TableLayout table = (TableLayout) findViewById(R.id.table);
        ModalAirQualityTable tbModel = new ModalAirQualityTable(AirQualityTable.this, table);

        if (dataAir) {
            tbModel.setHeaders(new String[]{"alert", "ppminternal", "ppmexternal", "pressure","temperature"});
        } else {
            tbModel.setHeaders(new String[]{"alert", "ppminternal", "ppmexternal"});
        }
        tbModel.setRows(lista);

        tbModel.setHeaderBackGroundColor(R.color.black);
        tbModel.setRowsBackGroundColor(R.color.white);

        tbModel.setHeadersForeGroundColor(R.color.white);
        tbModel.setRowsForeGroundColor(R.color.black);
        tbModel.makeTable();

    }
}