package com.cayn.caynapp.adapters;

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

public class ListEventoPropioAdapter extends RecyclerView.Adapter {

    private List<Evento> mData;
    private OnItemClickListener OnItemClickListener;

    public ListEventoPropioAdapter(List<Evento> listadoEventos) {
        this.mData = listadoEventos;
    }

    public class EventoViewHolder extends RecyclerView.ViewHolder{

        ImageView imagen;
        TextView nombreEvento;

        public EventoViewHolder(@NonNull View itemView) {
            super(itemView);
            nombreEvento = itemView.findViewById(R.id.tv_nombre_evento);
            imagen = itemView.findViewById(R.id.iv_evento);
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mivista = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_evento_propio, parent, false);
        return new EventoViewHolder(mivista);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final Evento miEvento = mData.get(position);
        EventoViewHolder miHolder = (EventoViewHolder) holder;
        miHolder.nombreEvento.setText(miEvento.getNombre());
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
    public int getItemCount() {
        return mData.size();
    }



    public interface OnItemClickListener{
        void OnItemClick(Evento miEvento);
    }


    public void setOnItemClickListener(ListEventoPropioAdapter.OnItemClickListener onItemClickListener) {
        OnItemClickListener = onItemClickListener;
    }


    public void setListadoEvento(List<Evento> listadoEvento) {
        this.mData = listadoEvento;
        notifyDataSetChanged();
    }
}
