/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.history;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longtr.bookedroom.BookedRoomDAO;
import longtr.bookedroom.BookedRoomDTO;
import longtr.booking.BookingDAO;
import longtr.booking.BookingDTO;
import longtr.randi.RandIDAO;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class HistoryBookingDAO implements Serializable {

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public List<HistoryBookingDTO> loadHistoryBooking(String email) throws NamingException, SQLException {
        List<HistoryBookingDTO> listHistoryBooking = new ArrayList<>();
        BookedRoomDAO brDao = new BookedRoomDAO();

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT bookingID, checkinDate, checkoutDate, email, total, status FROM Booking WHERE status != ?  AND email = ? ORDER BY bookingID DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, "inactive");
                stm.setString(2, email);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Timestamp bookingID = rs.getTimestamp("bookingID");
                    Date checkinDate = rs.getDate("checkinDate");
                    Date checkoutDate = rs.getDate("checkoutDate");
                    email = rs.getString("email");
                    double total = rs.getDouble("total");
                    String status = rs.getString("status");

                    BookingDTO bDto = new BookingDTO(bookingID, checkinDate, checkoutDate, email, total, status);
                    List<BookedRoomDTO> listBookedRoom = brDao.getBookedRoom(bookingID);

                    listHistoryBooking.add(new HistoryBookingDTO(listBookedRoom, bDto));
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
        return listHistoryBooking;
    }

    public HistoryBookingDTO searchByBookingID(Timestamp bookingID, String email) throws NamingException, SQLException {
        HistoryBookingDTO hbDto = null;
        BookedRoomDAO brDao = new BookedRoomDAO();

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT bookingID, checkinDate, checkoutDate, email, total, status FROM Booking WHERE status != ? AND bookingID = ? AND email LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "inactive");
                stm.setTimestamp(2, bookingID);
                stm.setString(3, email);
                rs = stm.executeQuery();
                while (rs.next()) {
                    bookingID = rs.getTimestamp("bookingID");
                    Date checkinDate = rs.getDate("checkinDate");
                    Date checkoutDate = rs.getDate("checkoutDate");
                    email = rs.getString("email");
                    double total = rs.getDouble("total");
                    String status = rs.getString("status");

                    BookingDTO bDto = new BookingDTO(bookingID, checkinDate, checkoutDate, email, total, status);
                    List<BookedRoomDTO> listBookedRoom = brDao.getBookedRoom(bookingID);

                    hbDto = new HistoryBookingDTO(listBookedRoom, bDto);
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
        return hbDto;
    }

    public List<HistoryBookingDTO> searchByDate(Timestamp date1, Timestamp date2, String email) throws NamingException, SQLException {
        List<HistoryBookingDTO> listHistoryBooking = new ArrayList<>();
        BookedRoomDAO brDao = new BookedRoomDAO();

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT bookingID, checkinDate, checkoutDate, email, total, status FROM Booking WHERE status != ? AND bookingID >= ? AND bookingID < ?  AND email LIKE ? ORDER BY bookingID DESC";
                stm = con.prepareStatement(sql);
                stm.setString(1, "inactive");
                stm.setTimestamp(2, date1);
                stm.setTimestamp(3, date2);
                stm.setString(4, email);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Timestamp bookingID = rs.getTimestamp("bookingID");
                    Date checkinDate = rs.getDate("checkinDate");
                    Date checkoutDate = rs.getDate("checkoutDate");
                    email = rs.getString("email");
                    double total = rs.getDouble("total");
                    String status = rs.getString("status");

                    BookingDTO bDto = new BookingDTO(bookingID, checkinDate, checkoutDate, email, total, status);
                    List<BookedRoomDTO> listBookedRoom = brDao.getBookedRoom(bookingID);

                    listHistoryBooking.add(new HistoryBookingDTO(listBookedRoom, bDto));
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
        return listHistoryBooking;
    }

    public List<HistoryBookingDTO> loadToCheckBooking() throws NamingException, SQLException {
        List<HistoryBookingDTO> listHistoryBooking = new ArrayList();
        BookedRoomDAO brDao = new BookedRoomDAO();

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT bookingID, checkinDate, checkoutDate, email, total, status FROM Booking "
                        + "WHERE status != ? "
                        + "ORDER BY checkinDate ASC";
                stm = con.prepareStatement(sql);
                stm.setString(1, "inactive");
                rs = stm.executeQuery();
                while (rs.next()) {

                    Timestamp bookingID = rs.getTimestamp("bookingID");
                    Date checkinDate = rs.getDate("checkinDate");
                    Date checkoutDate = rs.getDate("checkoutDate");
                    String email = rs.getString("email");
                    double total = rs.getDouble("total");
                    String status = rs.getString("status");

                    BookingDTO bDto = new BookingDTO(bookingID, checkinDate, checkoutDate, email, total, status);
                    List<BookedRoomDTO> listBookedRoom = brDao.getBookedRoom(bookingID);

                    listHistoryBooking.add(new HistoryBookingDTO(listBookedRoom, bDto));
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

        return listHistoryBooking;
    }

    public void subtractRoomAtCurrentDate(Date currentDate) throws NamingException, SQLException {
        BookedRoomDAO brDao = new BookedRoomDAO();
        RandIDAO riDao = new RandIDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT bookingID FROM Booking "
                        + "WHERE status = ? AND checkinDate <= ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "active");
                stm.setDate(2, currentDate);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Timestamp bookingID = rs.getTimestamp("bookingID");

                    List<BookedRoomDTO> listBookedRoom = brDao.getBookedRoom(bookingID);
                    for (BookedRoomDTO brDto : listBookedRoom) {
                        riDao.subtractRoom(brDto.getRoomID(), brDto.getQuantity());
                    }

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
    }
    
    public void setUsingBooking(Date currentDate) throws NamingException, SQLException{
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "UPDATE Booking SET status = ? WHERE checkinDate = ? AND status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "using");
                stm.setDate(2, currentDate);
                stm.setString(3, "active");
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

    public void addRoomAtCurrentDate(Date currentDate) throws NamingException, SQLException {
        BookedRoomDAO brDao = new BookedRoomDAO();
        BookingDAO bgDao = new BookingDAO();
        RandIDAO riDao = new RandIDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT bookingID FROM Booking "
                        + "WHERE status = ? AND checkoutDate <= ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "using");
                stm.setDate(2, currentDate);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Timestamp bookingID = rs.getTimestamp("bookingID");

                    List<BookedRoomDTO> listBookedRoom = brDao.getBookedRoom(bookingID);
                    for (BookedRoomDTO brDto : listBookedRoom) {
                        riDao.increaseRoom(brDto.getRoomID(), brDto.getQuantity());
                    }
                    bgDao.deleteBooking(bookingID);
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
    }
}
