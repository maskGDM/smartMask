package com.example.smartmask;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.hdodenhof.circleimageview.CircleImageView;

public class Profile1 extends AppCompatActivity {
    String jsonDataUser = "{}";
    TextView txtNombre, txtUser, txtApellido,txtCorreo;
    CircleImageView imgUser;
    TextView aux;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile1);
        Bundle bundle = this.getIntent().getExtras();
        String result=getIntent().getExtras().getString("Session");
          System.out.println("valor "+result);
        jsonDataUser = (result);
        Log.i("Logs", "SMART MASK");
        if (!jsonDataUser.equals("")) {
            Log.i("Logs", jsonDataUser);
            initialize();
            initdata();

        } else {
//                Toast.makeText(NavigationActivity.this, "NO HA INICIADO SESIÓN", Toast.LENGTH_LONG).show();
//                killSession();
        }

    }

    public void initialize() {
        txtNombre = findViewById(R.id.txtMyNombre1);
        txtUser = findViewById(R.id.txtMyUser1);
        txtApellido = findViewById(R.id.txtMyApellido1);
        txtCorreo=findViewById(R.id.txtMyCorreo1);
        aux=findViewById(R.id.asad);
        imgUser=findViewById(R.id.imgUser);
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