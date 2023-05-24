package com.oop_project.cs212.islamicapp;

import android.app.AlarmManager;
import android.content.Context;
import android.content.SharedPreferences;

import com.oop_project.cs212.islamicapp.R;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class SavedData {
    private static final String APP_PREFS_NAME = "SavedData";

    private SharedPreferences appSharedPrefs;
    private SharedPreferences.Editor prefsEditor;

    private static final String APP_LANGUAGE_SELECTED_ID ="appLanguageSelectedId";
    private static final String FREQUENCY_SELECTED_ID = "frequencyId";
    private static final String CALCULATION_METHOD_ID = "calculationMethodId";
    private static final String JURISTIC_METHOD_ID = "juristicMethodId";
    private static final String HOUR = "hour";
    private static final String MIN = "min";

    private static final String OLD_INTERVAL = "oldInterval";
    private static final String NEW_INTERVAL = "newInterval";

    private static final String REMAINDER_LANGUAGES = "remainderLanguages";
    private static final String LAST_ATHKAR_ID = "lastAthkarId";
    private static final String LAST_UPDATE_CODE = "lastUpdateCode";
    private static final String ATHKAR_TABLE_NAME = "athkarTableName";
    private static final String FIRST_TIME_DATA_SYNCHRONIZED = "dataFirstTimeAlreadySynchronized";
    private static final String FIRST_TIME_APP_OPEN = "firstTimeAppOpen";
    private static final String FIRST_TIME_REMAINDER_LANGUAGE_SETUP = "firstTimeRemainderLanguageSetup";
    private static final String LATITUDE = "lat";
    private static final String LONGITUDE = "long";
    private static final String CITY = "city";
    private Context context;


    public SavedData(Context context)
    {
        this.appSharedPrefs = context.getSharedPreferences(APP_PREFS_NAME, Context.MODE_PRIVATE);
        this.prefsEditor = appSharedPrefs.edit();
        this.context = context;
    }

    public void setAppLanguageSelectedId(int id){
        prefsEditor.putInt(APP_LANGUAGE_SELECTED_ID, id);
        prefsEditor.commit();
    }
    public int getAppLanguageSelectedId() {
        if (getIsAppFirstTimeOpen()){
            int id = getUserLocalLanguageId();
            setAppLanguageSelectedId(id);
            return id;
        }

        return appSharedPrefs.getInt(APP_LANGUAGE_SELECTED_ID,0);
    }

    public void setFrequencySelectedId(int id){
        prefsEditor.putInt(FREQUENCY_SELECTED_ID, id);
        prefsEditor.commit();
    }
    public int getFrequencySelectedId() {
        return appSharedPrefs.getInt(FREQUENCY_SELECTED_ID,4);
    }
    public void setCalculationMethodId(int id){
        prefsEditor.putInt(CALCULATION_METHOD_ID, id);
        prefsEditor.commit();
    }

    //default value of calculation method id 5 [5 is the index number of egipt]
    public int getCalculationMethodId() {
        return appSharedPrefs.getInt(CALCULATION_METHOD_ID,2);
    }
    public void setJuristicMethodId(int id){
        prefsEditor.putInt(JURISTIC_METHOD_ID, id);
        prefsEditor.commit();
    }
    public int getJuristicMethodId() {
        return appSharedPrefs.getInt(JURISTIC_METHOD_ID,0);
    }

    //when user change remainder frequency that time will be starting time for remainder
    //default value is 9am 0min
    public int getAppStartHour(){

        Calendar calendar = Calendar.getInstance();
        return appSharedPrefs.getInt(HOUR,calendar.get(Calendar.HOUR_OF_DAY));
    }

    public void setAppStartHour(int hour){

        prefsEditor.putInt(HOUR, hour);
        prefsEditor.commit();
    }

    public int getAppStartMin(){
        Calendar calendar = Calendar.getInstance();
        return appSharedPrefs.getInt(MIN,calendar.get(Calendar.MINUTE));
    }

    public void setAppStartMin(int min){
        prefsEditor.putInt(MIN, min);
        prefsEditor.commit();
    }


    public long getNewRemainderInterval(){
        return appSharedPrefs.getLong(NEW_INTERVAL, AlarmManager.INTERVAL_DAY);// after every 24 hours
    }

    public void setNewRemainderInterval(long inteval){
        prefsEditor.putLong(NEW_INTERVAL, inteval);
        prefsEditor.commit();
    }


    public long getOldRemainderInterval(){
        return appSharedPrefs.getLong(OLD_INTERVAL,0);//o means didn't set yet
    }

    public void setOldRemainderInterval(long inteval){
        prefsEditor.putLong(OLD_INTERVAL, inteval);
        prefsEditor.commit();
    }


    public void storeRemainderLanguages(boolean[] array) {

        prefsEditor.putInt(REMAINDER_LANGUAGES, array.length);

        for(int i=0;i<array.length;i++)
            prefsEditor.putBoolean(REMAINDER_LANGUAGES + i, array[i]);

        prefsEditor.commit();
    }

    public boolean[] getRemainderLanguages(int defaultSize) {

        if (isRemainderLanguageAlreadySetup()){
            int id = getUserLocalLanguageId();
            setAppLanguageSelectedId(id);//this will save user app language ID

            int size = defaultSize;
            boolean array[] = new boolean[size];
            boolean allFalse = true;
            for(int i=0;i<size;i++) {

                array[i] = appSharedPrefs.getBoolean(REMAINDER_LANGUAGES + i, false);

                if (i == id) {
                    array[i] = appSharedPrefs.getBoolean(REMAINDER_LANGUAGES + i, true);
                    allFalse = false;
                }
            }

            if (allFalse){
                array[0] = true;
            }

            storeRemainderLanguages(array);//store our latest remainder language

            return array;
        }

        int size = appSharedPrefs.getInt(REMAINDER_LANGUAGES, defaultSize);
        boolean array[] = new boolean[size];
        boolean allFalse = true;
        for(int i=0;i<size;i++) {

            array[i] = appSharedPrefs.getBoolean(REMAINDER_LANGUAGES + i, false);

            if (array[i]) {
                allFalse = false;
            }
        }

        if (allFalse){
            array[0] = true;//if no language select then english index position[0] will be true
        }
        return array;
    }


    public int getLastAthkarId(){
        return appSharedPrefs.getInt(LAST_ATHKAR_ID,0);//o means didn't create yet
    }

    public void setLastAthkarId(int id){
        prefsEditor.putInt(LAST_ATHKAR_ID, id);
        prefsEditor.commit();
    }


    public void saveLastUpdateCode(int updateCode){
        prefsEditor.putInt(LAST_UPDATE_CODE,updateCode);
        prefsEditor.commit();
    }
    public int getLastUpdateCode(){
        return appSharedPrefs.getInt(LAST_UPDATE_CODE,0);
    }

    public void saveAthkarTableName(String tableName){
        prefsEditor.putString(ATHKAR_TABLE_NAME,tableName);
        prefsEditor.commit();
    }
    public String getAthkarTableName(){
        return appSharedPrefs.getString(ATHKAR_TABLE_NAME,null);
    }

    public void saveDataSynchronizedStatusTrue(){
        prefsEditor.putBoolean(FIRST_TIME_DATA_SYNCHRONIZED,true);
        prefsEditor.commit();
    }
    public boolean getIsDataFirstTimeSynchronized(){
        return appSharedPrefs.getBoolean(FIRST_TIME_DATA_SYNCHRONIZED,false);
    }

    public void saveAppAlreadyOpen(){
        prefsEditor.putBoolean(FIRST_TIME_APP_OPEN,false);
        prefsEditor.commit();
    }
    public boolean getIsAppFirstTimeOpen(){
        boolean isFirstTime = appSharedPrefs.getBoolean(FIRST_TIME_APP_OPEN,true);
        saveAppAlreadyOpen();
        return isFirstTime;
    }
    public void saveAppRemainderLanguageAlreadySet(){
        prefsEditor.putBoolean(FIRST_TIME_REMAINDER_LANGUAGE_SETUP,false);
        prefsEditor.commit();
    }
    public boolean isRemainderLanguageAlreadySetup(){
        boolean isFirstTime = appSharedPrefs.getBoolean(FIRST_TIME_REMAINDER_LANGUAGE_SETUP,true);
        saveAppRemainderLanguageAlreadySet();
        return isFirstTime;
    }


    public void saveLat(float lat){
        prefsEditor.putFloat(LATITUDE,lat);
        prefsEditor.commit();
    }
    public float getLat(){
        return appSharedPrefs.getFloat(LATITUDE,0);
    }

    public void saveLongitude(float longitute){
        prefsEditor.putFloat(LONGITUDE,longitute);
        prefsEditor.commit();
    }
    public float getLong(){
        return appSharedPrefs.getFloat(LONGITUDE,0);
    }

    public void saveUserCity(String city){
        prefsEditor.putString(CITY,city);
        prefsEditor.commit();
    }
    public String getUserCity(){
        return appSharedPrefs.getString(CITY," ");
    }


    public int getUserLocalLanguageId(){
        String userDefaultLanguageCode = Locale.getDefault().getLanguage();
        int id = -1;

        List<String> temp = Arrays.asList(context.getResources().getStringArray(R.array.app_languages_code));
        for (String languageCode:temp) {
            id++;
            if (languageCode.toLowerCase().equals(userDefaultLanguageCode.toLowerCase())){
                return id;
            }
        }

        return 0;

    }

}
