package com.cayn.caynapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cayn.caynapp.R;
import com.cayn.caynapp.modelos.Evento;

import java.util.List;

public class ListEventoAdapter extends RecyclerView.Adapter{

    private List<Evento> mData;
    private OnItemClickListener OnItemClickListener;


    public ListEventoAdapter(List<Evento> itemList){
        this.mData = itemList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imagen;
        TextView nombreEvento, fechaInicio, fechaFin;

        ViewHolder(View itemView){
            super(itemView);
            nombreEvento = itemView.findViewById(R.id.tv_nombre_evento);
            fechaInicio = itemView.findViewById(R.id.tv_fecha_inicio_evento);
            fechaFin = itemView.findViewById(R.id.tv_fecha_fin_evento);
            imagen = itemView.findViewById(R.id.iv_evento);
        }

//        void binData(final Evento item){

//        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mivista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_evento_participado, parent, false);
        return new ListEventoAdapter.ViewHolder(mivista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            final Evento miEvento = mData.get(position);
            ListEventoAdapter.ViewHolder miHolder = (ListEventoAdapter.ViewHolder) holder;
            miHolder.nombreEvento.setText(miEvento.getNombre());
            miHolder.fechaFin.setText(miEvento.getCantidad_asistentes());
            miHolder.fechaInicio.setText(miEvento.getFecha_inicio());
            Glide.with(miHolder.imagen.getContext()).load(miEvento.getImagen()).into(miHolder.imagen);

        miHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(OnItemClickListener!= null){
                    OnItemClickListener.OnItemClick(miEvento)   ;
                }
            }
        });
    }

    @Override
    public int getItemCount() {return mData.size();}

    public interface OnItemClickListener{
        void OnItemClick(Evento miEvento);
    }

    public void setOnItemClickListener(ListEventoAdapter.OnItemClickListener onItemClickListener) {
        OnItemClickListener = onItemClickListener;
    }

    public void setListadoEvento(List<Evento> listadoEvento) {
        this.mData = listadoEvento;
        notifyDataSetChanged();
    }



}
