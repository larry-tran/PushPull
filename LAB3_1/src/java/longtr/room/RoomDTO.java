/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.room;

import java.io.Serializable;

/**
 *
 * @author Admin
 */
public class RoomDTO implements Serializable{
    private String roomID;
    private int quantity;
    private double price;
    private String type;
    private String description;
    private String emailOfOwner;
    private String address;
    private String status;

    public RoomDTO(String roomID, int quantity, double price, String type, String description, String emailOfOwner, String address, String status) {
        this.roomID = roomID;
        this.quantity = quantity;
        this.price = price;
        this.type = type;
        this.description = description;
        this.emailOfOwner = emailOfOwner;
        this.address = address;
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    
    public RoomDTO() {
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
