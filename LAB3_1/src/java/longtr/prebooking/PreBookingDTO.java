/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.prebooking;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class PreBookingDTO implements Serializable{
    private Date checkinDate;
    private Date checkoutDate;
    private int quantity;
    private int night;

    public PreBookingDTO() {
    }

    public PreBookingDTO(Date checkinDate, Date checkoutDate, int quantity, int night) {
        this.checkinDate = checkinDate;
        this.checkoutDate = checkoutDate;
        this.quantity = quantity;
        this.night = night;
    }
    

    public int getNight() {
        return night;
    }

    public void setNight(int night) {
        this.night = night;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    
}
