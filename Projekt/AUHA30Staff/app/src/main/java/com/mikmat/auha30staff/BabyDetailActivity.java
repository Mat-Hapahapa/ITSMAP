package com.mikmat.auha30staff;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.core.Context;
import com.mikmat.auha30staff.Models.Baby;

public class BabyDetailActivity extends AppCompatActivity {

    private Baby mBaby;

    private TextView mTextViewName;
    private TextView mTextViewBirthday;
    private TextView mTextviewGender;
    private FloatingActionButton buttonEdit;
    private Button buttonDischarge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_detail);

        mTextViewName = (TextView) findViewById(R.id.text_name);
        mTextViewBirthday = (TextView) findViewById(R.id.text_birthday);
        mTextviewGender = (TextView) findViewById(R.id.text_gender);
        buttonEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
        buttonDischarge = (Button) findViewById(R.id.button_terminate);

        Intent intent = getIntent();
        mBaby = (Baby) intent.getSerializableExtra("barn");

        mTextViewName.setText(mBaby.getName());
        mTextViewBirthday.setText(mBaby.getBirthday().toString());
        mTextviewGender.setText(mBaby.getGender());

        buttonDischarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonDischarge();
            }
        });

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonEdit();
            }
        });
    }

    public void onButtonEdit() {
        Toast.makeText(this, "You can't do this yet, but soon...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, AddBabyActivity.class);
        intent.putExtra(AppPrefs.BABYKEY, mBaby);

        startActivity(intent);
    }

    public void onButtonDischarge() {
        AlertDialog.Builder builder =  new AlertDialog.Builder(this);
        builder.setMessage(this.getResources().getString(R.string.alert_discharge_baby));
        builder.setNegativeButton(this.getResources().getString(R.string.no), null);

        builder.setPositiveButton( this.getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onAlertPositiveButton();
            }
        });
        builder.show();
    }

    public void onAlertPositiveButton() {
        Firebase firebase = new Firebase(mBaby.getFirebaseRef());
        firebase.removeValue();
        finish();

        Toast.makeText(this, this.getResources().getString(R.string.discharge_baby), Toast.LENGTH_SHORT).show();
    }
}
