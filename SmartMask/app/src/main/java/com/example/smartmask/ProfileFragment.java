package com.example.smartmask;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProfileFragment extends Fragment {
    String jsonDataUser = "{}";
    TextView txtNombre, txtUser, txtApellido,txtCorreo;
    ImageView imgUser;
    View view;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public ProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProfileFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mParam1 = getArguments().getString(ARG_PARAM1);
        mParam2 = getArguments().getString(ARG_PARAM2);
          Bundle  bundle = this.getArguments();
          Intent intent = new Intent(getActivity(), MainActivity.class);
//           intent.putExtras(bundle);
          startActivity(intent);

//        Bundle bundle = new Bundle();
//        String myMessage = "Mi mensaje";
//        bundle.putString("message", myMessage );
//        UsuarioFragment userFragment = new UsuarioFragment();
//        userFragment.setArguments(bundle);
//        transaction.replace(RESOURCE_ID, userFragment);
//        transaction.commit();

            jsonDataUser = (bundle.getString("Session"));
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

    public void initialize() {
        
        txtNombre = view.findViewById(R.id.txtNombre);
        txtUser = view.findViewById(R.id.txtUser);
        txtApellido = view.findViewById(R.id.txtApellido);
        txtCorreo=view.findViewById(R.id.txtCorreo);

        imgUser=view.findViewById(R.id.imgUser);
        
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);


    }
}