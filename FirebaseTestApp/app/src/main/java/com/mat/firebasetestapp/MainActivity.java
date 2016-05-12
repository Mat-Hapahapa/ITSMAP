package com.mat.firebasetestapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mat.firebasetestapp.Adaptors.userListAdaptor;
import com.mat.firebasetestapp.models.User;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private userListAdaptor userAdaptor;
    private ListView userListView;
    private static Firebase ref;
    private static Firebase refUser;
    private Button submitBtn;
    private Button getDataBtn;
    private ArrayList<User> userList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userList = new ArrayList<User>();
        updateListView();

        Firebase.setAndroidContext(this);
        ref = new Firebase("https://shining-heat-2615.firebaseio.com/web/data");
        refUser = new Firebase("https://shining-heat-2615.firebaseio.com/web/data/users");

        // region submit data

        submitBtn = (Button) findViewById(R.id.submitBtn);
        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText nameEdit = (EditText) findViewById(R.id.nameEdit);
                EditText ageEdit = (EditText) findViewById(R.id.ageEdit);

                String nameToAdd = nameEdit.getText().toString();
                String tmpAge = ageEdit.getText().toString();
                int ageToAdd;
                if (nameToAdd == null || nameToAdd.equals("")) {
                    Snackbar.make(v, "invalid Name", Snackbar.LENGTH_LONG).show();
                } else {
                    try {
                        ageToAdd = Integer.parseInt(tmpAge);
                        User user = new User(nameToAdd, ageToAdd);
                        addOrOverwriteUser(user);
                    }catch (NumberFormatException e) {
                        Snackbar.make(v, "Invalid Age", Snackbar.LENGTH_LONG).show();
                        return;
                    }
                }
            }
        });
        // endregion

        // region retrieve data
        getDataBtn = (Button) findViewById(R.id.getUserBtn);
        getDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v, "This button has not been implemented yet", Snackbar.LENGTH_LONG).show();
            }
        });
        // endregion

        // region addValueEventListener
        refUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                System.out.println(snapshot.getValue()); //print whole snapshot

                userList.clear();
                for (DataSnapshot UserSnapshot: snapshot.getChildren()) {
                    User user = UserSnapshot.getValue(User.class);
                    userList.add(user);
                }
                updateListView();
            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

       // endregion

        // region scaffolded code
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Example: No internet connection", Snackbar.LENGTH_LONG)
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
        // endregion
    }

    public void addOrOverwriteUser(User user) {
        Firebase userRef = ref.child("users").child(user.getName());
        //Use userRef.updateChildren(user); if the current data shouldn't be overwritten
        userRef.setValue(user, new Firebase.CompletionListener() {
            @Override
            public void onComplete(FirebaseError firebaseError, Firebase firebase) {
                if (firebaseError != null) {
                    System.out.println("Data could not be saved. " + firebaseError.getMessage());
                } else {
                    System.out.println("Data saved successfully.");
                }
            }
        });

    }

    public void updateListView() {
        userAdaptor = new userListAdaptor(this, userList);
        userListView = (ListView) findViewById(R.id.UserListView);
        userListView.setAdapter(userAdaptor);
        userListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String userName = userList.get(position).getName();
                Firebase userRefToDelete = new Firebase("https://shining-heat-2615.firebaseio.com/web/data/users/" + userName);
                userRefToDelete.removeValue();
            }
        });
    }

    // region scaffolded code

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

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    // endregion

}
