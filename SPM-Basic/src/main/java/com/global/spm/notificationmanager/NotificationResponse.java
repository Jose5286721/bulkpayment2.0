package com.global.spm.notificationmanager;

import com.global.spm.basic.BasicResponseDto;

public class NotificationResponse {
    private String destiny;
    private BasicResponseDto response;

    public String getDestiny() {
        return destiny;
    }

    public void setDestiny(String destiny) {
        this.destiny = destiny;
    }

    public BasicResponseDto getResponse() {
        return response;
    }

    public void setResponse(BasicResponseDto response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "NotificationResponse{" +
                "destiny='" + destiny + '\'' +
                ", response=" + response +
                '}';
    }
}
