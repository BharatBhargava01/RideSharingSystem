package com.rideshare.model;

public class RideHistory {

    private int historyId;
    private String rideDate;

    public RideHistory(int historyId,
            String rideDate) {

        this.historyId = historyId;
        this.rideDate = rideDate;
    }

    public int getHistoryId() {
        return historyId;
    }

    public String getrideDate(){
        return rideDate;
    }


}
