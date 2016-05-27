package com.mikmat.auha30parent;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

public class AgreementFragment extends BaseFragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String FREETEXT_FOOD = "food";
    private static final String FREETEXT_HYGIENE = "Hygiene";
    private static final String FREETEXT_NIDCAP = "NIDCAP";
    private static final String FREETEXT_OTHER = "Other";
    private boolean mParam1;
    private String mParam2;

    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private Spinner spinner4;
    private Spinner spinner5;

    private SharedPreferences sharedPreferences;
    private View view;
    private FragmentInteractionListener mListener;

    public AgreementFragment() {
        // Required empty public constructor
    }

    public static AgreementFragment newInstance(boolean param1, String param2) {
        AgreementFragment fragment = new AgreementFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getBoolean(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_agreement, container, false);
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE);

        if (mParam1 == true) {
            getSpinnersFromView();
            if (mParam2.equals("Food")) {
                initFood(view);
            } else if (mParam2.equals("Hygiene")) {
                initHygiene(view);
            }
        } else {
            LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.schemaLayout);
            linearLayout.setVisibility(View.GONE);
            if (mParam2.equals("NIDCAP")) {
                initNIDCAP(view);
            } else if (mParam2.equals("Other")) {
                initOther(view);
            }
        }

        Button submit = (Button) view.findViewById(R.id.submitAgreement);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                persistAgreement(mParam2);
            }
        });

        return view;
    }

    private void initFood(View view) {
        TextView headlineText = (TextView) view.findViewById(R.id.headline);
        headlineText.setText(R.string.newBornFood);
        TextView textView1 = (TextView) view.findViewById(R.id.choice1);
        textView1.setText(R.string.giveFood);
        spinner1.setSelection(sharedPreferences.getInt(getString(R.string.giveFood),0));
        TextView textView2 = (TextView) view.findViewById(R.id.choice2);
        textView2.setText(R.string.mixMilk);
        spinner2.setSelection(sharedPreferences.getInt(getString(R.string.mixMilk),0));
        TextView textView3 = (TextView) view.findViewById(R.id.choice3);
        textView3.setText(R.string.warmMilk);
        spinner3.setSelection(sharedPreferences.getInt(getString(R.string.warmMilk),0));
        TextView textView4 = (TextView) view.findViewById(R.id.choice4);
        textView4.setText(R.string.Vitamins);
        spinner4.setSelection(sharedPreferences.getInt(getString(R.string.Vitamins),0));
        TextView textView5 = (TextView) view.findViewById(R.id.choice5);
        textView5.setText(R.string.sonde);
        spinner5.setSelection(sharedPreferences.getInt(getString(R.string.sonde),0));

        EditText freetext = (EditText) view.findViewById(R.id.freeText);
        freetext.setText(sharedPreferences.getString(FREETEXT_FOOD,""));
    }

    private void initHygiene(View view) {
        TextView headlineText = (TextView) view.findViewById(R.id.headline);
        headlineText.setText(R.string.Hygiene);
        TextView textView1 = (TextView) view.findViewById(R.id.choice1);
        textView1.setText(R.string.changeDiaper);
        spinner1.setSelection(sharedPreferences.getInt(getString(R.string.changeDiaper),0));
        TextView textView2 = (TextView) view.findViewById(R.id.choice2);
        textView2.setText(R.string.washBaby);
        spinner2.setSelection(sharedPreferences.getInt(getString(R.string.washBaby),0));
        TextView textView3 = (TextView) view.findViewById(R.id.choice3);
        textView3.setText(R.string.bath);
        spinner3.setSelection(sharedPreferences.getInt(getString(R.string.bath),0));
        TextView textView4 = (TextView) view.findViewById(R.id.choice4);
        textView4.setText(R.string.cleaningAcc);
        spinner4.setSelection(sharedPreferences.getInt(getString(R.string.cleaningAcc),0));
        TextView textView5 = (TextView) view.findViewById(R.id.choice5);
        textView5.setText(R.string.cleanCribb);
        spinner5.setSelection(sharedPreferences.getInt(getString(R.string.cleanCribb),0));

        EditText freetext = (EditText) view.findViewById(R.id.freeText);
        freetext.setText(sharedPreferences.getString(FREETEXT_HYGIENE,""));
    }

    private void initNIDCAP(View view) {
        TextView headlineText = (TextView) view.findViewById(R.id.headline);
        headlineText.setText(R.string.NIDCAP);

        EditText freetext = (EditText) view.findViewById(R.id.freeText);
        freetext.setText(sharedPreferences.getString(FREETEXT_NIDCAP,""));
    }

    private void initOther(View view) {
        TextView headlineText = (TextView) view.findViewById(R.id.headline);
        headlineText.setText(R.string.other);

        EditText freetext = (EditText) view.findViewById(R.id.freeText);
        freetext.setText(sharedPreferences.getString(FREETEXT_OTHER,""));
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof FragmentInteractionListener) {
            mListener = (FragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void persistAgreement(String agreementType){
        sharedPreferences = getContext().getSharedPreferences(getString(R.string.sharedPreferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        TextView freeText = (TextView) getView().findViewById(R.id.freeText);
        switch (agreementType) {
            case "Food":
                getSpinnersFromView();
                editor.putInt(getString(R.string.giveFood), spinner1.getSelectedItemPosition());
                editor.putInt(getString(R.string.mixMilk), spinner2.getSelectedItemPosition());
                editor.putInt(getString(R.string.warmMilk), spinner3.getSelectedItemPosition());
                editor.putInt(getString(R.string.Vitamins), spinner4.getSelectedItemPosition());
                editor.putInt(getString(R.string.sonde), spinner5.getSelectedItemPosition());
                editor.putString(FREETEXT_FOOD, freeText.getText().toString());
                editor.commit();
                break;
            case "Hygiene":
                getSpinnersFromView();
                editor.putInt(getString(R.string.changeDiaper), spinner1.getSelectedItemPosition());
                editor.putInt(getString(R.string.washBaby), spinner2.getSelectedItemPosition());
                editor.putInt(getString(R.string.bath), spinner3.getSelectedItemPosition());
                editor.putInt(getString(R.string.cleaningAcc), spinner4.getSelectedItemPosition());
                editor.putInt(getString(R.string.cleanCribb), spinner5.getSelectedItemPosition());
                editor.putString(FREETEXT_HYGIENE, freeText.getText().toString());
                editor.commit();
                break;
            case "NIDCAP":
                editor.putString(FREETEXT_NIDCAP, freeText.getText().toString());
                editor.commit();
                break;
            case "Other":
                editor.putString(FREETEXT_OTHER, freeText.getText().toString());
                editor.commit();
                break;
            default:
                break;
        }
    }

    private void getSpinnersFromView(){
        spinner1 = (Spinner)view.findViewById(R.id.spinner1);
        spinner2 = (Spinner)view.findViewById(R.id.spinner2);
        spinner3 = (Spinner)view.findViewById(R.id.spinner3);
        spinner4 = (Spinner)view.findViewById(R.id.spinner4);
        spinner5 = (Spinner)view.findViewById(R.id.spinner5);
    }
}
