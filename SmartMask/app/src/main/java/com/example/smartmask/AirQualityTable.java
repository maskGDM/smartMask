package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
import com.github.mikephil.charting.charts.PieChart;
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
    private Button btnhearhregistry;

    //handler
    private Handler handler;
    private Runnable mTicker;
    // pastel

    PieChart piechart;

    String jsonDataUser = "{}";
    RequestQueue requestQueue;
    String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";
    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_quality_table);
        init();
        sessionuser();
        if (user_informationid!=null && email != null) {
            Log.i("User", email);
            requestQueue = Volley.newRequestQueue(this);
            String jsonfiltrarall = "{\n" +
                    "    \"id_usuario\":\"" + user_informationid +"\"\n" +
                    "}";
            Log.i("Logs", jsonfiltrarall);
            stringRequestVolley(jsonfiltrarall,"maskapis/maskRecords");

            btnhearhregistry.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(txtFechaInicio.getText() !=null && txtFechaFin.getText() !=null){
                        String jsonfiltrarbydate = "{\n" +
                                "    \"id_usuario\":\"" + user_informationid +"\"\n" +
                                "    \"stardate\":\"" + txtFechaInicio.getText() +"\"\n" +
                                "    \"enddate\":\"" + txtFechaFin.getText() +"\"\n" +
                                "}";
                        Log.i("Logs", jsonfiltrarbydate);
                        stringRequestVolley(jsonfiltrarbydate,"maskapis/maskRecordsInterval");
                    }else{
                        Toast.makeText(AirQualityTable.this, "Empty fields", Toast.LENGTH_LONG).show();
                    }
                }
            });
            initDatePicker();

        } else {
            Toast.makeText(AirQualityTable.this, "No session", Toast.LENGTH_LONG).show();
            gologin();
        }

}
    private void exec(){
        handler= new Handler();
        mTicker = new Runnable() {
            @Override
            public void run() {
             //   grafi(); método que se llamará frecuentemente para simular el tiempo real
                handler.postDelayed(this,1000);//se ejecutara cada 1 segundos
            }
        };
        handler.postDelayed(mTicker,5000);//se ejecutara cada 5 segundos
    }

    // método para destruir el handler
    @Override public void onDestroy ()
    {
        handler.removeCallbacks(mTicker);
        super.onDestroy ();
    }
    private void init(){
        txtFechaInicio=findViewById(R.id.txtFechaInicio);
        txtFechaFin=findViewById(R.id.txtFechaFin);
        txtFechaInicio.setText(getTodayDate());
        btnhearhregistry=findViewById(R.id.btnshearhregistry);
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
        Toast.makeText(AirQualityTable.this, "Closed session", Toast.LENGTH_LONG).show();
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
                txtFechaInicio.setText(date);
                txtFechaFin.setText(finaldate);
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
       // return getMonthFormat(month)+ " " + day + " " + year;
        return year+ "-" + month + "-" + day;
    }
    private String makeDateStringFinish(int day, int month, int year){
       // return getMonthFormat(month)+ " " + day + " " + year;
        return year+ "-" + month + "-" + day;
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

    private void stringRequestVolley(String json, String ruta) {
        StringRequest request = new StringRequest(Request.Method.POST, URL +  ruta, new com.android.volley.Response.Listener<String>() {
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