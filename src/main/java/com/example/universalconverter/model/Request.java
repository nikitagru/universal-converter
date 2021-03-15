package com.example.universalconverter.model;

public class Request {
    private final String from;
    private final String to;

    public Request(String from, String to) {
        this.from = from.replaceAll(" ", "").toLowerCase();
        this.to = to.replaceAll(" ", "").toLowerCase();
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

}
