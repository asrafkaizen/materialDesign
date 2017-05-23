package com.example.asrafkaizen.materialdesignpractice.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.asrafkaizen.materialdesignpractice.R;

/**
 * Created by asrafkaizen on 5/4/2017.
 */

public class MessagesFragment extends Fragment {

    ArrayAdapter<CharSequence> mArrayAdapter;
    Spinner mSpn;
    TextView tv;

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

        tv = (TextView)rootView.findViewById(R.id.label);

        mSpn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // TODO Auto-generated method stub

            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    public void onItemSelected(){

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
