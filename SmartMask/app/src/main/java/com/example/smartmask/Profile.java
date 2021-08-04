package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Profile extends AppCompatActivity {

    String jsonDataUser = "{}";
    TextView txtNombre, txtUser, txtApellido,txtCorreo;
    ImageView imgUser;
    View view;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Bundle bundle = this.getIntent().getExtras();
        String result=getIntent().getExtras().getString("Session");
        System.out.println("valor "+result);
        jsonDataUser = (result);
        Log.i("Logs", "SMART MASK");
        if (!jsonDataUser.equals("")) {
            Log.i("Logs", jsonDataUser);
            initdata();
            initialize();
        } else {
//                Toast.makeText(NavigationActivity.this, "NO HA INICIADO SESIÓN", Toast.LENGTH_LONG).show();
//                killSession();
        }

    }

    public void initialize() {

        txtNombre = view.findViewById(R.id.txtNombre);
        txtUser = view.findViewById(R.id.txtUser);
        txtApellido = view.findViewById(R.id.txtApellido);
        txtCorreo=view.findViewById(R.id.txtCorreo);

    //    imgUser=view.findViewById(R.id.imgUser);

    }

    public void initdata() {
        JsonParser jsonParser = new JsonParser();
        JsonObject jsonObject = jsonParser.parse(jsonDataUser).getAsJsonObject();
        if (jsonObject.size() > 0) {
            txtNombre.setText(jsonObject.get("names").toString());
            txtUser.setText(jsonObject.get("user").toString());
            txtApellido.setText(jsonObject.get("lastnames").toString());
            txtCorreo.setText(jsonObject.get("email").toString());
        } else {

//            Toast.makeText(ProfileFragment, "Error", Toast.LENGTH_SHORT).show();

        }
    }
}