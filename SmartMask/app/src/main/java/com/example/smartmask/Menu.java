package com.example.smartmask;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
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
import java.util.HashMap;
import java.util.Map;

public class Menu extends AppCompatActivity {

    private String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";
    private RequestQueue requestQueue;

    private CardView profile,settings,options,report,btnReportAir;
    String jsonDataUser ="";
    private ImageView logoff;

    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;

    //handler
    private Handler handler;
    private Runnable mTicker;

    // notificaciones
    private PendingIntent pendingIntent;
    private final static String CHANNEL_ID = "NOTIFICACION";
    private final static int NOTIFICACION_ID = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        init();
        sessionuser();
        Log.i("Logs", "SMART MASK");
        if (user_informationid!=null && email != null) {
            Log.i("User-Menu", email);
            String datajson = "{\n" +
                    "    \"user_informationid\":\"" + user_informationid +"\"" +
                    "}";
            Log.i("Logs", datajson);
            exec(datajson);
            logoff.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    logoff();
                }
            });

        } else {
            Toast.makeText(Menu.this, "No session", Toast.LENGTH_LONG).show();
            gologin();
        }
     //   Bundle bundle = this.getIntent().getExtras();
      //  jsonDataUser=getIntent().getExtras().getString("Session");
    }
    public void init() {
        profile=findViewById(R.id.btnProfile);
        settings=findViewById(R.id.btnSettings);
        btnReportAir = findViewById(R.id.btnReportAir);
        options=findViewById(R.id.btnOptions);
        //logoff = findViewById(R.id.btnlog_off);
        logoff=findViewById(R.id.btnSalir);
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
    private void logoff(){
        preferences.edit().clear().apply();
        gologin();
        Toast.makeText(Menu.this, "Closed session", Toast.LENGTH_LONG).show();
    }
    // Se controla la pulsación del botón atrás
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Do you want to exit SmartMask?")
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                     dialog.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void Profile(View view){
        Intent intent=new Intent(Menu.this, Profile1.class);
        //Bundle b=new Bundle();
      //  intent.putExtra("Session",jsonDataUser);
        //intent.putExtras(b);
        startActivity(intent);
    }
    public void Settings(View view){
        Intent intent=new Intent(Menu.this, Settings.class);
        Bundle b=new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }
    public void Options(View view){
        Intent intent=new Intent(Menu.this, AirQuiality.class);
        Bundle b=new Bundle();
        intent.putExtras(b);
        startActivity(intent);
    }
    public void AirQualityTable(View view){
        Intent intent=new Intent(Menu.this, AirQualityTable.class);
        //Bundle b=new Bundle();
      //  intent.putExtra("Session",jsonDataUser);
        //intent.putExtras(b);
        startActivity(intent);
//
//       Intent intent=new Intent(Menu.this, AirQuiality.class);
//        Bundle b=new Bundle();
//        intent.putExtras(b);
//        startActivity(intent);
    }

    // Notificaciones

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion";
            NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }
    }

    private void sendMyNotification(String title,String message) {

        //On click of notification it redirect to this Activity
        Intent intent = new Intent(this, AirQuiality.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       /* Bundle b = new Bundle();
        b.putString("notification", String.valueOf(bandnotify));
        intent.putExtras(b); */
        pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        Bitmap icon = BitmapFactory.decodeResource(getResources(),
                R.drawable.logosmartmask);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID)
                .setSmallIcon(R.drawable.notifications)
                .setLargeIcon(icon)
                .setContentTitle(title)
                .setContentText(message)
                .setAutoCancel(true)
                .setSound(soundUri)
                .setContentIntent(pendingIntent);

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());
    }
    private void exec(String datajson){
        handler= new Handler();
        mTicker = new Runnable() {
            @Override
            public void run() {
                notifypush(datajson); // método que se llamará frecuentemente para simular el tiempo real
                handler.postDelayed(this,5000);//se ejecutara cada 1 segundos
            }
        };
        handler.postDelayed(mTicker,5000);//se ejecutara cada 5 segundos
    }
    public void eliminarMask(View view){
       // Intent intent=new Intent(Menu.this, DeleteMask.class);
        Intent intent=new Intent(Menu.this, ListMaskActivity.class);

        Bundle b=new Bundle();

        intent.putExtras(b);
        startActivity(intent);
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

    private void notifypush(String datajson) {

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
                                                if(jsonAirQuality.get("alert").toString().equals("true")){
                                                   // Log.d("ALERTA PARA ",response);
                                                    sendMyNotification("Notificación Android","Emitiendo alerta");
                                                    createNotificationChannel();
                                                }

                                            } catch (JsonIOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    }catch (Exception e) {
                                        Log.e("Error",e.getMessage());
                                    }
                                } else {
                                    Toast.makeText(Menu.this, "Data not captured", Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(Menu.this, "ERROR", Toast.LENGTH_LONG).show();
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