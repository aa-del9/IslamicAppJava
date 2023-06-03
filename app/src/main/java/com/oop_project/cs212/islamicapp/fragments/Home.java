package com.oop_project.cs212.islamicapp.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.oop_project.cs212.islamicapp.MVP.HomePresenter;
import com.oop_project.cs212.islamicapp.MVP.MVPPresenter;
import com.oop_project.cs212.islamicapp.MVP.MVPView;
import com.oop_project.cs212.islamicapp.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */


public class Home extends Fragment implements MVPView.HomeView{


    private View view;
    private TextView autoSizeTv;
    private TextView clockView;
    private String appName;
    private MVPPresenter.HomePresenter presenter;
    private TextView clockTextView;
    private Runnable runnable;
    private Handler CLockHandler;

    private ImageView clockFaceImageView;
    private ImageView hourHandImageView;
    private ImageView minuteHandImageView;
    private ImageView clockframe;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_home, container, false);
        initializeAll();
        clockTextView = view.findViewById(R.id.clockTextView);
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
        CLockHandler = new Handler();
        runnable = new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss aa", Locale.getDefault());
                String currentTime = sdf.format(new Date());
                clockTextView.setText(currentTime);
                updateClockHands();
                CLockHandler.postDelayed(this, 1000);
            }
        };
        CLockHandler.postDelayed(runnable, 0);
    }



    private void initializeAll() {
          clockView = view.findViewById(R.id.clockTextView);
        clockFaceImageView=view.findViewById(R.id.clockfaceview);
        clockFaceImageView.setImageResource(R.drawable.ic_analog_clock);


        appName = "IslamicApp";
        //imageDirectory = new File(Environment.getExternalStorageDirectory() + "/"+appName+"/");
//        imageDirectory = new File("C:/Users/This PC/Downloads/WhatsApp Image 2022-11-05 at 19.07.14");
//        imageName = "Athkar.jpg";

//        presenter = new HomePresenter(this);

        //shareIvBtn.setOnClickListener(this);
        //createNewImageBtn.setOnClickListener(this);
       // ring= MediaPlayer.create(getContext(),R.raw.shared_thank_you);
        //TextViewCompat.setAutoSizeTextTypeWithDefaults(autoSizeTv, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);

//        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(connectionStatusReceiver
//                ,new IntentFilter(Utils.BROADCAST_CONNECTION_STATUS));
//
//        LocalBroadcastManager.getInstance(getContext()).registerReceiver(messageReceiver
//                ,new IntentFilter(Utils.BROADCAST_ACTION));

    }

    /**
     * after view create initialize remainders and also show athkar/verse on home page
     * */
    //@Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        clockframe = view.findViewById(R.id.clockframe);
//        analogClockImageView = view.findViewById(R.id.clockfaceview);
        hourHandImageView = view.findViewById(R.id.hourhand);
        minuteHandImageView = view.findViewById(R.id.minutehand);

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


        // Add similar lines to set rotation for minute and second hands


    @Override
    public void onPause() {
        super.onPause();
        // Stop the clock when the activity is paused to avoid unnecessary updates
        CLockHandler.removeCallbacks(runnable);
    }


    /**
     * This method will be call from presenter class
     * */
//    @Override
//    public void updateRemainder(Context context,int hour, int mint, long interval){
//        NotificationScheduler.setReminder(context, AlarmReceiver.class, hour, mint,interval);
//    }



    /**
     * when user press the share button this method will show user the available social media
     * */
//    private void shareImageBtn(){
//        File filePath = new File(imageDirectory,"/"+imageName);
//
//        presenter.createIntentToShareImage(filePath);
//    }
//
//    //this is method will call from presenter it will take a intent then it will make share event
//    @Override
//    public void shareImage(Intent shareIntent){
//
//        Intent intent2 = Intent.createChooser(shareIntent, getString(R.string.share_via));
//        /** From version 24  we need to take file read permission */
//        if(Build.VERSION.SDK_INT>=24){
//            intent2.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        }
//
//        startActivityForResult(intent2, SHARE_IMAGE_REQUEST_CODE);
//    }
//
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == SHARE_IMAGE_REQUEST_CODE ){
//            if (resultCode == RESULT_OK){
//                playSound();
//            }
//        }
//    }
//
//
//    /**
//     * show athkar/ verse*/
//    @Override
//    public void setTodayImage(String  data){
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
//            autoSizeTv.setText(Html.fromHtml(data,Html.FROM_HTML_MODE_LEGACY));
//        } else {
//            autoSizeTv.setText(Html.fromHtml(data));
//        }
//    }
//
//    /*
//    * Store athkar image to user phone
//    * */
//    @Override
//    public void storeBitMapImage(Bitmap bitmap){
//
//        File folderDirectory = imageDirectory;
//        //create storage directories, if they don't exist
//        folderDirectory.mkdirs();
//        try {
//            String filePath = folderDirectory.toString() +"/"+imageName;
//            FileOutputStream fileOutputStream = new FileOutputStream(filePath);
//
//            BufferedOutputStream bos = new BufferedOutputStream(fileOutputStream);
//            //choose another format if PNG doesn't suit you
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
//
//            bos.flush();
//            bos.close();
//
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
////
////    BroadcastReceiver connectionStatusReceiver = new BroadcastReceiver() {
////        @Override
////        public void onReceive(Context context, Intent intent) {
////            Bundle bundle = intent.getExtras();
////            String message = bundle.getString(Utils.CONNECTION_STATUS);
////
//////            if (bundle.getInt(Utils.STATUS_CODE) == Utils.ALL_CONNECTED){
//////                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
//////
//////            }else if (bundle.getInt(Utils.STATUS_CODE) == Utils.NO_CONNECTION_CODE){
//////                Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
//////            }
////
////        }
////    };
//
//    public void playSound(){
//        ring= MediaPlayer.create(getContext(),R.raw.shared_thank_you);
//        ring.start();
//
//    }
//
//    @Override
//    public void onResume() {
//        ring= MediaPlayer.create(getContext(),R.raw.shared_thank_you);
//        super.onResume();
//    }
//
//    @Override
//    public void onPause() {
//        ring.stop();
//        super.onPause();
//    }
//
//    /*
//    * This method will be call after API call
//    * */
//    //broadcast receiver
//    BroadcastReceiver messageReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            boolean isUpdateData = intent.getBooleanExtra(Utils.EXTENDED_IS_UPDATE_DATA,false);
//            //new data update
//            if (isUpdateData){
//                presenter.prepareAtkhar();
//
//            }
//
//            Log.d("Test", "onReceive: "+isUpdateData);
//        }
//    };
}
