package com.mikmat.auha30staff;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.mikmat.auha30staff.Models.Baby;

import java.util.ArrayList;
import java.util.Calendar;

public class AddBabyActivity extends AppCompatActivity {

    private EditText mEditTextName;
    private DatePicker mDatePickerBirthday;
    private Button mButtonCreateBaby;
    private Spinner mCaretakerSpinner;
    private ArrayList<String> mCaretakerList = new ArrayList<>();

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
        Firebase fbCaretakerRef = new Firebase("https://auha30.firebaseio.com/web/data/caretakers");

        fbCaretakerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> newList = dataSnapshot.getValue(ArrayList.class);
                updateCaretaker(newList);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

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

        Firebase firebaseRef = new Firebase("https://auha30.firebaseio.com/web/data/Babies");
        Firebase newBabyRef = firebaseRef.push();
        baby.setFirebaseRef(newBabyRef.toString());
        newBabyRef.setValue(baby);
    }

    private void updateCaretaker(ArrayList<String> sortList) {

        mCaretakerList.clear();
        for(int i=0; i< sortList.size(); i++){
            if (sortList.get(i) != null){
                mCaretakerList.add(sortList.get(i));
            }
        }
        mCaretakerSpinner = (Spinner) findViewById(R.id.caretaker_spinner);
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, mCaretakerList);
        mCaretakerSpinner.setAdapter(stringArrayAdapter);
    }

}
