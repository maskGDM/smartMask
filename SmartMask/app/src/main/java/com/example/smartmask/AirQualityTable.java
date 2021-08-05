package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TableLayout;
import android.widget.TextView;
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

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

public class AirQualityTable extends AppCompatActivity {
    private DatePickerDialog datePickerDialog,dateFinish;
    private TextView txtFechaInicio,txtFechaFin;


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

        initDatePicker();
        txtFechaInicio=findViewById(R.id.txtFechaInicio);
        txtFechaFin=findViewById(R.id.txtFechaFin);

        txtFechaInicio.setText(getTodayDate());
        
}
    private String getTodayDate(){
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day,month,year);
    }
    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month=month+1;
                String date=makeDateString(day,month,year);
                String finaldate=makeDateStringFinish(day,month,year);
                //txtFechaInicio.setText(date);
                //txtFechaFin.setText(finaldate);
            }
        };
        Calendar cal= Calendar.getInstance();
        int year=cal.get(Calendar.YEAR);
        int month=cal.get(Calendar.MONTH);
        int day=cal.get(Calendar.DAY_OF_MONTH);
        int style= AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog=new DatePickerDialog(this,style,dateSetListener,year,month,day);
       dateFinish=new DatePickerDialog(this,style,dateSetListener,year,month,day);
    }
    private String makeDateString(int day, int month, int year){
        return getMonthFormat(month)+ " " + day + " " + year;
    }
    private String makeDateStringFinish(int day, int month, int year){
        return getMonthFormat(month)+ " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if (month==1){
            return "Jan";
        }if (month==2){
            return "Feb";
        }if (month==3){
            return "Mar";
        }if (month==4){
            return "Apr";
        }if (month==5){
            return "May";
        }if (month==6){
            return "Jun";
        }if (month==7){
            return "Jul";
        }if (month==8){
            return "Aug";
        }if (month==9){
            return "Sep";
        }if (month==10){
            return "Oct";
        }if (month==11){
            return "Nov";
        }if (month==12){
            return "Dec";
        }
        return "Jan";
    }
    public void openDatePicker(View view){
        datePickerDialog.show();
    }
    public void finishDate(View view){
        dateFinish.show();
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