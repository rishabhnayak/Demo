package com.example.android.miwok;

/**
 * Created by sahu on 5/5/2017.
 */

 class stn_status_Items_Class {

    private String trainName;
    private String trainNo;
    private String trainSrc;
    private String trainDstn;
    private String divertedFrom;
    private String divertedTo;
    private String startDate;
    private String trainType;

    public stn_status_Items_Class(String trainNo, String trainName, String trainSrc, String trainDstn){
        this.trainName =trainName;
        this.trainNo=trainNo;
        this.trainSrc=trainSrc;
        this.trainDstn=trainDstn;
    }

    public String getTrainName() {
        return trainName;
    }

    public String getTrainNo() {
        return trainNo;
    }

    public String getTrainSrc() {
        return trainSrc;
    }
    public String getTrainDstn() {
        return trainDstn;
    }

    public String getDivertedFrom() {
        return divertedFrom;
    }

    public String getDivertedTo() {
        return divertedTo;
    }

    public String getTrainType() {
        return trainType;
    }

    public String getStartDate() {
        return startDate;
    }
}
