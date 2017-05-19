package pt.ulisboa.tecnico.cmov.locdev;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import pt.ulisboa.tecnico.cmov.locdev.Application.FragmentInterface;
import pt.ulisboa.tecnico.cmov.locdev.Application.LocdevApp;
import pt.ulisboa.tecnico.cmov.locdev.wifiP2p.WifiP2pActivity;


public class MainActivity extends WifiP2pActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    //Active fragment
    FragmentInterface fragment = null;

    public final int GPS_PERMISSION_GRANT = 1234;
    // Acquire a reference to the system Location Manager
    public LocationManager locationManager = null;

    // Define a listener that responds to location updates
    public final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            // Called when a new location is found by the network location provider.
            Toast.makeText(getApplicationContext(), "Got new location: Lat:" + location.getLatitude() + "| Lon:" + location.getLongitude(), Toast.LENGTH_LONG).show();
            NewLocation(location);
            fragment.refreshFragment();
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }

        public void onProviderEnabled(String provider) {
        }

        public void onProviderDisabled(String provider) {
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        locationManager.removeUpdates(locationListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        StartLocationServices();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        try {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            ProfileFragment fragment = ProfileFragment.class.newInstance();
            this.fragment=fragment;
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, fragment)
                    .commit();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        fragment.refreshFragment();
    }

    private void StartLocationServices() {
        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION},GPS_PERMISSION_GRANT);
            Toast.makeText(this,"No access to GPS",Toast.LENGTH_LONG).show();
        }else{
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }
    }

    private void NewLocation(Location location) {
        LocdevApp app = (LocdevApp) getApplicationContext();
        double lat = location.getLatitude();
        double lng = location.getLongitude();
        app.setCurrentLocation(new pt.ulisboa.tecnico.cmov.projcmu.Shared.Location(lat,lng,""));
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmpt" +
            "yBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        Class fragmentClass = null;

        try {
            if (id == R.id.nav_profile) {
    //            fragmentClass = ProfileFragment.class;
                ProfileFragment fragment1 = ProfileFragment.class.newInstance();
                this.fragment = fragment1;
                fragment=fragment1;
            } else if (id == R.id.nav_locations) {
                LocationsFragment fragment1 = LocationsFragment.class.newInstance();
                this.fragment = fragment1;
                fragment=fragment1;
//                fragmentClass = LocationsFragment.class;
            } else if (id == R.id.nav_messages) {
                MessagesFragment fragment1 = MessagesFragment.class.newInstance();
                this.fragment = fragment1;
                fragment=fragment1;
//                fragmentClass = MessagesFragment.class;
            }

//            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentManager.beginTransaction()
                .replace(R.id.content_frame, fragment)
                .commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GPS_PERMISSION_GRANT: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    StartLocationServices();
                } else {

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void finish() {
        super.finish();
        Log.d(this.getClass().getName(),"OnFinish");
    }

    @Override
    public void RefreshActivityFromWifiP2p(){
        Log.d(getClass().getName(),"RefreshActivityFromWifiP2p Main class");
        if(fragment!=null){
            fragment.refreshFragment();
        }
    }
}
