package com.gov.Advertisments.Model.Response;

public class ScreenResponse {

    private String serialNo;
    private String location;
    private int rating;

    public ScreenResponse(String serialNo, String location, int rating) {
        this.serialNo = serialNo;
        this.location = location;
        this.rating = rating;
    }
    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

}
