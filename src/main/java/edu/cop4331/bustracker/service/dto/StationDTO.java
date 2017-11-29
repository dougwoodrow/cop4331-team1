package edu.cop4331.bustracker.service.dto;

/**
 * A DTO representing a route.
 */
public class StationDTO {

    private Long id;

    private String address;

    private UserDTO user;

    public StationDTO() {
    }

    public StationDTO(Long id, String address, UserDTO user) {
        this.id = id;
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
