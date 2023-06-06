package com.oop_project.cs212.islamicapp.MVP;

import android.hardware.SensorManager;
import android.view.LayoutInflater;
import android.view.View;

import com.oop_project.cs212.islamicapp.model.Surah;

import java.io.File;
import java.util.ArrayList;

public interface MVPPresenter {
    interface HomePresenter{
        void startCalculationPrayerTime();
        ArrayList calculateTime();
    }

    interface PrayerTimePresenter{
        void startCalculationPrayerTime();
    }
    interface QiblaPresenter{
        void startCalculatingLocation();

        boolean checkSensorAvailability(SensorManager mSensorManager);

        void calculateQiblaInfo();

        void onResume();

        void onPause();
    }

    interface QuranPresenter{
        void prepareSearchAdapter(LayoutInflater inflater, ArrayList<Surah> surahs);
    }
    interface SettingsPresenter{

        void prepareAdapters();

        void saveAppLanguageId(int id);


        void saveCalculationMethodId(int id);

        void saveJuristicMethodId(int id);

    }
}
