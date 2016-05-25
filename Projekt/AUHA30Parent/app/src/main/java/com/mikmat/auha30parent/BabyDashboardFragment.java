package com.mikmat.auha30parent;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.zip.Inflater;


public class BabyDashboardFragment extends Fragment {

    private FragmentInteractionListener mListener;
    private View view;

    public BabyDashboardFragment() {
        // Required empty public constructor
    }

    public static BabyDashboardFragment newInstance() {
        BabyDashboardFragment fragment = new BabyDashboardFragment();

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
        view = inflater.inflate(R.layout.fragment_baby_dashboard, container, false);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(getString(R.string.sharedPreferences), getActivity().MODE_PRIVATE);

        Firebase firebase = new Firebase(sharedPreferences.getString(getString(R.string.FbBabyRef),""));
        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                TextView name = (TextView) view.findViewById(R.id.caretaker);
                name.setText(dataSnapshot.child("/caretaker").toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
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
                    + " must implement FragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
