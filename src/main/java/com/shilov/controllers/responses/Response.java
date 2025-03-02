package com.shilov.controllers.responses;

import com.shilov.common.enums.ResponseStatus;

public class Response {

    private ResponseStatus status;
    private String output;

    public Response(ResponseStatus status, String output) {
        this.status = status;
        this.output = output;
    }

    public Response(ResponseStatus status) {
        this.status = status;
    }

    public ResponseStatus getStatus() {
        return status;
    }

    public void setStatus(ResponseStatus status) {
        this.status = status;
    }

    public String getOutput() {
        return output;
    }

    public void setOutput(String output) {
        this.output = output;
    }
}
