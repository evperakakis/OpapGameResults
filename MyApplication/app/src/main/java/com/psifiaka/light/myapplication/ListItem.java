package com.psifiaka.light.myapplication;

public class ListItem {

    private String drawTime;
    private String drawNo;
    private String drawResults;

    public ListItem(String drawTime, String drawNo, String drawResults) {
        this.drawTime = drawTime;
        this.drawNo = drawNo;
        this.drawResults = drawResults;
    }

    public String getDrawTime() {
        return drawTime;
    }

    public String getDrawNo() {
        return drawNo;
    }

    public String getDrawResults() {
        return drawResults;
    }
}
