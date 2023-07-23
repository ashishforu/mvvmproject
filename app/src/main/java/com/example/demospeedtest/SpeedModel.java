package com.example.demospeedtest;

import androidx.annotation.Nullable;

public class SpeedModel {

//    public SpeedModel(@Nullable String currentSpeed, @Nullable String meanSpeed, @Nullable String maxSpeed, @Nullable String minSpeed) {
//        this.currentSpeed = currentSpeed;
//        this.meanSpeed = meanSpeed;
//        this.maxSpeed = maxSpeed;
//        this.minSpeed = minSpeed;
//    }

    public String getCurrentSpeed() {
        return currentSpeed;
    }

    public void setCurrentSpeed(@Nullable String currentSpeed) {
        this.currentSpeed = currentSpeed;
    }


    public String getMeanSpeed() {
        return meanSpeed;
    }

    public void setMeanSpeed(@Nullable String meanSpeed) {
        this.meanSpeed = meanSpeed;
    }


    public String getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(@Nullable String maxSpeed) {
        this.maxSpeed = maxSpeed;
    }


    public String getMinSpeed() {
        return minSpeed;
    }

    public void setMinSpeed(@Nullable String minSpeed) {
        this.minSpeed = minSpeed;
    }

    @Nullable
    private String currentSpeed,meanSpeed,maxSpeed,minSpeed;
}
