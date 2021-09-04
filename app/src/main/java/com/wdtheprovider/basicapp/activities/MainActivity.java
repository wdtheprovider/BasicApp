package com.wdtheprovider.basicapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.wdtheprovider.basicapp.R;
import com.wdtheprovider.basicapp.fragments.Dashboard;
import com.wdtheprovider.basicapp.fragments.Fragment_2;
import com.wdtheprovider.basicapp.fragments.Fragment_3;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawer;
    private TextView nav_title, nav_title_2;
    private ImageView nav_image;

    NavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getTitle());
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);


        navigationView = findViewById(R.id.nav_view);
        View viewHeader = navigationView.getHeaderView(0);
        nav_title = viewHeader.findViewById(R.id.nav_title);
        nav_title_2 = viewHeader.findViewById(R.id.nav_title_2);
        nav_image = viewHeader.findViewById(R.id.nav_image);

        nav_title.setText("WD The Provider");
        nav_title_2.setText("dingaan@wdtheprovider.co.za");


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.fragment_1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard()).commit();
                        break;
                    case R.id.fragment_2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_2()).commit();
                        break;
                    case R.id.fragment_3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Fragment_3()).commit();
                        break;
                }
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Dashboard()).commit();
            navigationView.setCheckedItem(R.id.fragment_1);
        }
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void OpenActivity(View view) {

        switch (view.getId()) {
            case R.id.btn1:
                startActivity(new Intent(this, Screen1Activity.class));
                break;
            case R.id.btn2:
                startActivity(new Intent(this, Screen2Activity.class));
                break;
            case R.id.btn3:
                startActivity(new Intent(this, Screen3Activity.class));
                break;
        }
    }

}