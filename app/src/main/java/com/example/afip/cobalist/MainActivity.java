package com.example.afip.cobalist;

import android.app.Fragment;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.afip.cobalist.model.User_Guide;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{


    ArrayList<Item> animalList=new ArrayList<>();
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

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);
//        animalList.add(new Item("Sungai Njagir",R.drawable.map,"14 Februari 2017","Tingkat Pencemaran Baik"));
//        animalList.add(new Item("Sungai Wonorejo",R.drawable.map,"17 Februari 2017","Tingkat Pencemaran Buruk"));
//        animalList.add(new Item("Sungai TMB",R.drawable.map,"19 Februari 2017","Tingkat Pencemaran Sangat Baik"));
//        animalList.add(new Item("Sungai Njagir",R.drawable.map,"15 Mei 2017","Tingkat Pencemaran Baik"));
//        animalList.add(new Item("Sungai Wonorejo",R.drawable.map,"10.23","Tingkat Pencemaran Buruk"));
//        animalList.add(new Item("Sungai xxxxx",R.drawable.map,"12.48","Tingkat Pencemaran Baik"));

//        MyAdapter myAdapter=new MyAdapter(this,R.layout.list_view_items,animalList);

                BottomNavigationView bottomNavigationView = (BottomNavigationView)
                        findViewById(R.id.navigation);

                bottomNavigationView.setOnNavigationItemSelectedListener
                        (new BottomNavigationView.OnNavigationItemSelectedListener() {
                            @Override
                            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                android.support.v4.app.Fragment selectedFragment = null;
                                switch (item.getItemId()) {
                                    case R.id.action_item1:
                                        selectedFragment = ItemOneFragment.newInstance();
                                        break;
                                    case R.id.action_item3:
                                        selectedFragment = ItemThreeFragment.newInstance();
                                        break;
                                }
                                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.frame_layout, selectedFragment);
                                transaction.commit();
                                return true;
                            }
                        });

                //Manually displaying the first fragment - one time only
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.frame_layout, ItemOneFragment.newInstance());
                 transaction.commit();

        //Used to select an item programmatically
        //bottomNavigationView.getMenu().getItem(2).setChecked(true);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();
        android.support.v4.app.Fragment fragment = null;

        if (id == R.id.nav_camera) {
            // Handle the camera action
            Log.d("TAG","enter nav camera");
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame_layout,new UserGuide2()).commit();
        } else if (id == R.id.nav_gallery) {
            Log.d("TAG","enter nav gallery");
//            Intent newAct = new Intent(MainActivity.this, aboutus.class);
            startActivity(new Intent(MainActivity.this, aboutus.class));

        } else if (id == R.id.nav_slideshow) {

        }
        else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        if(fragment != null){

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}