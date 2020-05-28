package com.example.concentrationdetect.ui.callback;

/**
 * 训练结果回调
 */
public interface ITrainCallBack {
    /**
     * 训练成功 返回表达式
     * @param expression
     */
    void trailSuccessed(String expression);
}
