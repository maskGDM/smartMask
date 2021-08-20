package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile1 extends AppCompatActivity {
    String jsonDataUser = "{}";
    RequestQueue requestQueue;
    String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";

    private TextView txtNombre, txtUser, txtApellido,txtCorreo;
    private ImageView imgUser;
    private TextView aux;
    private Button btnEdit;
    //session
    private String user_informationid,user,names,lastnames,email,imguser,birthdaydate;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        init();
        sessionuser();
        Log.i("Logs", "SMART MASK");
        if (user_informationid!=null && email != null) {
            Log.i("User", email);
            dataupdate();
            btnEdit.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    if(txtNombre !=null && txtApellido !=null){
                        String jsondata = "{\n" +
                                "    \"user_informationid\":\"" + user_informationid +"\",\n" +
                                "    \"names\":\"" + txtNombre.getText() +"\",\n" +
                                "    \"user\":\"" + txtUser.getText() +"\",\n" +
                                "    \"lastnames\":\"" + txtApellido.getText() +"\",\n" +
                                "    \"email\":\"" + txtCorreo.getText() +"\"\n" +
                                "}";
                        Log.i("Logs", jsondata);
                        updateuser(jsondata);
                    }else{
                        Toast.makeText(Profile1.this, "Empty fields", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {
            Toast.makeText(Profile1.this, "No session", Toast.LENGTH_LONG).show();
            gologin();
        }

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
    public void init() {
        txtNombre = findViewById(R.id.txtMyNombre1);
        txtUser = findViewById(R.id.txtMyUser1);
        txtApellido = findViewById(R.id.txtMyApellido1);
        txtCorreo=findViewById(R.id.txtMyCorreo1);
        aux=findViewById(R.id.asad);
        imgUser=findViewById(R.id.imgUser);
        btnEdit = findViewById(R.id.btnEdit);
        preferences = getSharedPreferences("Preferences", MODE_PRIVATE);
    }
    private void logoff(){
        preferences.edit().clear().apply();
        gologin();
        Toast.makeText(Profile1.this, "Closed session", Toast.LENGTH_LONG).show();
    }
    public void dataupdate() {
        System.out.println(imguser);
            txtNombre.setText(names);
            txtUser.setText(user);
            txtApellido.setText(lastnames);
            txtCorreo.setText(email);

        Picasso.get()
                .load(imguser.replace('\\', '/'))
                .error(R.mipmap.ic_launcher)
                .resize(292,264)
                .centerCrop()
                .into(imgUser);
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
    private void updateuser(String datajson) {

        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL + "maskapis/updateUser",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        boolean band = false;
                        response = fixEncoding(response);
                        JSONObject json_transform = null;
                        try {
                            if (size > 0)
                            {
                               // json_transform = new JSONObject(response);
                                Log.d("response",response);
                                goMenu();

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

    private void goMenu(){
        Intent i = new Intent(this, Menu.class);
        // bandera para que no se creen nuevas actividades innecesarias
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
    }

    public void initdata() {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonDataUser).getAsJsonObject();
        if (jsonObject.size() > 0) {
            System.out.println("names"+jsonObject.get("names").toString());
            System.out.println("user"+jsonObject.get("user").toString());
            System.out.println("lastnames"+jsonObject.get("lastnames").toString());
            System.out.println("email"+jsonObject.get("email").toString());
            txtNombre.setText(jsonObject.get("names").toString());
            txtUser.setText(jsonObject.get("user").toString());
            txtApellido.setText(jsonObject.get("lastnames").toString());
            txtCorreo.setText(jsonObject.get("email").toString());
            String  imgURL= jsonObject.get("imguser").toString();
//            Uri img=new Uri();

//            System.out.println("FOTO  "+imgURL);
//            imgUser.setImageURI(Uri.parse(imgURL));
            Picasso.get().load("https://thumbs.dreamstime.com/b/vector-de-usuario-redes-sociales-perfil-avatar-predeterminado-retrato-vectorial-del-176194876.jpg")
                    .resize(100,100)
                    .centerCrop().into(imgUser);
        } else {

//            Toast.makeText(ProfileFragment, "Error", Toast.LENGTH_SHORT).show();

        }
    }
}