package com.mikmat.auha30staff;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.mikmat.auha30staff.Models.Baby;

import java.util.List;

public class BabyDashboardFragment extends Fragment {

    private static final String TAG = BabyDashboardFragment.class.getSimpleName();

    private Baby mBaby;
    private FloatingActionButton mButtonEdit;
    private Button mButtonDischarge;
    private TextView mTextViewName;
    private TextView mTextViewBirthday;
    private TextView mTextViewGender;
    private TextView mTextViewParentName;
    private TextView mTextViewPhone;
    private TextView mTextViewMail;

    public BabyDashboardFragment() {
        // Required empty public constructor
    }

    public static BabyDashboardFragment newInstance() {
        BabyDashboardFragment fragment = new BabyDashboardFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Intent intent = getActivity().getIntent();
        mBaby = (Baby) intent.getSerializableExtra(AppPrefs.BABYKEY);

        View rootView = inflater.inflate(R.layout.fragment_baby_dashboard, container, false);
        mTextViewName = (TextView)rootView.findViewById(R.id.text_name);
        mTextViewBirthday = (TextView)rootView.findViewById(R.id.text_birthday);
        mTextViewGender = (TextView)rootView.findViewById(R.id.text_gender);
        mTextViewParentName = (TextView)rootView.findViewById(R.id.text_parent_name);
        mTextViewPhone = (TextView)rootView.findViewById(R.id.text_parent_phone);
        mTextViewMail = (TextView)rootView.findViewById(R.id.text_parent_mail);
        mButtonEdit = (FloatingActionButton) rootView.findViewById(R.id.fab_edit);
        mButtonDischarge = (Button) rootView.findViewById(R.id.button_terminate);

        mTextViewName.setText(mBaby.getName());
        mTextViewBirthday.setText(mBaby.getBirthday().toString());
        mTextViewGender.setText(mBaby.getGender());
        mTextViewParentName.setText(mBaby.getParentName());
        mTextViewPhone.setText(mBaby.getPhoneNr());
        mTextViewMail.setText(mBaby.getEmail());

        mTextViewMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleContact(R.id.text_parent_mail);
            }
        });
        mTextViewPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleContact(R.id.text_parent_phone);
            }
        });

        mButtonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonEdit();
            }
        });
        mButtonDischarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onButtonDischarge();
            }
        });

        return rootView;
    }

    public void onButtonEdit() {
        Intent intent = new Intent(getActivity(), AddBabyActivity.class);
        intent.putExtra(AppPrefs.BABYKEY, mBaby);

        startActivity(intent);
    }

    public void onButtonDischarge() {
        AlertDialog.Builder builder =  new AlertDialog.Builder(getActivity());
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
        getActivity().finish();

        Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.discharge_baby), Toast.LENGTH_SHORT).show();
    }

    public void handleContact(int id) {
        Intent intent = null;
        switch (id) {
            case R.id.text_parent_mail:
                intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("mailto:" + mBaby.getEmail()));
                break;
            case R.id.text_parent_phone:
                intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mBaby.getPhoneNr()));
                break;
            default:
                Log.d(TAG, "Uknown id: " + id);
        }


        if(intent != null && isIntentSafe(intent)) {
            startActivity(intent);
        } else {
            Toast.makeText(getActivity(), R.string.toast_contact_warning, Toast.LENGTH_LONG).show();
        }
    }

    public boolean isIntentSafe(Intent intent) {
        final PackageManager packageManager = getActivity().getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }
}
