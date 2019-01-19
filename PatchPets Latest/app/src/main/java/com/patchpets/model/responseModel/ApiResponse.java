package com.patchpets.model.responseModel;

public class ApiResponse {

    /**
     * message : User hide/show successfully
     * status : 1
     */

    private String message;
    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
