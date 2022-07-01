// Generated by view binder compiler. Do not edit!
package com.cayn.caynapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cayn.caynapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView ivEventoDestacado;

  @NonNull
  public final LinearLayout llSinEventos;

  @NonNull
  public final RecyclerView rvEventosParticipados;

  @NonNull
  public final RecyclerView rvEventosPropios;

  @NonNull
  public final ScrollView scrollView2;

  @NonNull
  public final TextView tvFechaFinEventoDestacado;

  @NonNull
  public final TextView tvFechaInicioEventoDestacado;

  @NonNull
  public final TextView tvNombreEventoDestacado;

  @NonNull
  public final TextView tvTextoSinEventosCreador;

  @NonNull
  public final TextView txtVerTodas;

  private FragmentHomeBinding(@NonNull ConstraintLayout rootView,
      @NonNull ImageView ivEventoDestacado, @NonNull LinearLayout llSinEventos,
      @NonNull RecyclerView rvEventosParticipados, @NonNull RecyclerView rvEventosPropios,
      @NonNull ScrollView scrollView2, @NonNull TextView tvFechaFinEventoDestacado,
      @NonNull TextView tvFechaInicioEventoDestacado, @NonNull TextView tvNombreEventoDestacado,
      @NonNull TextView tvTextoSinEventosCreador, @NonNull TextView txtVerTodas) {
    this.rootView = rootView;
    this.ivEventoDestacado = ivEventoDestacado;
    this.llSinEventos = llSinEventos;
    this.rvEventosParticipados = rvEventosParticipados;
    this.rvEventosPropios = rvEventosPropios;
    this.scrollView2 = scrollView2;
    this.tvFechaFinEventoDestacado = tvFechaFinEventoDestacado;
    this.tvFechaInicioEventoDestacado = tvFechaInicioEventoDestacado;
    this.tvNombreEventoDestacado = tvNombreEventoDestacado;
    this.tvTextoSinEventosCreador = tvTextoSinEventosCreador;
    this.txtVerTodas = txtVerTodas;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.iv_evento_destacado;
      ImageView ivEventoDestacado = ViewBindings.findChildViewById(rootView, id);
      if (ivEventoDestacado == null) {
        break missingId;
      }

      id = R.id.ll_sin_eventos;
      LinearLayout llSinEventos = ViewBindings.findChildViewById(rootView, id);
      if (llSinEventos == null) {
        break missingId;
      }

      id = R.id.rv_eventos_participados;
      RecyclerView rvEventosParticipados = ViewBindings.findChildViewById(rootView, id);
      if (rvEventosParticipados == null) {
        break missingId;
      }

      id = R.id.rv_eventos_propios;
      RecyclerView rvEventosPropios = ViewBindings.findChildViewById(rootView, id);
      if (rvEventosPropios == null) {
        break missingId;
      }

      id = R.id.scrollView2;
      ScrollView scrollView2 = ViewBindings.findChildViewById(rootView, id);
      if (scrollView2 == null) {
        break missingId;
      }

      id = R.id.tv_fecha_fin_evento_destacado;
      TextView tvFechaFinEventoDestacado = ViewBindings.findChildViewById(rootView, id);
      if (tvFechaFinEventoDestacado == null) {
        break missingId;
      }

      id = R.id.tv_fecha_inicio_evento_destacado;
      TextView tvFechaInicioEventoDestacado = ViewBindings.findChildViewById(rootView, id);
      if (tvFechaInicioEventoDestacado == null) {
        break missingId;
      }

      id = R.id.tv_nombre_evento_destacado;
      TextView tvNombreEventoDestacado = ViewBindings.findChildViewById(rootView, id);
      if (tvNombreEventoDestacado == null) {
        break missingId;
      }

      id = R.id.tv_texto_sin_eventos_creador;
      TextView tvTextoSinEventosCreador = ViewBindings.findChildViewById(rootView, id);
      if (tvTextoSinEventosCreador == null) {
        break missingId;
      }

      id = R.id.txt_ver_todas;
      TextView txtVerTodas = ViewBindings.findChildViewById(rootView, id);
      if (txtVerTodas == null) {
        break missingId;
      }

      return new FragmentHomeBinding((ConstraintLayout) rootView, ivEventoDestacado, llSinEventos,
          rvEventosParticipados, rvEventosPropios, scrollView2, tvFechaFinEventoDestacado,
          tvFechaInicioEventoDestacado, tvNombreEventoDestacado, tvTextoSinEventosCreador,
          txtVerTodas);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
