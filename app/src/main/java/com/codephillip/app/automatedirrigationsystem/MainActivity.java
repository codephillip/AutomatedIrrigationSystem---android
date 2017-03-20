package com.codephillip.app.automatedirrigationsystem;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.codephillip.app.automatedirrigationsystem.jsonmodels.metrics.Metric;
import com.codephillip.app.automatedirrigationsystem.jsonmodels.metrics.Metrics;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiClient;
import com.codephillip.app.automatedirrigationsystem.retrofit.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static ApiInterface apiInterface;


    String[] screenNames = {
            "System status", "Configuration", "Feedback", "About"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Fragment fragment = new SystemStatusFragment();
        getSupportActionBar().setTitle(screenNames[0]);
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

        //TODO remove. testing retrofit
        apiInterface = ApiClient.getClient(ApiClient.BASE_URL).create(ApiInterface.class);
        loadMetrics();
    }

    //TODO remove. testing retrofit
    private void loadMetrics() {
        Call<Metrics> call = apiInterface.allMetrics();
        call.enqueue(new Callback<Metrics>() {
            @Override
            public void onResponse(Call<Metrics> call, retrofit2.Response<Metrics> response) {
                Log.d("RETROFIT#", "onResponse: " + response.headers());
                Metrics metrics = response.body();
                for (Metric metric : metrics.getMetrics()) {
                    Log.d("RETROFIT#", "water# " + metric.getWaterVolume());
                }
            }

            @Override
            public void onFailure(Call<Metrics> call, Throwable t) {
                Log.d("RETROFIT#", "onFailure: " + t.toString());
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Log.d("Navigation bar", "onNavigationItemSelected: " + id);
        Fragment fragment = null;

        if (id == R.id.nav_status) {
            fragment = new SystemStatusFragment();
            getSupportActionBar().setTitle(screenNames[0]);
        } else if (id == R.id.nav_configuration) {
            fragment = new ConfigurationFragment();
            getSupportActionBar().setTitle(screenNames[1]);
        } else if (id == R.id.nav_feedback) {
            fragment = new FeedbackFragment();
            getSupportActionBar().setTitle(screenNames[2]);
        } else if (id == R.id.nav_about) {
            fragment = new AboutFragment();
            getSupportActionBar().setTitle(screenNames[3]);
        } else {
            return true;
        }

        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame,fragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
