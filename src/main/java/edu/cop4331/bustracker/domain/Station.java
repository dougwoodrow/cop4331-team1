package edu.cop4331.bustracker.domain;

import javax.persistence.*;
import java.io.Serializable;

/**
 * A route.
 */
@Entity
@Table(name = "station")
public class Station extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column
    private String address;

    @OneToOne
    private User user;

    public Station() {
    }

    public Station(String address, User user) {
        this.address = address;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
