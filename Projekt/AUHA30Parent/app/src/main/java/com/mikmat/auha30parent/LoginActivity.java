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

/**
 * Created by matry on 13-05-2016.
 */
public class LoginActivity extends AppCompatActivity {

    private Button loginBtn;
    private EditText keyValue;
    private String tmpKey;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginBtn = (Button) findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (manageResult()) {
                    finish();
                } else {
                    Snackbar.make(v, "invalid key", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    public boolean manageResult() {
        keyValue = (EditText) findViewById(R.id.KeyInput);

        tmpKey = keyValue.getText().toString();
        if (tmpKey == "IsInDatabase") { //TODO: Check key in firebase database
            Intent result = new Intent(this, LoginActivity.class);

            result.putExtra(getString(R.string.KEY_EXTRA), tmpKey);
            setResult(Activity.RESULT_OK, result);
            return true;
        } else {
            return false;
        }


    }
}
