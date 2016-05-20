package com.mikmat.auha30staff;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;

public class CaretakerActivity extends AppCompatActivity {

    private Firebase fbRef;
    private Button createBtn;
    private ArrayList<String> caretakerList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caretaker);

        Firebase.setAndroidContext(this);
        fbRef = new Firebase("https://auha30.firebaseio.com/web/data/caretakers");

        fbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                caretakerList.clear();
                caretakerList = dataSnapshot.getValue(ArrayList.class);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        createBtn = (Button) findViewById(R.id.createCaretaker);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText caretakerName = (EditText) findViewById(R.id.caretaker_name);
                caretakerList.add(caretakerName.getText().toString());

                fbRef.setValue(caretakerList);
            }
        });

    }
}
