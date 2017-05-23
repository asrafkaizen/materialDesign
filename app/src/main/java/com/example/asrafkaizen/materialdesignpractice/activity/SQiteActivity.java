package com.example.asrafkaizen.materialdesignpractice.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.asrafkaizen.materialdesignpractice.R;
import com.example.asrafkaizen.materialdesignpractice.adapter.GuestListAdapter;
import com.example.asrafkaizen.materialdesignpractice.adapter.data.WaitlistContract;
import com.example.asrafkaizen.materialdesignpractice.adapter.data.WaitlistDbHelper;

/**
 * Created by asrafkaizen on 5/9/2017.
 */
    public class SQiteActivity extends AppCompatActivity {

        private GuestListAdapter mAdapter;
        private SQLiteDatabase mDb;
        private EditText mNewGuestNameEditText;
        private EditText mNewPartySizeEditText;
        private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_sqlite);

            RecyclerView waitlistRecyclerView;

            // Set local attributes to corresponding views
            waitlistRecyclerView = (RecyclerView) this.findViewById(R.id.all_guests_list_view);
            mNewGuestNameEditText = (EditText) this.findViewById(R.id.person_name_edit_text);
            mNewPartySizeEditText = (EditText) this.findViewById(R.id.party_count_edit_text);

            // Set layout for the RecyclerView, because it's a list we are using the linear layout
            waitlistRecyclerView.setLayoutManager(new LinearLayoutManager(this));


            // Create a DB helper (this will create the DB if run for the first time)
            WaitlistDbHelper dbHelper = new WaitlistDbHelper(this);

            // Keep a reference to the mDb until paused or killed. Get a writable database
            // because you will be adding restaurant customers
            mDb = dbHelper.getWritableDatabase();

            // Get all guest info from the database and save in a cursor
            Cursor cursor = getAllGuests();

            // Create an adapter for that cursor to display the data
            mAdapter = new GuestListAdapter(this, cursor);

            // Link the adapter to the RecyclerView
            waitlistRecyclerView.setAdapter(mAdapter);


            // COMPLETED (3) Create a new ItemTouchHelper with a SimpleCallback that handles both LEFT and RIGHT swipe directions
            // Create an item touch helper to handle swiping items off the list
            new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT ) {

                // COMPLETED (4) Override onMove and simply return false inside
                @Override
                public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                    //do nothing, we only care about swiping
                    return false;
                }

                // COMPLETED (5) Override onSwiped
                @Override
                public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
                    // COMPLETED (8) Inside, get the viewHolder's itemView's tag and store in a long variable id
                    //get the id of the item being swiped
                    long id = (long) viewHolder.itemView.getTag();
                    // COMPLETED (9) call removeGuest and pass through that id
                    //remove from DB
                    removeGuest(id);
                    // COMPLETED (10) call swapCursor on mAdapter passing in getAllGuests() as the argument
                    //update the list
                    mAdapter.swapCursor(getAllGuests());
                }

                //COMPLETED (11) attach the ItemTouchHelper to the waitlistRecyclerView
            }).attachToRecyclerView(waitlistRecyclerView);

            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            //mToolbar is declared from activity_main.xml, which includes toolbar.xml

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            //mToolbar is included & enabled
        }

        /**
         * This method is called when user clicks on the Add to waitlist button
         *
         * @param view The calling view (button)
         */
        public void addToWaitlist(View view) {
            if (mNewGuestNameEditText.getText().length() == 0) {
                return;
            }
            //default party size to 1
            int partySize = 0;
            try {
                //mNewPartyCountEditText inputType="number", so this should always work
                partySize = Integer.parseInt(mNewPartySizeEditText.getText().toString());
            } catch (NumberFormatException ex) {
                Log.e(LOG_TAG, "Failed to parse party size text to number: " + ex.getMessage());
            }

            if (partySize<1)
                partySize = 1;

            // Add guest info to mDb
            addNewGuest(mNewGuestNameEditText.getText().toString(), partySize);

            // Update the cursor in the adapter to trigger UI to display the new list
            mAdapter.swapCursor(getAllGuests());

            //clear UI text fields
            mNewPartySizeEditText.clearFocus();
            mNewGuestNameEditText.getText().clear();
            mNewPartySizeEditText.getText().clear();
        }



        /**
         * Query the mDb and get all guests from the waitlist table
         *
         * @return Cursor containing the list of guests
         */
        private Cursor getAllGuests() {
            return mDb.query(
                    WaitlistContract.WaitlistEntry.TABLE_NAME,
                    null,
                    null,
                    null,
                    null,
                    null,
                    WaitlistContract.WaitlistEntry.COLUMN_TIMESTAMP
            );
        }

        /**
         * Adds a new guest to the mDb including the party count and the current timestamp
         *
         * @param name  Guest's name
         * @param partySize Number in party
         * @return id of new record added
         */
        private long addNewGuest(String name, int partySize) {
            ContentValues cv = new ContentValues();
            cv.put(WaitlistContract.WaitlistEntry.COLUMN_GUEST_NAME, name);
            cv.put(WaitlistContract.WaitlistEntry.COLUMN_PARTY_SIZE, partySize);
            return mDb.insert(WaitlistContract.WaitlistEntry.TABLE_NAME, null, cv);
        }
    /**
         * Removes the record with the specified id
         *
         * @param id the DB id to be removed
         * @return True: if removed successfully, False: if failed
         */
        private boolean removeGuest(long id) {
            return mDb.delete(WaitlistContract.WaitlistEntry.TABLE_NAME, WaitlistContract.WaitlistEntry._ID + "=" + id, null) > 0;
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu; this adds items from menu_main.xml to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        selectingItems(item);
        return super.onOptionsItemSelected(item);
    }

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
