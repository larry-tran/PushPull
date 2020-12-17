/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.bookedroom;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class BookedRoomDTO implements Serializable {

    private Timestamp bookingID;
    private String roomID;
    private String roomName;
    private int quantity;
    private String hotelName;
    private double sum;

    public BookedRoomDTO() {
    }

    public BookedRoomDTO(Timestamp bookingID, String roomID, String roomName, int quantity, String hotelName, double sum) {
        this.bookingID = bookingID;
        this.roomID = roomID;
        this.roomName = roomName;
        this.quantity = quantity;
        this.hotelName = hotelName;
        this.sum = sum;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public Timestamp getBookingID() {
        return bookingID;
    }

    public void setBookingID(Timestamp bookingID) {
        this.bookingID = bookingID;
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

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public double getSum() {
        return sum;
    }

    public void setSum(double sum) {
        this.sum = sum;
    }

}
