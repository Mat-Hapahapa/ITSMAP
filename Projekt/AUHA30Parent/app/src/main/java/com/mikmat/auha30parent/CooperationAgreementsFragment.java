package com.mikmat.auha30parent;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;


public class CooperationAgreementsFragment extends Fragment implements AdapterView.OnItemClickListener {

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
        View rootView = inflater.inflate(R.layout.fragment_cooperation_agreements, container, false);
        mAgreementList = getContext().getResources().getStringArray(R.array.array_agreements);

        mListViewAgreements = (ListView) rootView.findViewById(R.id.list_agreements);
        mListViewAgreements.setOnItemClickListener(this);

        AgreementAdapter adapter = new AgreementAdapter(inflater);
        mListViewAgreements.setAdapter(adapter);
        return rootView;
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private class AgreementAdapter extends BaseAdapter {
        LayoutInflater inflater;

        public AgreementAdapter(LayoutInflater inflater) {
            this.inflater = inflater;
        }

        @Override
        public int getCount() {
            return mAgreementList.length;
        }

        @Override
        public Object getItem(int position) {
            return mAgreementList[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if(convertView == null) {
                convertView = inflater.inflate(android.R.layout.simple_list_item_1, null);
                TextView textView = (TextView) convertView.findViewById(android.R.id.text1);
                textView.setText((String)getItem(position));
            }
            return convertView;
        }
    }
}
