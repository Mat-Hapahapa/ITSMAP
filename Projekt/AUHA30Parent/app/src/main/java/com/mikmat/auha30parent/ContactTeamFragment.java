package com.mikmat.auha30parent;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class ContactTeamFragment extends Fragment {


    private FragmentInteractionListener mListener;
    private View view;

    public ContactTeamFragment() {
        // Required empty public constructor
    }

    public static ContactTeamFragment newInstance() {
        ContactTeamFragment fragment = new ContactTeamFragment();

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
        view = inflater.inflate(R.layout.fragment_contact_team, container, false);

        TextView mail = (TextView) view.findViewById(R.id.mail);
        mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_VIEW);

                emailIntent.setData(Uri.parse("mailto:boerneafdelinga@auh.rm.dk"));
                if (isIntentSafe(emailIntent))
                {
                    startActivity(emailIntent);
                }
            }
        });

        TextView phoneA = (TextView) view.findViewById(R.id.phoneAmbulatorim);
        phoneA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + "78451743"));
                if (isIntentSafe(phoneIntent))
                {
                    startActivity(phoneIntent);
                }
            }
        });

        TextView phoneB = (TextView) view.findViewById(R.id.phoneBed);
        phoneB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent phoneIntent = new Intent(Intent.ACTION_DIAL);
                phoneIntent.setData(Uri.parse("tel:" + "78451731"));
                if (isIntentSafe(phoneIntent))
                {
                    startActivity(phoneIntent);
                }
            }
        });

        return view;
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
    public Boolean isIntentSafe(Intent intent){
        final PackageManager packageManager = getActivity().getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return activities.size() > 0;
    }
}
