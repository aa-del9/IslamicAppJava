package com.oop_project.cs212.islamicapp.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.TextViewCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.oop_project.cs212.islamicapp.MVP.HomePresenter;
import com.oop_project.cs212.islamicapp.MVP.MVPPresenter;
import com.oop_project.cs212.islamicapp.MVP.MVPView;
import com.oop_project.cs212.islamicapp.R;
import com.oop_project.cs212.islamicapp.Remainder.AlarmReceiver;
import com.oop_project.cs212.islamicapp.Remainder.NotificationScheduler;
import com.oop_project.cs212.islamicapp.Utils;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */


public class Home extends Fragment implements View.OnClickListener, MVPView.HomeView{


    private View view;
    private TextView autoSizeTv;


    private RelativeLayout createImageRL;//relative layout that we will convert to an image bitmap
//    private ImageView shareIvBtn;
    private Button createNewImageBtn;
    private String appName;
    private File imageDirectory;
    private String imageName;
    private MVPPresenter.HomePresenter presenter;
    private MediaPlayer ring;
    public static final int SHARE_IMAGE_REQUEST_CODE=101;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        initializeAll();

        return view;
    }


    private void initializeAll() {
        autoSizeTv = view.findViewById(R.id.atkharTv);
        createImageRL = view.findViewById(R.id.createImageRL);
        //shareIvBtn = view.findViewById(R.id.shareIvBtn);
        createNewImageBtn = view.findViewById(R.id.createNewImageBtn);



        appName = "IslamicApp";
        //imageDirectory = new File(Environment.getExternalStorageDirectory() + "/"+appName+"/");
        imageDirectory = new File("C:/Users/This PC/Downloads/WhatsApp Image 2022-11-05 at 19.07.14");
        imageName = "Athkar.jpg";

        presenter = new HomePresenter(this);

        //shareIvBtn.setOnClickListener(this);
        createNewImageBtn.setOnClickListener(this);
        ring= MediaPlayer.create(getContext(),R.raw.shared_thank_you);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(autoSizeTv, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(connectionStatusReceiver
//                ,new IntentFilter(Utils.BROADCAST_CONNECTION_STATUS));
//
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(messageReceiver
                ,new IntentFilter(Utils.BROADCAST_ACTION));

    }

    /**
     * after view create initialize remainders and also show athkar/verse on home page
     * */
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //initialize remainder
        presenter.initializeRemainder();
        presenter.prepareAtkhar();
    }


    /**
     * This method will be call from presenter class
     * */
    @Override
    public void updateRemainder(Context context,int hour, int mint, long interval){
        NotificationScheduler.setReminder(context, AlarmReceiver.class, hour, mint,interval);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.shareIvBtn:
//                presenter.createBitMap(createImageRL);//this will create new image
//                shareImageBtn();
//                break;
            case R.id.createNewImageBtn:
                presenter.prepareAtkharBtnPress();
                break;
        }
    }

    /**
     * when user press the share button this method will show user the available social media
     * */
    private void shareImageBtn(){
        File filePath = new File(imageDirectory,"/"+imageName);

        presenter.createIntentToShareImage(filePath);
    }

    //this is method will call from presenter it will take a intent then it will make share event
    @Override
    public void shareImage(Intent shareIntent){

        Intent intent2 = Intent.createChooser(shareIntent, getString(R.string.share_via));
        /** From version 24  we need to take file read permission */
        if(Build.VERSION.SDK_INT>=24){
            intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        startActivityForResult(intent2, SHARE_IMAGE_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == SHARE_IMAGE_REQUEST_CODE ){
            if (resultCode == RESULT_OK){
                playSound();
            }
        }
    }


    /**
     * show athkar/ verse*/
    @Override
    public void setTodayImage(String  data){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            autoSizeTv.setText(Html.fromHtml(data,Html.FROM_HTML_MODE_LEGACY));
        } else {
            autoSizeTv.setText(Html.fromHtml(data));
        }
    }

    /*
    * Store athkar image to user phone
    * */
    @Override
    public void storeBitMapImage(Bitmap bitmap){

        File folderDirectory = imageDirectory;
        //create storage directories, if they don't exist
        folderDirectory.mkdirs();
        try {
            String filePath = folderDirectory.toString() +"/"+imageName;
            FileOutputStream fileOutputStream = new FileOutputStream(filePath);

            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
            //choose another format if PNG doesn't suit you
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);

            bos.flush();
            bos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
//
//    BroadcastReceiver connectionStatusReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            Bundle bundle = intent.getExtras();
//            String message = bundle.getString(Utils.CONNECTION_STATUS);
//
////            if (bundle.getInt(Utils.STATUS_CODE) == Utils.ALL_CONNECTED){
////                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
////
////            }else if (bundle.getInt(Utils.STATUS_CODE) == Utils.NO_CONNECTION_CODE){
////                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
////            }
//
//        }
//    };

    public void playSound(){
        ring= MediaPlayer.create(getContext(),R.raw.shared_thank_you);
        ring.start();

    }

    @Override
    public void onResume() {
        ring= MediaPlayer.create(getContext(),R.raw.shared_thank_you);
        super.onResume();
    }

    @Override
    public void onPause() {
        ring.stop();
        super.onPause();
    }

    /*
    * This method will be call after API call
    * */
    //broadcast receiver
    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            boolean isUpdateData = intent.getBooleanExtra(Utils.EXTENDED_IS_UPDATE_DATA,false);
            //new data update
            if (isUpdateData){
                presenter.prepareAtkhar();

            }

            Log.d("Test", "onReceive: "+isUpdateData);
        }
    };
}
