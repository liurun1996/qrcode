package com.lr.bean;

import java.util.ArrayList;

public class TransResultJson {
    private String from;
    private String to;
    private ArrayList<trans_result> trans_result;

    public TransResultJson() {
    }

    public TransResultJson(String from, String to, ArrayList<com.lr.bean.trans_result> trans_result) {
        this.from = from;
        this.to = to;
        this.trans_result = trans_result;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public ArrayList<com.lr.bean.trans_result> getTrans_result() {
        return trans_result;
    }

    public void setTrans_result(ArrayList<com.lr.bean.trans_result> trans_result) {
        this.trans_result = trans_result;
    }

    @Override
    public String toString() {
        return "TransResultJson{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", trans_result=" + trans_result +
                '}';
    }
}
