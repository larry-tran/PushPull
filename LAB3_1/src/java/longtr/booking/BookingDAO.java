/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.booking;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class BookingDAO implements Serializable {

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public void addBooking(BookingDTO bDto) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO Booking(bookingID,checkinDate,checkoutDate,email,total,status) VALUES(?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setTimestamp(1, bDto.getBookingID());
                stm.setDate(2, bDto.getCheckinDate());
                stm.setDate(3, bDto.getCheckoutDate());
                stm.setString(4, bDto.getEmail());
                stm.setDouble(5, bDto.getTotal());
                stm.setString(6, bDto.getStatus());
                stm.executeUpdate();
            }
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

    public boolean cancelBooking(Timestamp bookingID) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE Booking SET status = ? WHERE bookingID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "inactive");
                stm.setTimestamp(2, bookingID);
                int row = stm.executeUpdate();
                if(row > 0){
                    result = true;
                }
            }
        } finally{
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }
 
    
    public void deleteBooking(Timestamp currentDate) throws NamingException, SQLException{
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "UPDATE Booking SET status = ? WHERE bookingID = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "inactive");
                stm.setTimestamp(2, currentDate);
                
                stm.executeUpdate();
            }
        } finally{
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }

}