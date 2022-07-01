// Generated by view binder compiler. Do not edit!
package com.cayn.caynapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cayn.caynapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEditRouteBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final AppBarLayout appBarLayout;

  @NonNull
  public final Button btnAceptar;

  @NonNull
  public final ImageButton btnBackModal;

  @NonNull
  public final Button btnCancelar;

  @NonNull
  public final Button btnEditar;

  @NonNull
  public final ImageButton btnGoBack;

  @NonNull
  public final DatePicker dpFecha;

  @NonNull
  public final TextInputEditText etNombreEvento;

  @NonNull
  public final ImageButton ibCerrarModal;

  @NonNull
  public final LinearLayout linearLayout2;

  @NonNull
  public final LinearLayout linearLayout3;

  @NonNull
  public final LinearLayout linearLayout4;

  @NonNull
  public final LinearLayout linearLayout7;

  @NonNull
  public final LinearLayout llTipoEvento;

  @NonNull
  public final Switch swTipoEvento;

  @NonNull
  public final TextInputLayout textInputLayout;

  @NonNull
  public final Toolbar toolbar;

  @NonNull
  public final TextView tvEditarRutas;

  @NonNull
  public final TextView tvFechaEvento;

  @NonNull
  public final TextView tvHeaderText;

  @NonNull
  public final TextView tvTextoModalConfirmacion;

  private ActivityEditRouteBinding(@NonNull ConstraintLayout rootView,
      @NonNull AppBarLayout appBarLayout, @NonNull Button btnAceptar,
      @NonNull ImageButton btnBackModal, @NonNull Button btnCancelar, @NonNull Button btnEditar,
      @NonNull ImageButton btnGoBack, @NonNull DatePicker dpFecha,
      @NonNull TextInputEditText etNombreEvento, @NonNull ImageButton ibCerrarModal,
      @NonNull LinearLayout linearLayout2, @NonNull LinearLayout linearLayout3,
      @NonNull LinearLayout linearLayout4, @NonNull LinearLayout linearLayout7,
      @NonNull LinearLayout llTipoEvento, @NonNull Switch swTipoEvento,
      @NonNull TextInputLayout textInputLayout, @NonNull Toolbar toolbar,
      @NonNull TextView tvEditarRutas, @NonNull TextView tvFechaEvento,
      @NonNull TextView tvHeaderText, @NonNull TextView tvTextoModalConfirmacion) {
    this.rootView = rootView;
    this.appBarLayout = appBarLayout;
    this.btnAceptar = btnAceptar;
    this.btnBackModal = btnBackModal;
    this.btnCancelar = btnCancelar;
    this.btnEditar = btnEditar;
    this.btnGoBack = btnGoBack;
    this.dpFecha = dpFecha;
    this.etNombreEvento = etNombreEvento;
    this.ibCerrarModal = ibCerrarModal;
    this.linearLayout2 = linearLayout2;
    this.linearLayout3 = linearLayout3;
    this.linearLayout4 = linearLayout4;
    this.linearLayout7 = linearLayout7;
    this.llTipoEvento = llTipoEvento;
    this.swTipoEvento = swTipoEvento;
    this.textInputLayout = textInputLayout;
    this.toolbar = toolbar;
    this.tvEditarRutas = tvEditarRutas;
    this.tvFechaEvento = tvFechaEvento;
    this.tvHeaderText = tvHeaderText;
    this.tvTextoModalConfirmacion = tvTextoModalConfirmacion;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEditRouteBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEditRouteBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_edit_route, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEditRouteBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.appBarLayout;
      AppBarLayout appBarLayout = ViewBindings.findChildViewById(rootView, id);
      if (appBarLayout == null) {
        break missingId;
      }

      id = R.id.btn_aceptar;
      Button btnAceptar = ViewBindings.findChildViewById(rootView, id);
      if (btnAceptar == null) {
        break missingId;
      }

      id = R.id.btn_back_modal;
      ImageButton btnBackModal = ViewBindings.findChildViewById(rootView, id);
      if (btnBackModal == null) {
        break missingId;
      }

      id = R.id.btn_cancelar;
      Button btnCancelar = ViewBindings.findChildViewById(rootView, id);
      if (btnCancelar == null) {
        break missingId;
      }

      id = R.id.btn_editar;
      Button btnEditar = ViewBindings.findChildViewById(rootView, id);
      if (btnEditar == null) {
        break missingId;
      }

      id = R.id.btn_go_back;
      ImageButton btnGoBack = ViewBindings.findChildViewById(rootView, id);
      if (btnGoBack == null) {
        break missingId;
      }

      id = R.id.dp_fecha;
      DatePicker dpFecha = ViewBindings.findChildViewById(rootView, id);
      if (dpFecha == null) {
        break missingId;
      }

      id = R.id.et_nombre_evento;
      TextInputEditText etNombreEvento = ViewBindings.findChildViewById(rootView, id);
      if (etNombreEvento == null) {
        break missingId;
      }

      id = R.id.ib_cerrar_modal;
      ImageButton ibCerrarModal = ViewBindings.findChildViewById(rootView, id);
      if (ibCerrarModal == null) {
        break missingId;
      }

      id = R.id.linearLayout2;
      LinearLayout linearLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout2 == null) {
        break missingId;
      }

      id = R.id.linearLayout3;
      LinearLayout linearLayout3 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout3 == null) {
        break missingId;
      }

      id = R.id.linearLayout4;
      LinearLayout linearLayout4 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout4 == null) {
        break missingId;
      }

      id = R.id.linearLayout7;
      LinearLayout linearLayout7 = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout7 == null) {
        break missingId;
      }

      id = R.id.ll_tipo_evento;
      LinearLayout llTipoEvento = ViewBindings.findChildViewById(rootView, id);
      if (llTipoEvento == null) {
        break missingId;
      }

      id = R.id.sw_tipo_evento;
      Switch swTipoEvento = ViewBindings.findChildViewById(rootView, id);
      if (swTipoEvento == null) {
        break missingId;
      }

      id = R.id.textInputLayout;
      TextInputLayout textInputLayout = ViewBindings.findChildViewById(rootView, id);
      if (textInputLayout == null) {
        break missingId;
      }

      id = R.id.toolbar;
      Toolbar toolbar = ViewBindings.findChildViewById(rootView, id);
      if (toolbar == null) {
        break missingId;
      }

      id = R.id.tv_editar_rutas;
      TextView tvEditarRutas = ViewBindings.findChildViewById(rootView, id);
      if (tvEditarRutas == null) {
        break missingId;
      }

      id = R.id.tv_fecha_evento;
      TextView tvFechaEvento = ViewBindings.findChildViewById(rootView, id);
      if (tvFechaEvento == null) {
        break missingId;
      }

      id = R.id.tv_headerText;
      TextView tvHeaderText = ViewBindings.findChildViewById(rootView, id);
      if (tvHeaderText == null) {
        break missingId;
      }

      id = R.id.tv_texto_modal_confirmacion;
      TextView tvTextoModalConfirmacion = ViewBindings.findChildViewById(rootView, id);
      if (tvTextoModalConfirmacion == null) {
        break missingId;
      }

      return new ActivityEditRouteBinding((ConstraintLayout) rootView, appBarLayout, btnAceptar,
          btnBackModal, btnCancelar, btnEditar, btnGoBack, dpFecha, etNombreEvento, ibCerrarModal,
          linearLayout2, linearLayout3, linearLayout4, linearLayout7, llTipoEvento, swTipoEvento,
          textInputLayout, toolbar, tvEditarRutas, tvFechaEvento, tvHeaderText,
          tvTextoModalConfirmacion);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
