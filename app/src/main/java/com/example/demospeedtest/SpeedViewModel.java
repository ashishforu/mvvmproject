package com.example.demospeedtest;

import static com.example.demospeedtest.App.CHANNEL_1_ID;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.example.demospeedtest.database.AppDatabase;
import com.example.demospeedtest.database.Speed;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;

public class SpeedViewModel extends BaseObservable {
    private SpeedModel model;

    private AppDatabase database;
    double GB = 1000000000;
    double MB = 1000000;
    double KB = 1000;
    String downloadSpeedOutput = "";
    String units = "";
    String actualSpeed;
    Context context;
    private NotificationManagerCompat notificationManager;
    @Bindable
    public String getCurrentSpeed() {
        return model.getCurrentSpeed();
    }

    public void setCurrentSpeed(String currentspeed) {
        model.setCurrentSpeed(currentspeed);
        notifyPropertyChanged(BR.currentSpeed);
    }

    //----------------------------
    public void setMeanSpeed(String meanSpeed) {
        model.setMeanSpeed(meanSpeed);
        notifyPropertyChanged(BR.meanSpeed);
    }

    @Bindable
    public String getMeanSpeed() {
        return model.getMeanSpeed();
    }

    //---------------------------
    public void setMaxSpeed(String maxSpeed) {
        model.setMaxSpeed(maxSpeed);
        notifyPropertyChanged(BR.maxSpeed);

    }

    @Bindable
    public String getMaxSpeed() {
        return model.getMaxSpeed();
    }

    //----------------------
    public void setMinSpeed(String minSpeed) {
        model.setMinSpeed(minSpeed);
        notifyPropertyChanged(BR.minSpeed);
    }

    @Bindable
    public String getMinSpeed() {
        return model.getMinSpeed();
    }

    @Bindable
    // string variable for
    // toast message
    private String toastMessage = null;

    // getter and setter methods
    // for toast message
    public String getToastMessage() {
        return toastMessage;
    }

    private void setToastMessage(String toastMessage) {
        this.toastMessage = toastMessage;
        notifyPropertyChanged(BR.toastMessage);
    }

    //------------------------------------
    public SpeedViewModel(Application application,Context context1) {
        database = AppDatabase.getInstance(application);
        this.context=context1;
        model = new SpeedModel();
        notificationManager = NotificationManagerCompat.from(context1);
        Timer timer = new Timer();

        // Update the elapsed time every second.
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                dataSpeed();
            }
        }, 1000, 1000);
        sendOnChannel1();
    }

    public void dataSpeed() {
        double mBytesPrevious = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        double mBytesCurrent = TrafficStats.getTotalRxBytes() + TrafficStats.getTotalTxBytes();

        double mNetworkSpeed = mBytesCurrent - mBytesPrevious;


        Log.d("mynetspeed", "speed is :" + mNetworkSpeed);
        double mDownloadSpeedWithDecimals;
        if (mNetworkSpeed >= GB) {
            mDownloadSpeedWithDecimals = mNetworkSpeed / GB;
            units = " GB/s";
        } else if (mNetworkSpeed >= MB) {
            mDownloadSpeedWithDecimals = mNetworkSpeed / MB;
            units = " MB/s";

        } else {
            mDownloadSpeedWithDecimals = mNetworkSpeed / KB;
            units = " KB/s";
        }
        downloadSpeedOutput = String.valueOf(mDownloadSpeedWithDecimals);

        Log.d("realSpeed------------", "Speed :" + downloadSpeedOutput + units);
        actualSpeed = downloadSpeedOutput;
        //currentSpeed.setText(downloadSpeedOutput+units);
        model.setCurrentSpeed(downloadSpeedOutput + units);
        setCurrentSpeed(downloadSpeedOutput+units);

        loadMaxSpeed();
        loadMinSpeed();


        String sumData = database.speedDao().getSum();
        String allCount = database.speedDao().getALlcount();

        if (sumData == null) {
//

        } else {
            loadMeanSpeed();

        }
        saveNewUser(String.valueOf(actualSpeed));

    }

    private void loadMaxSpeed() {
        String maxValue = database.speedDao().getMax();
        // maxSpeed.setText(maxValue +"KB/s");
        model.setMaxSpeed(maxValue);
        setMaxSpeed(maxValue);
        Log.d("showmaxspeed", "max speed is " + maxValue);

    }

    private void loadMinSpeed() {
        String minValue = database.speedDao().getMin();
        //minSpeed1.setText(minValue+"KB/s");
        model.setMinSpeed(minValue);
        setMinSpeed(minValue);
        Log.d("showminspeed", "min speed is " + minValue);

    }


    private void loadMeanSpeed() {
        String sumData = database.speedDao().getSum();
        String allCount = database.speedDao().getALlcount();
        double mean = Double.parseDouble(sumData) / Double.parseDouble(allCount);
        //  meanSpeed.setText(mean+"KB/s");
        model.setMeanSpeed(String.valueOf(mean));
        setMeanSpeed(String.valueOf(mean));
        Log.d("showmeanspeed", "min speed is " + sumData + "-----," + allCount + ",------" + mean);
    }

    private void saveNewUser(String actualSpeed) {
        Speed user = new Speed();
        user.actualSpeed = actualSpeed;
        database.speedDao().insertSpeed(user);
    }
    public void sendOnChannel1() {

        Notification notification = new NotificationCompat.Builder(context, CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("teste")
                .setContentText("Textshjbncjdvgfegrth")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .build();

        notificationManager.notify(1, notification);
    }

}
