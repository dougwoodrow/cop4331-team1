package edu.cop4331.bustracker.domain;

import com.google.maps.model.LatLng;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * A route.
 */
@Entity
@Table(name = "route")
public class Route extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private DateTime arrivalDateTime;

    @Column
    private DateTime departureDateTime;

    @ElementCollection
    private List<LatLng> busLinePath;

    @Column
    private LatLng startLocation;

    @Column
    private LatLng endLocation;

    @Column
    private String startAddress;

    @Column
    private String endAddress;

    @Column
    private String fare;

    public Route() {
    }

    public Route(DateTime arrivalDateTime, DateTime departureDateTime, List<LatLng> busLinePath, LatLng startLocation, LatLng endLocation, String startAddress, String endAddress, String fare) {
        this.arrivalDateTime = arrivalDateTime;
        this.departureDateTime = departureDateTime;
        this.busLinePath = busLinePath;
        this.startLocation = startLocation;
        this.endLocation = endLocation;
        this.startAddress = startAddress;
        this.endAddress = endAddress;
        this.fare = fare;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public void setBusLinePath(List<LatLng> busLinePath) {
        this.busLinePath = busLinePath;
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
