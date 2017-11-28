package edu.cop4331.bustracker.service.dto;

import com.google.maps.model.EncodedPolyline;
import com.google.maps.model.LatLng;
import org.joda.time.DateTime;

import java.util.List;

/**
 * A DTO representing a route.
 */
public class RouteDTO {

    private DateTime arrivalDateTime;

    private DateTime departureDateTime;

    private List<LatLng> busLinePath;

    private LatLng startLocation;

    private LatLng endLocation;

    private String startAddress;

    private String endAddress;

    private String fare;

    public RouteDTO() {
    }

    public RouteDTO(DateTime arrivalDateTime, DateTime departureDateTime, EncodedPolyline busLinePath, LatLng startLocation, LatLng endLocation, String startAddress, String endAddress, String fare) {
        this.arrivalDateTime = arrivalDateTime;
        this.departureDateTime = departureDateTime;
        this.busLinePath = busLinePath.decodePath();
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.fare = fare;
    }

    public DateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(DateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public DateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(DateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public List<LatLng> getBusLinePath() {
        return busLinePath;
    }

    public void setBusLinePath(EncodedPolyline busLinePath) {
        this.busLinePath = busLinePath.decodePath();
    }

    public LatLng getStartLocation() {
        return startLocation;
    }

    public void setStartLocation(LatLng startLocation) {
        this.startLocation = startLocation;
    }

    public LatLng getEndLocation() {
        return endLocation;
    }

    public void setEndLocation(LatLng endLocation) {
        this.endLocation = endLocation;
    }

    public String getStartAddress() {
        return startAddress;
    }

    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }

    public String getEndAddress() {
        return endAddress;
    }

    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }
}
