package com.example.universalconverter.model;

public class Request {
    private final String fromUnits;
    private final String toUnits;

    public Request(String fromUnits, String toUnits) {
        this.fromUnits = fromUnits.replaceAll(" ", "");
        this.toUnits = toUnits.replaceAll(" ", "");
    }

    public String getFromUnits() {
        return fromUnits;
    }

    public String getToUnits() {
        return toUnits;
    }

    @Override
    public String toString() {
        return "Request{" +
                "fromUnits='" + fromUnits + '\'' +
                ", toUnits='" + toUnits + '\'' +
                '}';
    }
}
