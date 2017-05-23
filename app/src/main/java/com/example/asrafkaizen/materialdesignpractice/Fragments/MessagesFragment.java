package com.example.asrafkaizen.materialdesignpractice.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.asrafkaizen.materialdesignpractice.R;

/**
 * Created by asrafkaizen on 5/4/2017.
 */

public class MessagesFragment extends Fragment {

    ArrayAdapter<CharSequence> mArrayAdapter;
    Spinner mSpn;

    public MessagesFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_messages, container, false);

        mArrayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.utp_courses, R.layout.textview_item);
        mArrayAdapter.setDropDownViewResource(R.layout.textview_item);
        mSpn = (Spinner)rootView.findViewById(R.id.spn1);
        mSpn.setAdapter(mArrayAdapter);

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
