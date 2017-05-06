package com.example.android.miwok;

/**
 * Created by sahu on 5/3/2017.
 */

class CanceledTrainClass {

    private String trainName;
    private String trainNo;
    private String trainSrc;
    private String trainDstn;


    public CanceledTrainClass(String trainNo,String trainName,String trainSrc,String trainDstn){
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
}
