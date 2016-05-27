package com.mikmat.auha30staff;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mikmat.auha30staff.Models.Baby;

public class BabyDetailDrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = BabyDetailDrawerActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_detail_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        if(savedInstanceState == null) {
            updateContentFromIdentifier(R.id.nav_dashboard, false);
        }

    }

    public void updateContentFromIdentifier(int identifier, boolean replace) {
        Fragment fragment = null;

        switch (identifier) {
            case R.id.nav_dashboard:
                fragment = BabyDashboardFragment.newInstance();
                break;

            default:
                Log.e(TAG, "Unknown id: " + identifier);
                break;
        }

        if(fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(replace) {
                transaction.replace(R.id.content_container, fragment, fragment.getTag()).commit();
            } else {
                transaction.add(R.id.content_container, fragment, fragment.getTag()).commit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Since we as of the writing moment only have one menu item the navigation is not completely implemented, we refer to the
        // parent app MainActivity, where the navigation implementation is using the same principles.
        int id = item.getItemId();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
