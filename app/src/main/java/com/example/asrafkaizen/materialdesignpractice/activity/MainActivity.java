package com.example.asrafkaizen.materialdesignpractice.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.asrafkaizen.materialdesignpractice.Fragments.FragmentDrawer;
import com.example.asrafkaizen.materialdesignpractice.Fragments.HomeFragment;
import com.example.asrafkaizen.materialdesignpractice.Fragments.IntentFragment;
import com.example.asrafkaizen.materialdesignpractice.Fragments.MessagesFragment;
import com.example.asrafkaizen.materialdesignpractice.Fragments.ResizingFragment;
import com.example.asrafkaizen.materialdesignpractice.R;

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener{

    private static String TAG = MainActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //layout is set to activity_main.xml

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        //mToolbar is declared from activity_main.xml, which includes toolbar.xml

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //mToolbar is included & enabled

        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        //fragment_navigation_drawer.xml is included & enables
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        //drawer_layout is the id of navigation_drawer_row.xml
        drawerFragment.setDrawerListener(this);

        // display the first navigation drawer view on app launch
        displayView(0);
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

    //listen to when drawer item is selected, insert the position into int position
    @Override
    public void onDrawerItemSelected(View view, int position) {
        displayView(position);
    }

    //set what happens when each of the drawer item is selected
    private void displayView(int position) {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        switch (position) {
            case 0:
                fragment = new HomeFragment();
                title = getString(R.string.title_home);
                break;
            case 1:
                fragment = new IntentFragment();
                title = getString(R.string.nav_item_intent);
                break;
            case 2:
                title = getString(R.string.title_compass);
                Intent i = new Intent(MainActivity.this, CompassActivity.class);
                startActivity(i);
                break;
            case 3:
                fragment = new ResizingFragment();
                title = getString(R.string.title_resizing);
                break;
            case 4:
                title = getString(R.string.title_sql);
                Intent iSQL = new Intent(MainActivity.this, SQiteActivity.class);
                startActivity(iSQL);
                break;
            case 5:
                title = getString(R.string.title_camera);
                Intent iCamera = new Intent(MainActivity.this, CameraActivity.class);
                startActivity(iCamera);
                break;
            case 6:
                fragment = new MessagesFragment();
                title = getString(R.string.title_messages);
                break;
            default:
                break;
        }

        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.commit();

            // set the toolbar title
            getSupportActionBar().setTitle(title + " M.Des.Pr");
        }
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
            Intent intent = new Intent (MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        if (id == R.id.action_home){
            Intent intent = new Intent (getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}