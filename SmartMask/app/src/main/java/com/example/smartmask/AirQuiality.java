package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
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
import java.util.Map;

public class AirQuiality extends AppCompatActivity {

    private String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";
    private RequestQueue requestQueue;

    //handler
    private Handler handler;
    private Runnable mTicker;
    // pastel
    private PieChart piechart;
    // barra
    private BarChart barChart;

    String []meses=new String[] {"ppminternal","ppmexternal","pressure","temperature","humidity"};
    int []ventas=new int[]{359,300,108,21,71};
    int []color=new int[]{Color.BLUE,Color.CYAN,Color.GREEN,Color.MAGENTA,Color.RED};

    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_quiality);
        init();
        sessionuser();

        if (user_informationid!=null && email != null) {

            String datajson = "{\n" +
                    "    \"user_informationid\":\"" + user_informationid +"\"" +
                    "}";
            Log.i("Logs", datajson);
            exec(datajson);
        } else {
            Toast.makeText(AirQuiality.this, "No session", Toast.LENGTH_LONG).show();
            gologin();
        }

        showpopupersonalized();
        barChart=(BarChart) findViewById(R.id.barChart);
        createCharts();


    }

    private void init(){
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
        Toast.makeText(AirQuiality.this, "Closed session", Toast.LENGTH_LONG).show();
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

    private void airquilitydatapost(String datajson) {
        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL + "maskapis/maskRecordsOne",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        boolean band = false;
                        response = fixEncoding(response);
                        //  Log.d("Respuesta", response);
                        try {

                           // JSONArray jsonarray = new JSONArray(response);
                            Log.d("JsonData",response);
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
                                                  Log.d("ppminternal", jsonAirQuality.get("ppminternal").toString());
                                            } catch (JsonIOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }catch (Exception e) {
//                            Toast.makeText(MainActivity.this, "ERROR", Toast.LENGTH_LONG).show();
                                    }
                                } else {
                                    Toast.makeText(AirQuiality.this, "Datos no capturados", Toast.LENGTH_LONG).show();
                                }
                            } else {
                            Toast.makeText(AirQuiality.this, "ERROR", Toast.LENGTH_LONG).show();
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

    private Chart getSameChart(Chart chart,String description,int txtColor,int background,int animateY){
        chart.getDescription().setText(description);
        chart.getDescription().setTextSize(15);
        chart.setDrawingCacheBackgroundColor(background);
        chart.animateY(animateY);
        legend(chart);
        return chart;
    }
    private void legend(Chart chart){
        Legend legend=chart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setHorizontalAlignment(Legend.LegendHorizontalAlignment.CENTER);

        ArrayList<LegendEntry> entries=new ArrayList<>();
        for (int i=0;i<meses.length;i++){
            LegendEntry entry=new LegendEntry();
            entry.formColor=color[i];
            entry.label=meses[i];
            entries.add(entry);
        }
        legend.setCustom(entries);
    }
    private ArrayList<BarEntry>getBarEntries(){
        ArrayList<BarEntry> entries=new ArrayList<>();
        for (int i=0;i<ventas.length;i++)
            entries.add(new BarEntry(i,ventas[i]));
            return entries;

    }
    private void axisX(XAxis axis){
        axis.setGranularityEnabled(true);
        axis.setPosition(XAxis.XAxisPosition.BOTTOM);
        axis.setValueFormatter(new IndexAxisValueFormatter(meses));

    }

    private void axisLeft(YAxis axis){
        axis.setSpaceTop(30);
        axis.setAxisMinimum(0);
    }
    private void axisRight(YAxis axis){
        axis.setEnabled(false);
    }
    private void createCharts(){
        barChart=(BarChart) getSameChart(barChart,"Series",Color.BLUE,Color.CYAN,3000);
        barChart.setDrawGridBackground(true);
        barChart.setDrawBarShadow(true);
        barChart.setData(getBarData());

        barChart.invalidate();
        axisX(barChart.getXAxis());
        axisLeft(barChart.getAxisLeft());
        axisRight(barChart.getAxisRight());
    }
    private DataSet getDat(DataSet dataSet){
        dataSet.setColors(color);
        dataSet.setValueTextSize(Color.WHITE);
        dataSet.setValueTextSize(10);
        return dataSet;
    }
    private BarData getBarData(){
        BarDataSet barDataSet=(BarDataSet)getDat(new BarDataSet(getBarEntries(),""));
        barDataSet.setBarShadowColor(Color.GRAY);
        BarData barData=new BarData(barDataSet);
        barData.setBarWidth(0.45f);
        return barData;
    }

    private void showpopupersonalized(){

        AlertDialog.Builder builder = new AlertDialog.Builder(AirQuiality.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_modal_information,null);
        builder.setView(view);
        final AlertDialog dialog = builder.create();
        dialog.show();
        TextView txtclose =(TextView) dialog.findViewById(R.id.txtclose);
        txtclose.setText("M");
        txtclose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AirQuiality.this, "Close modal", Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
    }

    private void exec(String datajson){
        handler= new Handler();
        mTicker = new Runnable() {
            @Override
            public void run() {
                airquilitydatapost(datajson); // método que se llamará frecuentemente para simular el tiempo real
                handler.postDelayed(this,1000);//se ejecutara cada 1 segundos
            }
        };
        handler.postDelayed(mTicker,5000);//se ejecutara cada 5 segundos
    }

 /*   // método para destruir el handler
    @Override public void onDestroy ()
    {
        handler.removeCallbacks(mTicker);
        super.onDestroy ();
    } */


}