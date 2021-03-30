package com.example.universalconverter.model;

public class Request {
    private final String from;
    private final String to;

    public Request(String from, String to) {
        from = from.replaceAll(" ", "");
        to = to.replaceAll(" ", "");

        if (from.matches("(.*)/(.*)") && to.equals("")) {       // changing request from type "boo/foo" ""
            this.from = from.split("/")[0];                         // to "boo" "foo"
            this.to = from.split("/")[1];
        } else if (from.equals("") && to.matches("(.*)/(.*)")) {
            this.from = to.split("/")[0];
            this.to = to.split("/")[1];
        } else {
            this.from = from.replaceAll(" ", "");
            this.to = to.replaceAll(" ", "");
        }
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }



}
