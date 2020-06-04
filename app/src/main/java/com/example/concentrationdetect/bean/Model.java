package com.example.concentrationdetect.bean;

/**
 * 模型实体类
 */
public class Model {
    String name;//模型名
    float[] arguments;//参数数组

    public float[] getArguments() {
        return arguments;
    }

    public String getName() {
        return name;
    }

    public void setArguments(float[] arguments) {
        this.arguments = arguments;
    }

    public void setName(String name) {
        this.name = name;
    }
}
