/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.discount;

import java.io.Serializable;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class DiscountDTO implements Serializable {

    private String discountCode;
    private int detail;
    private Date expDate;
    private String status;
    private String email;
    private String address;

    public DiscountDTO() {
    }

    public DiscountDTO(String discountCode, int detail, Date expDate, String status, String email, String address) {
        this.discountCode = discountCode;
        this.detail = detail;
        this.expDate = expDate;
        this.status = status;
        this.email = email;
        this.address = address;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }

    public String getDiscountCode() {
        return discountCode;
    }

    public void setDiscountCode(String discountCode) {
        this.discountCode = discountCode;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

}
