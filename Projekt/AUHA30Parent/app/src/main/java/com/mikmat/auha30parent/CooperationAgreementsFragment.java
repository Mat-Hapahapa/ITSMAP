package com.mikmat.auha30parent;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class CooperationAgreementsFragment extends Fragment {

    private static String[] mAgreementList;
    private FragmentInteractionListener mListener;
    private ListView mListViewAgreements;

    public CooperationAgreementsFragment() {
        // Required empty public constructor
    }


    public static CooperationAgreementsFragment newInstance() {
        CooperationAgreementsFragment fragment = new CooperationAgreementsFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mAgreementList = getContext().getResources().getStringArray(R.array.array_agreements);
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
}


