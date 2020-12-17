/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.booking;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

/**
 *
 * @author Admin
 */
public class BookingDTO implements Serializable{
    private Timestamp bookingID;
    private Date checkinDate;
    private Date checkoutDate;
    private String email;
    private double total;
    private String status;

    public BookingDTO() {
    }

    public BookingDTO(Timestamp bookingDate, Date checkinDate, Date checkoutDate, String email, double total, String status) {
        this.bookingID = bookingDate;
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.email = email;
        this.total = total;
        this.status = status;
    }

    public Timestamp getBookingID() {
        return bookingID;
    }

    public void setBookingID(Timestamp bookingID) {
        this.bookingID = bookingID;
    }

   

    public Date getCheckinDate() {
        return checkinDate;
    }

    public void setCheckinDate(Date checkinDate) {
        this.checkinDate = checkinDate;
    }

    public Date getCheckoutDate() {
        return checkoutDate;
    }

    public void setCheckoutDate(Date checkoutDate) {
        this.checkoutDate = checkoutDate;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    
}
