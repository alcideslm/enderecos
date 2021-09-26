package com.alcides.config.error;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ErrorMessage {
    public ErrorMessage() {
        this.date = new Date();
    }

    public ErrorMessage(String message) {
        this();
        this.message = message;
    }

    @JsonProperty("date")
    private Date date;

    @JsonProperty("message")
    private String message;  
    
}