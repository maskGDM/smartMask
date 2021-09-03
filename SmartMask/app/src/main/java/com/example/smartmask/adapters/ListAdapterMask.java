package com.example.smartmask.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.smartmask.AirQuiality;
import com.example.smartmask.R;
import com.example.smartmask.Settings;
import com.example.smartmask.models.MaskModel;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListAdapterMask extends RecyclerView.Adapter<ListAdapterMask.ViewHolder> implements  View.OnClickListener{

    private String URL = "https://aplicaciones.uteq.edu.ec/smartmask/webapis/";
    private RequestQueue requestQueue;

    private List<MaskModel> mData;
    private LayoutInflater mInflater;
    private Context context;
    private View.OnClickListener listener;


    public ListAdapterMask(List<MaskModel> itemList, Context context){
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.mData = itemList;
    }


    @Override
    public ListAdapterMask.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = mInflater.inflate(R.layout.lista, null);
        view.setOnClickListener(this);
        return new ListAdapterMask.ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(final ListAdapterMask.ViewHolder holder, final int position){

       // holder.bindData(mData.get(position));
        holder.mask_code.setText(mData.get(position).getMask_code());
        holder.dateadded.setText(mData.get(position).getDateadded());

        Boolean switchState = holder.Accion.isChecked();

        Glide.with(context).load("https://www.elmundo.cr/wp-content/uploads/2020/05/WhatsApp-Image-2020-05-20-at-13.19.19.jpeg").into(holder.imgfoto);

        holder.Accion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             /*   Toast.makeText(context,"Selecciona: " +
                                mData.get(position).getMask_code(),
                        Toast.LENGTH_SHORT).show(); */

               if(!holder.Accion.isChecked()){
                   Log.d("checked","Desactivado");
                   for(int i=0;i<mData.size();i++){
                        if(mData.get(position).getMask_code() == mData.get(i).getMask_code()){
                            String jsoncodemask = "{\n" +
                                    "    \"mac\": \"" + mData.get(position).getMask_code() + "\"\n" +
                                    "}";
                            Log.i("Logs", jsoncodemask);
                            desactivatemask(jsoncodemask);
                            mData.remove(i);
                            break;
                        }
                    }
                    notifyDataSetChanged();
               }


            }
        });
    }

    @Override
    public int getItemCount() { return mData.size(); }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView imgfoto;
        TextView mask_code, dateadded;
        Switch Accion;

        ViewHolder(View itemsView) {
            super(itemsView);
            imgfoto = itemView.findViewById(R.id.imgfoto);
            mask_code = itemView.findViewById(R.id.mask_code);
            dateadded = itemView.findViewById(R.id.dateadded);
            Accion = (Switch) itemsView.findViewById(R.id.Accion);
        }
    }

    public void filter(ArrayList<MaskModel> filtermask){
        this.mData = filtermask;
        notifyDataSetChanged();
    }

    private void desactivatemask(String datajson) {


        StringRequest request = new StringRequest(
                Request.Method.POST,
                URL + "maskapis/deleteMask/",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int size = response.length();
                        boolean band = false;
                        response = fixEncoding(response);

                        JSONObject json_transform = null;

                        try {
                            if (size > 0)
                            {   json_transform = new JSONObject(response);
                                if(json_transform.getString("status").equals("2")){
                                    Toast.makeText(context, json_transform.getString("information"), Toast.LENGTH_LONG).show();
                                }else{
                                    Toast.makeText(context, json_transform.getString("information"), Toast.LENGTH_LONG).show();
                                }
                            }else {
                                Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
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
            requestQueue = Volley.newRequestQueue(context);
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

}
