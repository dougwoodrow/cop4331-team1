package edu.cop4331.bustracker.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A user.
 */
@Entity
@Table(name = "requested_route")

public class RequestedRoute extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String to;

    private String from;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RequestedRoute requestedRoute = (RequestedRoute) o;
        return !(requestedRoute.getId() == null || getId() == null) && Objects.equals(getId(), requestedRoute.getId());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "RequestedRoute{" +
            "id:" + this.getId() +
            "}";
    }
}
