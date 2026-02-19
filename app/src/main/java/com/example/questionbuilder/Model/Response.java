package com.example.questionbuilder.Model;

public class Response {
    private String responseText;

    public Response() {
        // Default constructor required for Firebase
    }

    public Response(String responseText) {
        this.responseText = responseText;
    }

    public String getResponseText() {
        return responseText;
    }

    public void setResponseText(String responseText) {
        this.responseText = responseText;
    }
}
