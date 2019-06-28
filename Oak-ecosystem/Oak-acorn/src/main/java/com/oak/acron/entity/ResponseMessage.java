package com.oak.acron.entity;

/**
 * Created by Oak on 2018/7/5.
 * Description: 响应体
 */
public class ResponseMessage {
    private String responseMessage;
    public String date;

    public ResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getResponseMessage() {
        return responseMessage;
    }
}
