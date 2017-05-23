package com.example.asrafkaizen.materialdesignpractice.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asrafkaizen.materialdesignpractice.R;

public class ReceiveIntentActivity extends AppCompatActivity {
    /** Called when the activity is first created. */

    private Toolbar mToolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_screen);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mToolbar is declared from second_screen.xml, which includes toolbar.xml

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //mToolbar is included & enabled

        // set the toolbar title
        getSupportActionBar().setTitle("You Entered");

        TextView txtName = (TextView) findViewById(R.id.txtName);
        TextView txtEmail = (TextView) findViewById(R.id.txtEmail);
        TextView txtCourse = (TextView) findViewById(R.id.txtCourse);
        TextView txtRating = (TextView) findViewById(R.id.txtRating);
        Button btnClose = (Button) findViewById(R.id.btnClose);

        Intent i = getIntent();
        // Receiving the Data
        String name = i.getStringExtra("name");
        String email = i.getStringExtra("email");
        String course = i.getStringExtra("course");
        String rating = i.getStringExtra("rating");
        Log.e("Second Screen", name + "." + email);

        // Displaying Received data
        txtName.setText(name);
        txtEmail.setText(email);
        txtCourse.setText(course);
        txtRating.setText(rating);

        // Binding Click event to Button
        btnClose.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                //Closing SecondScreen Activity
                finish();
            }
        });
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items from menu_main.xml to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        selectingItems(item);
        return super.onOptionsItemSelected(item);
    }

    //all below here is to hide the toolbar on scroll
    public RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        boolean hideToolBar = false;
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (hideToolBar) {
                ReceiveIntentActivity.this.getSupportActionBar().hide();
            } else {
                ReceiveIntentActivity.this.getSupportActionBar().show();
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
    public void selectingItems(MenuItem item){
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            //Just inserting my custom toast here
            LayoutInflater li = getLayoutInflater();
            //Getting the View object as defined in the toast_nosettings.xml file
            View toast_view = li.inflate(R.layout.toast_nosettings,
                    (ViewGroup)findViewById(R.id.custom_toast_layout));
            final Toast toast = new Toast(getApplicationContext());
            toast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(0, 0, -200);
            toast.setView(toast_view);//setting the view of custom toast layout
            toast.show();
            return;
        }
        if (id == R.id.action_about){
            Intent intent = new Intent (getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_home){
            Intent intent = new Intent (getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
