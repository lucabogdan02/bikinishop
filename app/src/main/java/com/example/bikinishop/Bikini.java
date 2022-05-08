package com.example.bikinishop;

public class Bikini {
    private int bust, underbust, waist, bottomLength;
    private String style, other;

    public Bikini() {
        this.bust = 0;
        this.underbust = 0;
        this.waist = 0;
        this.bottomLength = 0;
        this.style = "";
        this.other = "";
    }

    public Bikini(int bust, int underbust, int waist, int bottomLength, String style, String other) {
        this.bust = bust;
        this.underbust = underbust;
        this.waist = waist;
        this.bottomLength = bottomLength;
        this.style = style;
        this.other = other;
    }

    public int getBust() {
        return bust;
    }

    public void setBust(int bust) {
        this.bust = bust;
    }

    public int getUnderbust() {
        return underbust;
    }

    public void setUnderbust(int underbust) {
        this.underbust = underbust;
    }

    public int getWaist() {
        return waist;
    }

    public void setWaist(int waist) {
        this.waist = waist;
    }

    public int getBottomLength() {
        return bottomLength;
    }

    public void setBottomLength(int bottomLength) {
        this.bottomLength = bottomLength;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }
}
