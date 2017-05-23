package com.example.asrafkaizen.materialdesignpractice.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asrafkaizen.materialdesignpractice.R;
import com.example.asrafkaizen.materialdesignpractice.activity.ReceiveIntentActivity;

/**
 * Created by asrafkaizen on 5/5/2017.
 */

public class IntentFragment extends Fragment {

    private EditText name,email, comments;
    private Spinner course;
    private RatingBar ratingBar;
    private TextView namelabel;
    private int counter = 0;
    private Toast mToast;

    //default constructor
    public IntentFragment(){
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_working, container, false);
        // Inflate the layout for this fragment from fragment_working.xml

        namelabel = (TextView) rootView.findViewById(R.id.namelabel);
        name = (EditText) rootView.findViewById(R.id.name);
        email = (EditText) rootView.findViewById(R.id.email);
        course = (Spinner) rootView.findViewById(R.id.coursespinner);
        comments = (EditText) rootView.findViewById(R.id.comments);
        ratingBar = (RatingBar) rootView.findViewById(R.id.ratingBar);
        Button btnNextScreen = (Button) rootView.findViewById(R.id.btn);

        //Listening to button event
        btnNextScreen.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (counter < 3) {
                    counter++;
                        if (mToast != null) {
                            mToast.cancel();
                        }
                    mToast = Toast.makeText(getActivity(), "Button Clicked " + counter + " times",
                            Toast.LENGTH_SHORT);
                    mToast.show();
                }
                else{
                    counter = 0;
                    float rating = ratingBar.getRating();
                    //Starting a new Intent
                    Intent intent = new Intent(getContext(), ReceiveIntentActivity.class);
                    //Sending data to another Activity
                    intent.putExtra("name", name.getText().toString());
                    intent.putExtra("email", email.getText().toString());
                    intent.putExtra("course", course.getSelectedItem().toString());
                    intent.putExtra("comments", comments.getText().toString());
                    intent.putExtra("rating", Float.toString(rating));
                    startActivity(intent);
                }
            }
        });
        return rootView;
/*
        //all below here is to hide the toolbar on scroll
        RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
            boolean hideToolBar = false;
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (hideToolBar) {
                    this.getSupportActionBar().hide();
                } else {
                    this.getSupportActionBar().show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 20) {
                    hideToolBar = true;

                } else if (dy < -5) {
                    hideToolBar = false;
                }
            }
        };
        */

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
