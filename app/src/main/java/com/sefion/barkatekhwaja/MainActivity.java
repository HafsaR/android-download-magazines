package com.sefion.barkatekhwaja;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.sefion.barkatekhwaja.About.About;
import com.sefion.barkatekhwaja.Donate.Donate;
import com.sefion.barkatekhwaja.Home.Home;
import com.sefion.barkatekhwaja.Magazines.Magazines;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        manager = getSupportFragmentManager();


        Home home = new Home();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, home).commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


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


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Home h = new Home();

        if (id == R.id.home) {

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, h, "home");
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_magazines) {

            Magazines magazines = new Magazines();

            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, magazines, "magazines");
            transaction.addToBackStack("magazines");
            transaction.commit();

        } else if (id == R.id.nav_books) {
            Tile tile = new Tile();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, tile, "tile");
            transaction.addToBackStack("tile");
            transaction.commit();

        } else if (id == R.id.donate) {
            Donate donate = new Donate();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, donate, "donate");
            transaction.addToBackStack("donate");
            transaction.commit();

        } else if (id == R.id.about) {
            About about = new About();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.content_main, about, "tile");
            transaction.addToBackStack(null);
            transaction.commit();


        } else if (id == R.id.share) {
            try {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_SUBJECT, "Barkat-e-Khwaja");
                String sAux = "\nBarkat-e-Khwaja App: Monthly Magazine and Islamic Books \n\nDownload: ";
                sAux = sAux + "https://play.google.com/store/apps/details?id=com.sefion.barkatekhwaja";
                i.putExtra(Intent.EXTRA_TEXT, sAux);
                startActivity(Intent.createChooser(i, "Choose "));
            } catch (Exception e) {
                //e.toString();
            }


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
