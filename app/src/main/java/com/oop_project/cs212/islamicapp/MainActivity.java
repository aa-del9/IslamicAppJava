package com.oop_project.cs212.islamicapp;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;


import com.oop_project.cs212.islamicapp.database.MyDatabase;
import com.oop_project.cs212.islamicapp.fragments.Home;
import com.oop_project.cs212.islamicapp.fragments.PrayerTime;
import com.oop_project.cs212.islamicapp.fragments.Qibla;
import com.oop_project.cs212.islamicapp.fragments.Quran;
import com.oop_project.cs212.islamicapp.fragments.Settings;
import com.oop_project.cs212.islamicapp.interfaces.CallAttachBaseContext;

import com.oop_project.cs212.islamicapp.R;
public class MainActivity extends AppCompatActivity implements View.OnClickListener, CallAttachBaseContext {
    
    private RelativeLayout navHomeRl,navPrayerRl,navQiblaRl,navQuranRl,navSettingRl;

    private Fragment fragment;
    private FragmentTransaction transaction;
    private RelativeLayout selectedNav;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    // app opening sound, we will use media player
    private MediaPlayer ring;

    private Intent mServiceIntent;

    // save app data
    private SavedData savedData;

    private NetworkStateReceiver receiver = null;
    private Context mContext;

    private MyDatabase database;

    @Override
    public void attachBaseContext(Context newBase) {
        mContext = LocaleManager.setLocale(newBase);
        super.attachBaseContext(mContext);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initializeAll();
        //playSound();//this method will play opening sound
        this.startService(mServiceIntent);//start IntentService for fetch data from online server

        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);

        toggle.syncState();

        if (savedData.getIsAppFirstTimeOpen()){
            fragment = new Settings();
            changeSelectedNavBg(findViewById(R.id.navSettingRl));
        }
        transaction.add(R.id.containerMain,fragment);
        transaction.commit();

    }

    /*
     * initializing all variable object ect
     * */
    private void initializeAll() {
        database = new MyDatabase(this);//when first time app open this will create a initial database
        //ring= MediaPlayer.create(MainActivity.this,R.raw.open_app_salam);
        navHomeRl = findViewById(R.id.navHomeRl);
        navPrayerRl = findViewById(R.id.navPrayerRl);
        navQiblaRl =  findViewById(R.id.navQiblaRl);
        navQuranRl =  findViewById(R.id.navQuranRl);
        navSettingRl = findViewById(R.id.navSettingRl);
        //navUrlRl =  findViewById(R.id.navUrlRl);


        navHomeRl.setOnClickListener(this);
        navPrayerRl.setOnClickListener(this);
        navQiblaRl.setOnClickListener(this);
        navQuranRl.setOnClickListener(this);
        navSettingRl.setOnClickListener(this);
        //navUrlRl.setOnClickListener(this);

        fragment = new Home();
        transaction = getSupportFragmentManager().beginTransaction();

        /** Navigation default selected menu is home menu */
        selectedNav = findViewById(R.id.navHomeRl);

        /** This method will take location permission from user */
        mServiceIntent = new Intent(this, DownloadData.class);


        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction(LocationManager.PROVIDERS_CHANGED_ACTION);

        receiver = new NetworkStateReceiver();
        this.registerReceiver(receiver, filter);


        LocalBroadcastManager.getInstance(this).registerReceiver(connectionStatusReceiver
                ,new IntentFilter(Utils.BROADCAST_CONNECTION_STATUS));
        savedData = new SavedData(this);


    }


    /** Navigation menu onclick listener
     * After click on a navigation menu fragment will be change
     */

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.navHomeRl:
                fragment = new Home();
                changeSelectedNavBg(findViewById(R.id.navHomeRl));
                break;
            case R.id.navPrayerRl:
                fragment = new PrayerTime();
                changeSelectedNavBg(findViewById(R.id.navPrayerRl));
                break;
            case R.id.navQiblaRl:
                fragment = new Qibla();
                changeSelectedNavBg(findViewById(R.id.navQiblaRl));
                break;
            case R.id.navSettingRl:
                fragment = new Settings();
                changeSelectedNavBg(findViewById(R.id.navSettingRl));
                break;
            case R.id.navQuranRl:
                fragment = new Quran();
                changeSelectedNavBg(findViewById(R.id.navQuranRl));
                break;
        }
        transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.containerMain,fragment);
        transaction.commit();
        closeDrawer();
    }

    /**
     * This method will change selected button background color
     * And also it will change previous selected navigation menu background to white background
     */

    public void changeSelectedNavBg(View newSelectedNavId){
        selectedNav.setBackgroundResource(R.color.color_white);

        selectedNav = (RelativeLayout) newSelectedNavId;
        selectedNav.setBackgroundResource(R.color.cardview_shadow_start_color);
    }

    // Close navigation drawer
    public void closeDrawer(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {
                        //permission granted
                    }

                } else {
                    permissionDenied();
                }
                return;
            }

        }
    }

    private void permissionDenied() {
        Toast.makeText(this, R.string.permission_denied,Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onAttachBaseContext(Context context){

        finish();
        startActivity(getIntent());
    }




    private void unregisterReceiver() {
        this.unregisterReceiver(receiver);
    }

    BroadcastReceiver connectionStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();

            if (bundle.getBoolean(Utils.DATA_CONNECTION_ENABLE)){
                startServiceIntent();
            }
        }
    };

    public void startServiceIntent(){
        startService(mServiceIntent);
    }



}
