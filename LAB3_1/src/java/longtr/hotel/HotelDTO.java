/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.hotel;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class HotelDTO implements Serializable{
    private String emailOfOwner;
    private String address;
    private String phone;
    private String description;
    private String name;
    private String location;
    private String status;

    public HotelDTO() {
    }

    public HotelDTO(String emailOfOwner, String address, String phone, String description, String name, String location, String status) {
        this.emailOfOwner = emailOfOwner;
        this.address = address;
        this.phone = phone;
        this.description = description;
        this.name = name;
        this.location = location;
        this.status = status;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    
    public String getEmailOfOwner() {
        return emailOfOwner;
    }

    public void setEmailOfOwner(String emailOfOwner) {
        this.emailOfOwner = emailOfOwner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
