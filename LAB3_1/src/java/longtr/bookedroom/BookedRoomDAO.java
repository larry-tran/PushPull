/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.bookedroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class BookedRoomDAO {

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public void addBookedRoom(BookedRoomDTO dto) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO BookedRoom(bookedRoomID,roomID,roomName,quantity,hotelName,sum) VALUES (?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setTimestamp(1, dto.getBookingID());
                stm.setString(2, dto.getRoomID());
                stm.setString(3, dto.getRoomName());
                stm.setInt(4, dto.getQuantity());
                stm.setString(5, dto.getHotelName());
                stm.setDouble(6, dto.getSum());

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

    public List<BookedRoomDTO> getBookedRoom(Timestamp bookingID) throws NamingException, SQLException {
        List<BookedRoomDTO> listBookedRoom = new ArrayList<>();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT bookedRoomID, roomID,roomName, quantity, hotelName, sum FROM BookedRoom WHERE bookedRoomID = ?";
                stm = con.prepareStatement(sql);
                stm.setTimestamp(1, bookingID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Timestamp bookedRoomID = rs.getTimestamp("bookedRoomID");
                    String roomID = rs.getString("roomID");
                    String roomName = rs.getString("roomName");
                    int quantity = rs.getInt("quantity");
                    String hotelName = rs.getString("hotelName");
                    double sum = rs.getDouble("sum");

                    listBookedRoom.add(new BookedRoomDTO(bookedRoomID, roomID, roomName, quantity, hotelName, sum));
                }
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
        return listBookedRoom;
    }
}
