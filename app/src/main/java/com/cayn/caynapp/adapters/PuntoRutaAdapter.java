package com.cayn.caynapp.adapters;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cayn.caynapp.R;

import java.util.ArrayList;

public class PuntoRutaAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private ArrayList<String> puntos;
    public PuntoRutaAdapter(Context context, int layout, ArrayList<String> puntos){
        this.context = context;
        this.layout = layout;
        this.puntos = puntos;
    }

    @Override
    public int getCount() {
        return this.puntos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.puntos.get(position);
    }

    @Override
    public long getItemId(int id) {
        return id;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup viewGroup) {
        View v = convertView;

        LayoutInflater layoutInflater = LayoutInflater.from(this.context);

        v= layoutInflater.inflate(R.layout.item_list_ruta, null);

        String currentName  = puntos.get(position);

        TextView direccion = (TextView) v.findViewById(R.id.tv_nombre_punto);
        direccion.setText(currentName);
        return v;
    }
}