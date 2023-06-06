package com.oop_project.cs212.islamicapp.MVP;

import android.app.AlarmManager;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;

import com.oop_project.cs212.islamicapp.adapters.SpinnerAdapter;
import com.oop_project.cs212.islamicapp.fragments.Home;
import com.oop_project.cs212.islamicapp.interfaces.CallAttachBaseContext;

import com.oop_project.cs212.islamicapp.R;
import com.oop_project.cs212.islamicapp.SavedData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class SettingsPresenter implements MVPPresenter.SettingsPresenter{

    private Fragment fragment;
    private List<String > juristicMethods;
    private List<String > prayerTimeCalculationMethods;
    private List<String > frequencies;
    private List<String> languages;

    private MVPView.SettingsView mvpView;
    private SavedData savedData;
    private CallAttachBaseContext callAttachBaseContext;
    private Home homeFragment;
    private ArrayAdapter<String> adapter;




    public SettingsPresenter(Fragment fragment) {
        this.fragment = fragment;
        mvpView = (MVPView.SettingsView) fragment;
        savedData = new SavedData(fragment.getContext());
        callAttachBaseContext = (CallAttachBaseContext) fragment.getContext();
        homeFragment = new Home();
    }

    @Override
    public void prepareAdapters(){
        languageAdapter();
        prayerTimeCalculationMethod();
        juristicMethod();

    }



    private void juristicMethod() {
        juristicMethods = null;
        juristicMethods = Arrays.asList(fragment.getContext().getResources().getStringArray(R.array.juristic_method));

        //get saved data from shared preferences
        int indexNo = savedData.getJuristicMethodId();
        String selectedName = juristicMethods.get(indexNo);
        SpinnerAdapter adapter = new SpinnerAdapter(fragment.getContext(), juristicMethods);
        mvpView.initializeJuristicSpinner(adapter,selectedName,indexNo);
    }

    private void prayerTimeCalculationMethod() {
        prayerTimeCalculationMethods = null;
        prayerTimeCalculationMethods = Arrays.asList(fragment.getContext().getResources().getStringArray(R.array.prayer_calculation_method));


        int indexNo = savedData.getCalculationMethodId();
        String selectedName = prayerTimeCalculationMethods.get(indexNo);


        SpinnerAdapter adapter = new SpinnerAdapter(fragment.getContext(), prayerTimeCalculationMethods);
        mvpView.initializePrayerTimeCalculationSpinner(adapter,selectedName, indexNo);
    }

    private void languageAdapter() {
        languages = null;
        languages = Arrays.asList(fragment.getContext().getResources().getStringArray(R.array.app_languages));

        int indexNo = savedData.getAppLanguageSelectedId();
        String selectedName = languages.get(indexNo);

        SpinnerAdapter adapter = new SpinnerAdapter(fragment.getContext(), languages);
        mvpView.initializeLanguageSpinner(adapter, selectedName, indexNo);

    }


    //save selected item id into shared preferences

    @Override
    public void saveAppLanguageId(int id){
        savedData.setAppLanguageSelectedId(id);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    callAttachBaseContext.onAttachBaseContext(fragment.getContext());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }



    @Override
    public void saveCalculationMethodId(int id){
        savedData.setCalculationMethodId(id);
    }
    @Override
    public void saveJuristicMethodId(int id){
        savedData.setJuristicMethodId(id);
    }

    public void generateNewAthkarTable(){
        List<String > tableLanguages = Arrays.asList(fragment.getContext().getResources().getStringArray(R.array.remainder_language_table_name));

        int size = tableLanguages.size();
        boolean[] remainderLanguages = savedData.getRemainderLanguages(size);

        ArrayList<Integer> indexNoOfSelectedLanguage = new ArrayList<>();

        for (int i = 0; i < size; i++){
            if (remainderLanguages[i]){
                indexNoOfSelectedLanguage.add(i);
            }
        }
        size = indexNoOfSelectedLanguage.size();
        Random random= new Random();
        int randomSelectedLanguageIndex = random.nextInt(size);

        String tableName = tableLanguages.get(indexNoOfSelectedLanguage.get(randomSelectedLanguageIndex));
        savedData.saveAthkarTableName(tableName);//new image generated table name
    }
}
