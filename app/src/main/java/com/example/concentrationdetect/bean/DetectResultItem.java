package com.example.concentrationdetect.bean;

/**
 * 训练页面检测结果条目
 */
public class DetectResultItem {
    float grayValue;
    float concentrationValue;

    public float getConcentrationValue() {
        return concentrationValue;
    }

    public float getGrayValue() {
        return grayValue;
    }

    public void setConcentrationValue(float concentrationValue) {
        this.concentrationValue = concentrationValue;
    }

    public void setGrayValue(float grayValue) {
        this.grayValue = grayValue;
    }
}
