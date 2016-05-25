package com.mikmat.auha30parent;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.TextViewCompat;
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
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mikmat.auha30parent.Helpers.FirebaseHelper;
import com.mikmat.auha30parent.Models.Baby;
import com.mikmat.auha30parent.Services.BabyService;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String REFBABY = "BabyToReturn";
    static final int LOGIN_RESULT = 100;
    private FirebaseHelper fbtmp;
    private Baby thisBaby;
    private ViewGroup mContentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fbtmp = new FirebaseHelper(this);

        mContentContainer = (ViewGroup) findViewById(R.id.content_container);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                  Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                         .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateContentFromIdentifier(R.id.nav_dashboard, false);
    }

    public void updateContentFromIdentifier(int identifier, boolean replace) {
        Fragment fragment = null;

        switch (identifier) {
            case R.id.nav_dashboard:
                fragment = BabyDashboardFragment.newInstance();
                break;
            case R.id.nav_cooperation_agreements:
                fragment = CooperationAgreementsFragment.newInstance();
                break;
            case R.id.nav_my_childs_examinations:
                fragment = ChildExaminationsFragment.newInstance();
                break;
            case R.id.nav_contact_team:
                fragment = ContactTeamFragment.newInstance();
                break;
            default:
                Log.e(TAG, "Unknown id: " + identifier);
                break;
        }

        if(fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(replace) {
                transaction.replace(R.id.content_container, fragment, fragment.getTag()).addToBackStack(fragment.getTag()).commit();
            } else {
                transaction.add(R.id.content_container, fragment, fragment.getTag()).commit();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        isLoggedIn();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        updateContentFromIdentifier(id, true);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case LOGIN_RESULT:
                if (resultCode == RESULT_OK) {
                    Bundle extra = data.getExtras();

                    SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferences), MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(getString(R.string.FbBabyRef), extra.getString(REFBABY));
                    editor.commit();

                    new Firebase(extra.getString(REFBABY)).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot snapshot) {
                            thisBaby = snapshot.getValue(Baby.class);
                        }

                        @Override
                        public void onCancelled(FirebaseError firebaseError) {
                            System.out.println("The read failed: " + firebaseError.getMessage());
                        }
                    });
                }
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }


    }

    private void isLoggedIn() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferences), MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(getString(R.string.LOGGED_IN), false)) {
            Intent loginIntent = new Intent(this, LoginActivity.class);
            startActivityForResult(loginIntent, LOGIN_RESULT);
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    protected void onStop() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferences), MODE_PRIVATE);
        if (sharedPreferences.getBoolean(getString(R.string.LOGGED_IN), false)) {
            startService(new Intent(this, BabyService.class));
        }
        super.onStop();
    }
}
