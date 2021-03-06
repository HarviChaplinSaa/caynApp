// Generated by view binder compiler. Do not edit!
package com.cayn.caynapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cayn.caynapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class CardEventoPropio2Binding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final LinearLayout cvEventoPropio;

  @NonNull
  public final ImageView ivEvento;

  @NonNull
  public final TextView tvNombreEvento;

  private CardEventoPropio2Binding(@NonNull LinearLayout rootView,
      @NonNull LinearLayout cvEventoPropio, @NonNull ImageView ivEvento,
      @NonNull TextView tvNombreEvento) {
    this.rootView = rootView;
    this.cvEventoPropio = cvEventoPropio;
    this.ivEvento = ivEvento;
    this.tvNombreEvento = tvNombreEvento;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static CardEventoPropio2Binding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static CardEventoPropio2Binding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.card_evento_propio2, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static CardEventoPropio2Binding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      LinearLayout cvEventoPropio = (LinearLayout) rootView;

      id = R.id.iv_evento;
      ImageView ivEvento = ViewBindings.findChildViewById(rootView, id);
      if (ivEvento == null) {
        break missingId;
      }

      id = R.id.tv_nombre_evento;
      TextView tvNombreEvento = ViewBindings.findChildViewById(rootView, id);
      if (tvNombreEvento == null) {
        break missingId;
      }

      return new CardEventoPropio2Binding((LinearLayout) rootView, cvEventoPropio, ivEvento,
          tvNombreEvento);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
