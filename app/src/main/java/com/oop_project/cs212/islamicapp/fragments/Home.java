package com.oop_project.cs212.islamicapp.fragments;
import static com.oop_project.cs212.islamicapp.fragments.PrayerTime.MY_PERMISSIONS_REQUEST_LOCATION;
import static com.oop_project.cs212.islamicapp.fragments.PrayerTime.hasPermissions;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.icu.util.IslamicCalendar;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.oop_project.cs212.islamicapp.CheckInternetConnection;
import com.oop_project.cs212.islamicapp.MVP.HomePresenter;
import com.oop_project.cs212.islamicapp.MVP.MVPPresenter;
import com.oop_project.cs212.islamicapp.MVP.MVPView;
import com.oop_project.cs212.islamicapp.MVP.PrayerTimePresenter;
import com.oop_project.cs212.islamicapp.R;
import com.oop_project.cs212.islamicapp.Utils;
import com.oop_project.cs212.islamicapp.model.PrayerTimeModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */


public class Home extends Fragment implements MVPView.HomeView{

    private MVPPresenter.HomePresenter presenter;
    private View view;
    private TextView clockView;
    private ArrayList prayerTimes;
    private TextView clockTextView;
    private Runnable runnable;
    private CheckInternetConnection internetConnectionTest;
    private Handler CLockHandler;
    private ImageView clockFaceImageView;
    private ImageView hourHandImageView;
    private ImageView minuteHandImageView;
    private ImageView clockframe;
    private TextView gregDate;
    private TextView IslamicDate;
    private TextView Currentprayer;
    private TextView cityTv;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        initializeAll();

        return view;
    }

    private void initializeAll() {
        clockView = view.findViewById(R.id.clockTextView);
        clockFaceImageView=view.findViewById(R.id.clockfaceview);
        clockFaceImageView.setImageResource(R.drawable.ic_analog_clock);
        clockframe = view.findViewById(R.id.clockframe);
        clockTextView = view.findViewById(R.id.clockTextView);
        gregDate = view.findViewById(R.id.gregoriandate);
        IslamicDate = view.findViewById(R.id.islamicdate);
        presenter = new HomePresenter(this);
        cityTv = view.findViewById(R.id.CityTv);
        Currentprayer = view.findViewById(R.id.currentprayer);
        internetConnectionTest = new CheckInternetConnection();
        //LocalBroadcastManager.getInstance(getActivity()).registerReceiver(connectionStatusReceiver,new IntentFilter(Utils.BROADCAST_CONNECTION_STATUS));
    }
    @Override
    public void onResume() {
        super.onResume();
        CLockHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                updateDigiTime();
                updateIslamicDate();
                updateDate();
                updateClockHands();
                Currentprayer.setText(PrayerTimeChecker(prayerTimes));
                CLockHandler.postDelayed(this, 1000);
            }
        };
        CLockHandler.postDelayed(runnable, 0);
    }

    public void updateDigiTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        clockTextView.setText(currentTime);
    }
    public void updateIslamicDate(){
        IslamicCalendar islamicCalendar = new IslamicCalendar();
        String currentIslamicDate = getIslamicDate(islamicCalendar);
        IslamicDate.setText(currentIslamicDate);
    }
    public void updateDate(){
        Calendar calendar = Calendar.getInstance();
        String currentDate = getFormattedDate(calendar);
        gregDate.setText(currentDate);
    }



    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

//        analogClockImageView = view.findViewById(R.id.clockfaceview);
        hourHandImageView = view.findViewById(R.id.hourhand);
        minuteHandImageView = view.findViewById(R.id.minutehand);
        callPresenter();
    }
    private void updateClockHands() {
        Calendar calendar = Calendar.getInstance();
        int hours = calendar.get(Calendar.HOUR);
        int minutes = calendar.get(Calendar.MINUTE);


        float hourRotation = (hours * 30) + (minutes * 0.5f);
        float minuteRotation = minutes * 6;

        hourHandImageView.setRotation(hourRotation);
        minuteHandImageView.setRotation(minuteRotation);
    }
    @Override
    public void onPause() {
        super.onPause();
        // Stop the clock when the activity is paused to avoid unnecessary updates
        CLockHandler.removeCallbacks(runnable);
    }
    private String getFormattedDate(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
        return sdf.format(calendar.getTime());
    }
    private String getIslamicDate(IslamicCalendar islamicCalendar) {
        int day = islamicCalendar.get(IslamicCalendar.DAY_OF_MONTH);
        int month = islamicCalendar.get(IslamicCalendar.MONTH);
        int year = islamicCalendar.get(IslamicCalendar.YEAR);

        String monthName = getIslamicMonthName(month);

        return day + " " + monthName + " " + year;
    }

    private String getIslamicMonthName(int month) {
        String[] months = getResources().getStringArray(R.array.islamic_months);
        return months[month];
    }

    public void setCityName(String cityName) {
        cityTv.setText(cityName);
    }
    public boolean checkLocationPermission() {

        final String[] PERMISSIONS = {Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if(!hasPermissions(getContext(), PERMISSIONS)){

            ActivityCompat.requestPermissions(getActivity(),
                    PERMISSIONS,
                    MY_PERMISSIONS_REQUEST_LOCATION);
            return false;
        } else {
            return true;
            //permission already granted
            //like android version < 5(lollipop) don,t need runtime permission

        }
    }

    private void callPresenter() {
        Toast.makeText(getContext(), getResources().getString(R.string.gps_setting_message), Toast.LENGTH_SHORT).show();
        if (internetConnectionTest.netCheck(getContext())){

            if (checkLocationPermission()){
                presenter.startCalculationPrayerTime();
                prayerTimes =  presenter.calculateTime();

                //unVisibleErrorTv();
            }else {
                Toast.makeText(getContext(),getResources().getString(R.string.gps_setting_message), Toast.LENGTH_SHORT).show();
                //visibleErrorTv(getContext().getResources().getString(R.string.gps_setting_message));
            }

        }else {
            Toast.makeText(getContext(),getResources().getString(R.string.required_data_connection), Toast.LENGTH_SHORT).show();
            //errorNoInternetTv.setText(getContext().getResources().getString(R.string.required_data_connection));
            //errorNoInternetTv.setVisibility(View.VISIBLE);
        }
    }
    public String PrayerTimeChecker(ArrayList<PrayerTimeModel> prayerTimes) {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a", Locale.getDefault());
        SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = sdf.format(new Date());
        String prayerTime;
        //Toast.makeText(getContext(), currentTime, Toast.LENGTH_SHORT).show();
        for (int i = 0; i < prayerTimes.size(); i++) {
            PrayerTimeModel prayerTimeObj = prayerTimes.get(i);
            String timeString= prayerTimeObj.getPrayerTime();
            try{
                Date date = inputFormat.parse(timeString);
                prayerTime = outputFormat.format(date);
                //Toast.makeText(getContext(), prayerTime, Toast.LENGTH_SHORT).show();
            }
            catch (ParseException e) {
                e.printStackTrace();
                return null;
            }
            if (currentTime.compareTo(prayerTime) < 0) {
                // The current time is before the prayer time
                if (i > 0) {
                    switch (i-1){
                        case 1: {return "Ishraq";}
                        case 2:{return "Zuhr";}
                        case 3:{return "Asr";}
                        case 4:{return "Maghrib";}
                        case 5:{return "Isha";}
                    }
                    //return prayerTimes.get(i - 1);
                } else {
                    // If the current time is before the first prayer time, return the last prayer time
                    return "Isha";
                }
            }
        }
        return "Fajr";

    }
    BroadcastReceiver connectionStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Bundle bundle = intent.getExtras();
            String message = bundle.getString(Utils.CONNECTION_STATUS);

            if (bundle.getInt(Utils.STATUS_CODE) == Utils.ALL_CONNECTED){
                //unVisibleErrorTv();
                presenter.startCalculationPrayerTime();
            }else if (bundle.getInt(Utils.STATUS_CODE) == Utils.NO_CONNECTION_CODE){
                //visibleErrorTv(message);
            }

        }
    };}
