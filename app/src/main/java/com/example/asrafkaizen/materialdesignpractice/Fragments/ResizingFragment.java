package com.example.asrafkaizen.materialdesignpractice.Fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.asrafkaizen.materialdesignpractice.R;

/**
 * Created by asrafkaizen on 5/4/2017.
 */

public class ResizingFragment extends Fragment {

    private TextView label;
    private Button bInc, bDec;

    public ResizingFragment(){
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_resizing, container, false);
        label = (TextView) rootView.findViewById(R.id.label);
        bInc = (Button) rootView.findViewById(R.id.btn_biggertext);
        bDec = (Button) rootView.findViewById(R.id.btn_smallertext);

        bInc.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View arg0) {
                                        float sp = label.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
                                        label.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp + 5);
                                    }
                                });

        bDec.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                float sp = label.getTextSize() / getResources().getDisplayMetrics().scaledDensity;
                label.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp - 5);
            }
        });

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


    //manipulate the size of namelabel

//    float sp = namelabel.getTextSize() /
//                    if (sp <= 20)
//            namelabel.setTextSize(TypedValue.COMPLEX_UNIT_SP, sp + 4);
//                    Toast.makeText(getActivity(), "NameLabel text size : " + sp, Toast.LENGTH_SHORT).show();


}
