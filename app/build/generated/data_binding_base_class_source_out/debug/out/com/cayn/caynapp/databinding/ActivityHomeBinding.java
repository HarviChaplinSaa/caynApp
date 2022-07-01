// Generated by view binder compiler. Do not edit!
package com.cayn.caynapp.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.cayn.caynapp.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomeBinding implements ViewBinding {
  @NonNull
  private final DrawerLayout rootView;

  @NonNull
  public final AppBarHomeBinding appBarHome;

  @NonNull
  public final FragmentDrawerBinding appDrawer;

  @NonNull
  public final FragmentMainTabsBinding appMainTabs;

  @NonNull
  public final DrawerLayout drawerLayout;

  private ActivityHomeBinding(@NonNull DrawerLayout rootView, @NonNull AppBarHomeBinding appBarHome,
      @NonNull FragmentDrawerBinding appDrawer, @NonNull FragmentMainTabsBinding appMainTabs,
      @NonNull DrawerLayout drawerLayout) {
    this.rootView = rootView;
    this.appBarHome = appBarHome;
    this.appDrawer = appDrawer;
    this.appMainTabs = appMainTabs;
    this.drawerLayout = drawerLayout;
  }

  @Override
  @NonNull
  public DrawerLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.app_bar_home;
      View appBarHome = ViewBindings.findChildViewById(rootView, id);
      if (appBarHome == null) {
        break missingId;
      }
      AppBarHomeBinding binding_appBarHome = AppBarHomeBinding.bind(appBarHome);

      id = R.id.app_drawer;
      View appDrawer = ViewBindings.findChildViewById(rootView, id);
      if (appDrawer == null) {
        break missingId;
      }
      FragmentDrawerBinding binding_appDrawer = FragmentDrawerBinding.bind(appDrawer);

      id = R.id.app_main_tabs;
      View appMainTabs = ViewBindings.findChildViewById(rootView, id);
      if (appMainTabs == null) {
        break missingId;
      }
      FragmentMainTabsBinding binding_appMainTabs = FragmentMainTabsBinding.bind(appMainTabs);

      DrawerLayout drawerLayout = (DrawerLayout) rootView;

      return new ActivityHomeBinding((DrawerLayout) rootView, binding_appBarHome, binding_appDrawer,
          binding_appMainTabs, drawerLayout);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}