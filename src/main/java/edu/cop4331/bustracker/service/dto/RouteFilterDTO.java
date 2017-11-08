package edu.cop4331.bustracker.service.dto;

public class RouteFilterDTO {
    private String to;

    private String from;

    public RouteFilterDTO() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }
}
