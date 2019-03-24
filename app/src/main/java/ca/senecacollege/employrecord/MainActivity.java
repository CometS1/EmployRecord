package ca.senecacollege.employrecord;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import ca.senecacollege.employrecord.DatabaseHelper.User;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "MainActivity";

    public static Intent newIntent(Context packageContext) {
        Intent intent = new Intent(packageContext, MainActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // Start the Job Board Fragment after logging in - it will be the main fragment
        Fragment mainActivityFragment = null;
        mainActivityFragment = new JobBoardFragment();
        if (mainActivityFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_screen_area, mainActivityFragment).commit();
            navigationView.setCheckedItem(R.id.nav_job_board);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager mFragmentManager = getSupportFragmentManager();
            if (mFragmentManager.getBackStackEntryCount() > 0) {
                mFragmentManager.popBackStack();
            } else {
                //super.onBackPressed();
                /* 1. Implementing super.onBackPressed() will exit & logout of the app when pressing the back button on the last stack entry
                 * --> this works because finish() is added to LoginActivity under startActivityForResult(intent, 0);
                 * --> If finish() is NOT added to LoginActivity, pressing the back button does NOT exit the app and user is stuck on the showProgress() screen
                 * 2. Not implementing super.onBackPressed() means pressing the back button will do nothing on the last stack entry (stays on the last screen)
                 */
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        Log.e(TAG, "--> Start logout");
        User.setUser(null);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Fragment fragment = null;

        /* Clicking an item from the navigation drawer inserts
         * a specific fragment into main activity, except for log out */
        if (id == R.id.nav_profile) {
            fragment = new ProfileFragment();
        } else if (id == R.id.nav_job_search) {
            fragment = new JobSearchFragment();
        } else if (id == R.id.nav_job_board) {
            fragment = new JobBoardFragment();
        } else if (id == R.id.nav_notifications) {
            fragment = new NotificationsFragment();
        } else if (id == R.id.nav_logout) {
            // Log out will log the user out and return the user to the Login screen
            logout();
        }

        // The specified fragment is placed into the main screen area (content_main.xml page)
        if (fragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.main_screen_area, fragment).addToBackStack(null).commit();
            //getSupportFragmentManager().beginTransaction().replace(R.id.main_screen_area, fragment).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
