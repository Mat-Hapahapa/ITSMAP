package com.mikmat.auha30parent;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.client.realtime.util.StringListReader;
import com.mikmat.auha30parent.Helpers.FirebaseHelper;
import com.mikmat.auha30parent.Models.Baby;

/**
 * Created by matry on 13-05-2016.
 */
public class LoginActivity extends AppCompatActivity {

    private static final String REFBABY = "BabyToReturn";
    private Button loginBtn;
    private EditText keyValue;
    private ProgressBar spinner;

    private String tmpKey;
    private FirebaseHelper firebaseHelper;
    Baby foundBaby;
    private View LoginView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        spinner = (ProgressBar) findViewById(R.id.progressBar);
        keyValue = (EditText) findViewById(R.id.KeyInput);

        firebaseHelper = new FirebaseHelper(this);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tmpKey = keyValue.getText().toString();
                checkBaby(v, Integer.parseInt(tmpKey));
            }
        });
    }

    private void checkBaby(View view,final int ID) {
        LoginView = view;
        startLoading();

        Firebase.setAndroidContext(this);
        Firebase rootRef = new Firebase("https://auha30.firebaseio.com/web/data");
        rootRef.child("/Babies").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                foundBaby = null;
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Baby baby = postSnapshot.getValue(Baby.class);
                    if (baby.getID() == ID) {
                        foundBaby = baby;
                        logInSucces();
                        endLoading(true);
                        break;
                    }
                }
                if (foundBaby == null) {
                    endLoading(false);
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });

    }

    public void startLoading() {
        spinner.setVisibility(View.VISIBLE);
        loginBtn.setClickable(false);
        keyValue.setEnabled(false);
    }

    public void endLoading(boolean isBabyFound) {
        spinner.setVisibility(View.GONE);
        loginBtn.setClickable(true);
        keyValue.setEnabled(true);
        if (isBabyFound){
            finish();
        } else {
            Snackbar.make(LoginView, "invalid key", Snackbar.LENGTH_LONG).show();
        }
    }

    private void logInSucces() {
        SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.sharedPreferences), MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(getString(R.string.LOGGED_IN), true);
        editor.commit();

        Intent result = new Intent(this, LoginActivity.class);
        result.putExtra(REFBABY, foundBaby.getFirebaseRef());
        setResult(Activity.RESULT_OK, result);
    }

}
