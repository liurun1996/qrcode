package com.lr.bean;

public class trans_result{
    private String src;
    private String dst;

    public trans_result(String src, String dst) {
        this.src = src;
        this.dst = dst;
    }

    public trans_result() {
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getDst() {
        return dst;
    }

    public void setDst(String dst) {
        this.dst = dst;
    }

    @Override
    public String toString() {
        return "trans_result{" +
                "src='" + src + '\'' +
                ", dst='" + dst + '\'' +
                '}';
    }
}
