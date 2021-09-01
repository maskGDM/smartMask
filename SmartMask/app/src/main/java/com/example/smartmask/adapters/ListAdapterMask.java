package com.example.smartmask.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.smartmask.R;
import com.example.smartmask.models.MaskModel;

import java.util.ArrayList;
import java.util.List;

public class ListAdapterMask extends RecyclerView.Adapter<ListAdapterMask.ViewHolder> implements  View.OnClickListener{
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
                Toast.makeText(context,"Selecciona: " +
                                mData.get(position).getMask_code(),
                        Toast.LENGTH_SHORT).show();

                //  interfacecommunicates_Fragments.SendPharmacy(listpharmacy.get(rvListPharmacy.getChildAdapterPosition(view)));
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

}
