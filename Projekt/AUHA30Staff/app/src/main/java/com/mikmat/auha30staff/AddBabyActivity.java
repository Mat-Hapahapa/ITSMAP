package com.mikmat.auha30staff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
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
import java.util.Random;

public class AddBabyActivity extends AppCompatActivity {

    private static final char[] symbols;
    static {
        StringBuilder tmp = new StringBuilder();
        for (char ch = '0'; ch <= '9'; ++ch)
            tmp.append(ch);
        for (char ch = 'a'; ch <= 'z'; ++ch)
            tmp.append(ch);
        symbols = tmp.toString().toCharArray();
    }
    private static final int ID_LENGTH = 4;

    private EditText mEditTextName;
    private DatePicker mDatePickerBirthday;
    private Button mButtonCreateBaby;
    private Spinner mCaretakerSpinner;
    private Spinner mGenderSpinner;
    private EditText mEditTextParentName;
    private EditText mEditTextPhoneNr;
    private EditText mEditTextEmail;
    private ArrayList<String> mCaretakerList = new ArrayList<>();
    private Baby mBaby;
    private boolean mEditState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        mBaby = (Baby)intent.getSerializableExtra(AppPrefs.BABYKEY);
        mEditState = mBaby != null;
        if(mEditState) {
            mButtonCreateBaby.setText(R.string.Update);
        }
        setContentView(R.layout.activity_add_baby);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mEditTextName = (EditText) findViewById(R.id.edit_name);
        mDatePickerBirthday = (DatePicker) findViewById(R.id.date_birthday);
        mButtonCreateBaby = (Button) findViewById(R.id.button_create_baby);
        mEditTextParentName = (EditText) findViewById(R.id.parentName);
        mEditTextPhoneNr = (EditText) findViewById(R.id.phoneNr);
        mEditTextEmail = (EditText) findViewById(R.id.mail);
        mGenderSpinner = (Spinner) findViewById(R.id.gender_spinner);

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
                prepareEditState();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

    }
    private void prepareEditState() {
        if(mEditState) {
            mEditTextName.setText(mBaby.getName());
            mEditTextEmail.setText(mBaby.getEmail());
            mEditTextParentName.setText(mBaby.getParentName());
            mEditTextPhoneNr.setText(mBaby.getPhoneNr());
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mBaby.getBirthday());
            mDatePickerBirthday.updateDate(calendar.get(Calendar.YEAR),calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
            mCaretakerSpinner.setSelection(mCaretakerList.indexOf(mBaby.getCaretaker()));
            String[] genders = getResources().getStringArray(R.array.array_gender);
            int index = -1;
            for (int i = 0; i < genders.length; i++) {
                if(genders[i].equals(mBaby.getGender()))
                {
                    index = i;
                    break;
                }
            }
            mGenderSpinner.setSelection(index);
        }
    }

    public void onButtonCreateBabyPressed(){
        Baby baby = generateBaby();
        if(mEditState) {
            Firebase ref = new Firebase(mBaby.getFirebaseRef());
            baby.setFirebaseRef(mBaby.getFirebaseRef());
            baby.setID(mBaby.getID());
            ref.setValue(baby);
            finish();
            return;
        }

        if (baby == null) {
            return;
        }

        Firebase firebaseRef = new Firebase("https://auha30.firebaseio.com/web/data/Babies");
        Firebase newBabyRef = firebaseRef.push();
        baby.setFirebaseRef(newBabyRef.toString());
        newBabyRef.setValue(baby);

        sendText(baby);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("ID: " + baby.getID());
        builder.setNeutralButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                exit();
            }
        });
        builder.show();
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

    private  String generateId() {
        //This is pieced together from the various answers from this post http://stackoverflow.com/questions/41107/how-to-generate-a-random-alpha-numeric-string
        Random random = new Random();

        StringBuilder builder = new StringBuilder( ID_LENGTH );
        for (int i = 0; i < ID_LENGTH; i++) {
            builder.append(symbols[random.nextInt(symbols.length)]);
        }
        return builder.toString();
    }

    private Baby generateBaby() {
        Baby baby = new Baby();
        String name = mEditTextName.getText().toString();
        String parentName = mEditTextParentName.getText().toString();
        String phoneNr = mEditTextPhoneNr.getText().toString();
        String email = mEditTextEmail.getText().toString();

        if (name.equals("") || parentName.equals("") || phoneNr.equals("") || email.equals("")) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.alertMissingInfo);
            builder.setNeutralButton("OK", null);
            builder.show();
            baby = null;
        } else {
            int day = mDatePickerBirthday.getDayOfMonth();
            int month = mDatePickerBirthday.getMonth();
            int year = mDatePickerBirthday.getYear();

            String gender;
            switch (mGenderSpinner.getSelectedItemPosition()) {
                case 1:
                    gender = mGenderSpinner.getSelectedItem().toString();
                    break;
                case 2:
                    gender = mGenderSpinner.getSelectedItem().toString();
                    break;
                default:
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(R.string.alertGender);
                    builder.setNeutralButton("OK", null);
                    builder.show();
                    return baby = null;
            }
            Spinner caretakerSpinner = (Spinner) findViewById(R.id.caretaker_spinner);
            String caretaker = caretakerSpinner.getSelectedItem().toString();

            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            baby.setBirthday(calendar.getTime());
            baby.setName(name);
            baby.setGender(gender);
            baby.setCaretaker(caretaker);
            baby.setID(generateId());
            baby.setParentName(parentName);
            baby.setPhoneNr(phoneNr);
            baby.setEmail(email);

            return baby;
        }
        return baby;
    };

    private void sendText (Baby b) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(b.getPhoneNr(), null, R.string.smsMessage + b.getID(), null, null);
    }

    private void exit() {
        finish();
    }

}
