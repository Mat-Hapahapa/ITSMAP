package com.mikmat.auha30staff;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.mikmat.auha30staff.Models.Baby;

public class BabyDetailActivity extends AppCompatActivity {

    private Baby mBaby;

    private TextView mTextViewName;
    private TextView mTextViewBirthday;
    private TextView mTextviewGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baby_detail);

        mTextViewName = (TextView) findViewById(R.id.text_name);
        mTextViewBirthday = (TextView) findViewById(R.id.text_birthday);
        mTextviewGender = (TextView) findViewById(R.id.text_gender);

        Intent intent = getIntent();
        mBaby = (Baby) intent.getSerializableExtra("barn");

        mTextViewName.setText(mBaby.getName());
        mTextViewBirthday.setText(mBaby.getBirthday().toString());
        mTextviewGender.setText(mBaby.getGender());
    }
}
