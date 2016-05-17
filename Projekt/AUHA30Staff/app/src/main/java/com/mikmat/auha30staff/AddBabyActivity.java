package com.mikmat.auha30staff;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.mikmat.auha30staff.Models.Baby;

import java.util.Calendar;

public class AddBabyActivity extends AppCompatActivity {

    private EditText mEditTextName;
    private DatePicker mDatePickerBirthday;
    private Button mButtonCreateBaby;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_baby);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditTextName = (EditText) findViewById(R.id.edit_name);
        mDatePickerBirthday = (DatePicker) findViewById(R.id.date_birthday);
        mButtonCreateBaby = (Button) findViewById(R.id.button_create_baby);

        mButtonCreateBaby.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonCreateBabyPressed();
            }
        });

        Firebase.setAndroidContext(this);

    }

    public void onButtonCreateBabyPressed(){
        String name = mEditTextName.getText().toString();
        int day = mDatePickerBirthday.getDayOfMonth();
        int month = mDatePickerBirthday.getMonth();
        int year = mDatePickerBirthday.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        Baby baby = new Baby();
        baby.setBirthday(calendar.getTime());
        baby.setName(name);

        Firebase firebaseRef = new Firebase("https://blinding-inferno-2026.firebaseio.com/web/data");
        firebaseRef.child("children").setValue(baby);
    }

}
