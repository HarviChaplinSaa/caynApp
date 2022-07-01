package com.cayn.caynapp.HomeFragments.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.cayn.caynapp.DetailEventActivity;
import com.cayn.caynapp.EventActivity;
import com.cayn.caynapp.HomeActivity;
import com.cayn.caynapp.R;
import com.cayn.caynapp.RoutesActivity;
import com.cayn.caynapp.adapters.ListEventoAdapter;
import com.cayn.caynapp.adapters.ListEventoPropioAdapter;
import com.cayn.caynapp.databinding.FragmentHomeBinding;
import com.cayn.caynapp.modelos.Evento;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

public class HomeFragment<FragmentHomeBinding> extends Fragment {

    private FragmentHomeBinding binding;

    private TextView tvNombreEvenDes, tvFechaIniEvenDes, tvFechaFinEvenDes, tvVerTodas, tvSinEventosPropios;
    private ImageView ivEvenDes;
    private RecyclerView rvEventosPropios, rvEventos;
    private LinearLayout llSinEventosParticipados;

    private List<Evento> eventosUsuario; //eventos usuario
    private List<Evento> eventosParticipados; //eventos en los que va a participar el usuario
    private Evento eventoDestacado = new Evento(); //evento destacado

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        //CONFIGURACION INICIAL
        preferences = this.getContext().getSharedPreferences("sesion", Context.MODE_PRIVATE);
        editor = preferences.edit();

        //union de variables del drawer
        rvEventosPropios = (RecyclerView) root.findViewById(R.id.rv_eventos_propios);
        rvEventos = (RecyclerView) root.findViewById(R.id.rv_eventos_participados);
        ivEvenDes = (ImageView) root.findViewById(R.id.iv_evento_destacado);
        tvNombreEvenDes = (TextView) root.findViewById(R.id.tv_nombre_evento_destacado);
        tvFechaIniEvenDes = (TextView) root.findViewById(R.id.tv_fecha_inicio_evento_destacado);
        tvFechaFinEvenDes = (TextView) root.findViewById(R.id.tv_fecha_fin_evento_destacado);
        llSinEventosParticipados = (LinearLayout) root.findViewById(R.id.ll_sin_eventos);
        tvVerTodas = (TextView) root.findViewById(R.id.txt_ver_todas);
        tvSinEventosPropios = (TextView) root.findViewById(R.id.tv_texto_sin_eventos_creador);

        //configuracion inicial
        tvVerTodas.setText(Html.fromHtml("<u>"+"Ver todas"+"</u>"));

        //METODOS
        //metodo inicial
        setUp(this.preferences.getString("idUser", ""));
        return root;

    }

    public void setUp(String idUser){
        eventosUsuario = new ArrayList<>();
        eventosParticipados = new ArrayList<>();
        db.collection("eventos")
                .whereEqualTo("id_creador", idUser)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot user : task.getResult()) {
                                Evento evento = new Evento();
                                evento.setId_evento(user.getId());
                                evento.setDescripcion(user.getData().get("descripcion").toString());
                                evento.setFecha_fin(user.getData().get("fecha_fin").toString());
                                evento.setFecha_inicio(user.getData().get("fecha_inicio").toString());
                                evento.setHora_fin(user.getData().get("hora_fin").toString());
                                evento.setHora_inicio(user.getData().get("hora_inicio").toString());
                                List<String> idAsistentes = (ArrayList<String>) user.getData().get("id_asistentes");
                                evento.setId_asistentes(idAsistentes);
                                evento.setId_creador(user.getData().get("id_creador").toString());
                                evento.setImagen(user.getData().get("imagen").toString());
                                evento.setNombre(user.getData().get("nombre").toString());
                                evento.setRuta(user.getData().get("ruta").toString());
                                evento.setTipo((Boolean) user.getData().get("tipo"));
                                evento.setCantidad_asistentes(user.getData().get("cantidad_asistentes").toString());
                                eventosUsuario.add(evento);
                            }
                        }
                        if(eventosUsuario.size() == 0){
                            tvSinEventosPropios.setVisibility(View.VISIBLE);
                            rvEventosPropios.setVisibility(View.GONE);
                        }
                        ListEventoPropioAdapter listAdapter = new ListEventoPropioAdapter(eventosUsuario);
                        rvEventosPropios.setAdapter(listAdapter);
                        rvEventosPropios.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
                        rvEventosPropios.setHasFixedSize(true);

                        listAdapter.setOnItemClickListener(new ListEventoPropioAdapter.OnItemClickListener() {
                            @Override
                            public void OnItemClick(Evento miEvento) {
                                Intent i = new Intent(getContext(), RoutesActivity.class);
                                i.putExtra("evento", miEvento);
                                startActivity(i);
                            }
                        });
                    }
                });

        db.collection("eventos")
                .orderBy("cantidad_asistentes", Query.Direction.DESCENDING).limit(1)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for (QueryDocumentSnapshot eventos : task.getResult()){
                                List<String> idAsistentes = (ArrayList<String>) eventos.getData().get("id_asistentes");
                                    if(!eventos.getData().get("id_creador").equals(idUser)){
                                        eventoDestacado.setId_evento(eventos.getId());
                                        eventoDestacado.setDescripcion(eventos.getData().get("descripcion").toString());
                                        eventoDestacado.setFecha_fin(eventos.getData().get("fecha_fin").toString());
                                        eventoDestacado.setFecha_inicio(eventos.getData().get("fecha_inicio").toString());
                                        eventoDestacado.setHora_fin(eventos.getData().get("hora_fin").toString());
                                        eventoDestacado.setHora_inicio(eventos.getData().get("hora_inicio").toString());
                                        eventoDestacado.setId_asistentes(idAsistentes);
                                        eventoDestacado.setId_creador(eventos.getData().get("id_creador").toString());
                                        eventoDestacado.setImagen(eventos.getData().get("imagen").toString());
                                        eventoDestacado.setNombre(eventos.getData().get("nombre").toString());
                                        eventoDestacado.setRuta(eventos.getData().get("ruta").toString());
                                        eventoDestacado.setCantidad_asistentes(eventos.getData().get("cantidad_asistentes").toString());
                                    }
                            }
                            Glide.with(ivEvenDes.getContext()).load(eventoDestacado.getImagen()).into(ivEvenDes);
                            tvNombreEvenDes.setText(eventoDestacado.getNombre().toUpperCase(Locale.ROOT));
                            tvFechaIniEvenDes.setText(eventoDestacado.getFecha_inicio());
                            tvFechaFinEvenDes.setText(eventoDestacado.getCantidad_asistentes());
                        }

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure: "+e );
                    }
                });

        db.collection("eventos")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()){
                            for(QueryDocumentSnapshot eventos : task.getResult()){
                                List<String> idAsistentes = (ArrayList<String>) eventos.getData().get("id_asistentes");
                                if(idAsistentes.contains(idUser) && !eventos.getData().get("id_creador").equals(idUser)){
                                    Evento evento = new Evento();
                                    evento.setId_evento(eventos.getId());
                                    evento.setDescripcion(eventos.getData().get("descripcion").toString());
                                    evento.setFecha_fin(eventos.getData().get("fecha_fin").toString());
                                    evento.setFecha_inicio(eventos.getData().get("fecha_inicio").toString());
                                    evento.setHora_fin(eventos.getData().get("hora_fin").toString());
                                    evento.setHora_inicio(eventos.getData().get("hora_inicio").toString());
                                    evento.setId_asistentes(idAsistentes);
                                    evento.setId_creador(eventos.getData().get("id_creador").toString());
                                    evento.setImagen(eventos.getData().get("imagen").toString());
                                    evento.setNombre(eventos.getData().get("nombre").toString());
                                    evento.setRuta(eventos.getData().get("ruta").toString());
                                    evento.setTipo((Boolean) eventos.getData().get("tipo"));
                                    evento.setCantidad_asistentes(eventos.getData().get("cantidad_asistentes").toString());
                                    eventosParticipados.add(evento);
                                }
                            }
                        }
                        if(eventosParticipados.size()==0){
                            llSinEventosParticipados.setVisibility(View.VISIBLE);
                            rvEventos.setVisibility(View.GONE);
                        }else{
                            llSinEventosParticipados.setVisibility(View.GONE);
                            rvEventos.setVisibility(View.VISIBLE);
                            ListEventoAdapter listAdapter = new ListEventoAdapter(eventosParticipados);
                            rvEventos.setHasFixedSize(true);
                            rvEventos.setLayoutManager(new LinearLayoutManager(getContext()));
                            rvEventos.setAdapter(listAdapter);

                            listAdapter.setOnItemClickListener(new ListEventoAdapter.OnItemClickListener() {
                                @Override
                                public void OnItemClick(Evento miEvento) {
                                    Intent i = new Intent(getContext(), DetailEventActivity.class);
                                    i.putExtra("evento", miEvento);
                                    startActivity(i);
                                }
                            });
                        }
                    }
                });

        //ver todas las rutas
        tvVerTodas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), RoutesActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}