// Generated by view binder compiler. Do not edit!
package com.cayn.caynapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cayn.caynapp.R;
import com.google.android.material.textfield.TextInputEditText;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegisterBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final Button btnRegister;

  @NonNull
  public final SeekBar dbEdad;

  @NonNull
  public final FrameLayout frameLayout;

  @NonNull
  public final FrameLayout frameLayout2;

  @NonNull
  public final LinearLayout linearLayout;

  @NonNull
  public final Spinner spGenero;

  @NonNull
  public final TextInputEditText teEmail;

  @NonNull
  public final TextInputEditText teName;

  @NonNull
  public final TextInputEditText teNit;

  @NonNull
  public final TextInputEditText tePassword;

  @NonNull
  public final TextInputEditText tePasswordAux;

  @NonNull
  public final TextView tvEdadEdit;

  @NonNull
  public final TextView tvIniciaSesion;

  private ActivityRegisterBinding(@NonNull ConstraintLayout rootView, @NonNull Button btnRegister,
      @NonNull SeekBar dbEdad, @NonNull FrameLayout frameLayout, @NonNull FrameLayout frameLayout2,
      @NonNull LinearLayout linearLayout, @NonNull Spinner spGenero,
      @NonNull TextInputEditText teEmail, @NonNull TextInputEditText teName,
      @NonNull TextInputEditText teNit, @NonNull TextInputEditText tePassword,
      @NonNull TextInputEditText tePasswordAux, @NonNull TextView tvEdadEdit,
      @NonNull TextView tvIniciaSesion) {
    this.rootView = rootView;
    this.btnRegister = btnRegister;
    this.dbEdad = dbEdad;
    this.frameLayout = frameLayout;
    this.frameLayout2 = frameLayout2;
    this.linearLayout = linearLayout;
    this.spGenero = spGenero;
    this.teEmail = teEmail;
    this.teName = teName;
    this.teNit = teNit;
    this.tePassword = tePassword;
    this.tePasswordAux = tePasswordAux;
    this.tvEdadEdit = tvEdadEdit;
    this.tvIniciaSesion = tvIniciaSesion;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegisterBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_register, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegisterBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_register;
      Button btnRegister = ViewBindings.findChildViewById(rootView, id);
      if (btnRegister == null) {
        break missingId;
      }

      id = R.id.db_edad;
      SeekBar dbEdad = ViewBindings.findChildViewById(rootView, id);
      if (dbEdad == null) {
        break missingId;
      }

      id = R.id.frameLayout;
      FrameLayout frameLayout = ViewBindings.findChildViewById(rootView, id);
      if (frameLayout == null) {
        break missingId;
      }

      id = R.id.frameLayout2;
      FrameLayout frameLayout2 = ViewBindings.findChildViewById(rootView, id);
      if (frameLayout2 == null) {
        break missingId;
      }

      id = R.id.linearLayout;
      LinearLayout linearLayout = ViewBindings.findChildViewById(rootView, id);
      if (linearLayout == null) {
        break missingId;
      }

      id = R.id.sp_genero;
      Spinner spGenero = ViewBindings.findChildViewById(rootView, id);
      if (spGenero == null) {
        break missingId;
      }

      id = R.id.te_email;
      TextInputEditText teEmail = ViewBindings.findChildViewById(rootView, id);
      if (teEmail == null) {
        break missingId;
      }

      id = R.id.te_name;
      TextInputEditText teName = ViewBindings.findChildViewById(rootView, id);
      if (teName == null) {
        break missingId;
      }

      id = R.id.te_nit;
      TextInputEditText teNit = ViewBindings.findChildViewById(rootView, id);
      if (teNit == null) {
        break missingId;
      }

      id = R.id.te_password;
      TextInputEditText tePassword = ViewBindings.findChildViewById(rootView, id);
      if (tePassword == null) {
        break missingId;
      }

      id = R.id.te_passwordAux;
      TextInputEditText tePasswordAux = ViewBindings.findChildViewById(rootView, id);
      if (tePasswordAux == null) {
        break missingId;
      }

      id = R.id.tv_edad_edit;
      TextView tvEdadEdit = ViewBindings.findChildViewById(rootView, id);
      if (tvEdadEdit == null) {
        break missingId;
      }

      id = R.id.tv_inicia_sesion;
      TextView tvIniciaSesion = ViewBindings.findChildViewById(rootView, id);
      if (tvIniciaSesion == null) {
        break missingId;
      }

      return new ActivityRegisterBinding((ConstraintLayout) rootView, btnRegister, dbEdad,
          frameLayout, frameLayout2, linearLayout, spGenero, teEmail, teName, teNit, tePassword,
          tePasswordAux, tvEdadEdit, tvIniciaSesion);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
