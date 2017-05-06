package com.example.android.miwok;


class RescheduledTrainClass {

    private String trainName;
    private String trainNo;
    private String trainSrc;
    private String trainDstn;
    private String schDep;
    private String actDep;
    private String delayDep;
    private String startDate;


    public RescheduledTrainClass(String trainNo, String trainName, String trainSrc, String trainDstn){
        this.trainName =trainName;
        this.trainNo=trainNo;
        this.trainSrc=trainSrc;
        this.trainDstn=trainDstn;
//        this.schDep=schDep;
//        this.actDep=actDep;
//        this.delayDep=delayDep;
//        this.startDate=startDate;

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

    public String getSchDep() {
        return schDep;
    }

    public String getActDep() {
        return actDep;
    }

    public String getDelayDep() {
        return delayDep;
    }

    public String getStartDate() {
        return startDate;
    }
}
