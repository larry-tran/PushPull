/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.randi;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longtr.bookedroom.BookedRoomDTO;
import longtr.history.HistoryBookingDAO;
import longtr.history.HistoryBookingDTO;
import longtr.image.ImageDAO;
import longtr.image.ImageDTO;
import longtr.room.RoomDTO;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class RandIDAO {

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public List<RandIDTO> loadAvailableRoom(String emailOfOwner, String address, int quantity) throws NamingException, SQLException {
        List<RandIDTO> listRI = new ArrayList<>();
        ImageDAO iDao = new ImageDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT roomID, quantity, price, type ,description, emailOfOwner, address, status FROM Room WHERE status = ? AND emailOfOwner = ? AND address = ? AND quantity >= ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "available");
                stm.setString(2, emailOfOwner);
                stm.setString(3, address);
                stm.setInt(4, quantity);

                rs = stm.executeQuery();
                while (rs.next()) {
                    String roomID = rs.getString("roomID");
                    quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String type = rs.getString("type");
                    String description = rs.getString("description");
                    emailOfOwner = rs.getString("emailOfOwner");
                    address = rs.getString("address");

                    String status = rs.getString("status");

                    RoomDTO rDto = new RoomDTO(roomID, quantity, price, type, description, emailOfOwner, address, status);
                    List<ImageDTO> iDto = iDao.loadImageForRoom(emailOfOwner, address, roomID);
                    listRI.add(new RandIDTO(rDto, iDto, 0));
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

        return listRI;
    }

//    public List<RandIDTO> loadAvailableRoom12(String emailOfOwner, String address, int inputQuantity, Date checkin, Date checkout) throws NamingException, SQLException {
//        List<RandIDTO> listRI = new ArrayList<>();
//        ImageDAO iDao = new ImageDAO();
//        HistoryBookingDAO hbDao = new HistoryBookingDAO();
//
//        try {
//            Date maxDate = getMaxDate();
//            Date minDate = getMinDate();
//
////            Date minDate = new Date(System.currentTimeMillis());
////            Date maxDate = new Date(System.currentTimeMillis());
//            con = DBHelper.getConnection();
//            if (con != null) {
//                String sql = "SELECT roomID, quantity, price, type ,description, emailOfOwner, address, status FROM Room WHERE status = ? AND emailOfOwner = ? AND address = ? AND quantity >= ?";
//                stm = con.prepareStatement(sql);
//                stm.setString(1, "available");
//                stm.setString(2, emailOfOwner);
//                stm.setString(3, address);
//                stm.setInt(4, inputQuantity);
//
//                rs = stm.executeQuery();
//                while (rs.next()) {
//                    boolean pass = false;
//                    int result = 0;
//                    String roomID = rs.getString("roomID");
//                    int quantity = rs.getInt("quantity");
//                    double price = rs.getDouble("price");
//                    String type = rs.getString("type");
//                    String description = rs.getString("description");
//                    emailOfOwner = rs.getString("emailOfOwner");
//                    address = rs.getString("address");
//                    String status = rs.getString("status");
//
//                    if (minDate != null && maxDate != null) {
//                        if (checkout.getTime() <= minDate.getTime() || checkin.getTime() >= maxDate.getTime()) {
//                            pass = true;
//                        } else {
//                            List<HistoryBookingDTO> listHistory = hbDao.loadToCheckBooking();
//                            if (listHistory != null) {
//                                if (listHistory.size() > 1) {
//                                    for (int i = 0; i < listHistory.size() - 1; i++) {
//                                        if (listHistory.get(i).getBookingDto().getCheckoutDate().getTime() <= checkin.getTime() && listHistory.get(i + 1).getBookingDto().getCheckinDate().getTime() >= checkout.getTime()) {
//                                            pass = true;
//                                            break;
//                                        } else {
//                                            for (BookedRoomDTO br : listHistory.get(i).getListBookedRoom()) {
//                                                if (br.getRoomID().equals(roomID)) {
//                                                    result = quantity - br.getQuantity();
//
//                                                    if (result >= inputQuantity) {
//                                                        pass = true;
//                                                        break;
//                                                    } else {
//                                                        pass = false;
//                                                        break;
//                                                    }
//                                                } else {
//                                                    result = quantity;
//                                                    pass = true;
//                                                    break;
//                                                }
//                                            }
//                                        }
//                                    }
//                                } else if (listHistory.size() == 1) {
//                                    if (listHistory.get(0).getBookingDto().getCheckoutDate().getTime() <= checkin.getTime() || listHistory.get(0).getBookingDto().getCheckinDate().getTime() >= checkout.getTime()) {
//                                        pass = true;
//                                        break;
//                                    } else if (listHistory.get(0).getBookingDto().getCheckinDate().getTime() <= checkin.getTime() && listHistory.get(0).getBookingDto().getCheckoutDate().getTime() >= checkout.getTime()) {
//                                        for (BookedRoomDTO br : listHistory.get(0).getListBookedRoom()) {
//                                            if (br.getRoomID().equals(roomID)) {
//                                                result = quantity - br.getQuantity();
//                                                if (result >= inputQuantity) {
//                                                    pass = true;
//                                                    break;
//                                                } else {
//                                                    pass = false;
//                                                    break;
//                                                }
//                                            } else {
//                                                result = quantity;
//                                                pass = true;
//                                                break;
//                                            }
//                                        }
//                                    }
//                                }
//                            } else {
//                                result = quantity;
//                                pass = true;
//                            }
//                        }
//                    }else{
//                        result = quantity;
//                        pass = true;
//                    }
//                    if (pass) {
//                        RoomDTO rDto = new RoomDTO(roomID, result, price, type, description, emailOfOwner, address, status);
//                        List<ImageDTO> iDto = iDao.loadImageForRoom(emailOfOwner, address, roomID);
//                        listRI.add(new RandIDTO(rDto, iDto, 0));
//                    }
//                }
//            }
//        } finally {
//            if (rs != null) {
//                rs.close();
//            }
//            if (stm != null) {
//                stm.close();
//            }
//            if (con != null) {
//                con.close();
//            }
//        }
//
//        return listRI;
//    }
    public RandIDTO checkAvailableBookingRoom(RandIDTO riDto ,int inputQuantity, Date checkin, Date checkout) throws NamingException, SQLException {
        RandIDTO result = riDto;
        RandIDTO finalResult = null;
        Date maxDate = getMaxDate();
        Date minDate = getMinDate();
        
        HistoryBookingDAO hbDao = new HistoryBookingDAO();
        
        boolean pass = false;
        int currentQuan = 0;
        
        if (minDate != null && maxDate != null) {
            if (checkout.getTime() <= minDate.getTime() || checkin.getTime() >= maxDate.getTime()) {
                pass = true;
            } else {
                List<HistoryBookingDTO> listHistory = hbDao.loadToCheckBooking();
                if (listHistory != null) {
                    if (listHistory.size() > 1) {
                        for (int i = 0; i < listHistory.size() - 1; i++) {
                            if (listHistory.get(i).getBookingDto().getCheckoutDate().getTime() <= checkin.getTime() && listHistory.get(i + 1).getBookingDto().getCheckinDate().getTime() >= checkout.getTime()) {
                                pass = true;
                                break;
                            } else {
                                for (BookedRoomDTO br : listHistory.get(i).getListBookedRoom()) {
                                    if (br.getRoomID().equals(result.getRoomDto().getRoomID())) {
                                        currentQuan = result.getRoomDto().getQuantity() - br.getQuantity();

                                        if (currentQuan >= inputQuantity) {
                                            pass = true;
                                            break;
                                        } else {
                                            pass = false;
                                            break;
                                        }
                                    } else {
                                        currentQuan = result.getRoomDto().getQuantity();
                                        pass = true;
                                        break;
                                    }
                                }
                            }
                        }
                    } else if (listHistory.size() == 1) {
                        if (listHistory.get(0).getBookingDto().getCheckoutDate().getTime() <= checkin.getTime() || listHistory.get(0).getBookingDto().getCheckinDate().getTime() >= checkout.getTime()) {
                            pass = true;
//                                        break;
                        } else if (listHistory.get(0).getBookingDto().getCheckinDate().getTime() <= checkin.getTime() && listHistory.get(0).getBookingDto().getCheckoutDate().getTime() >= checkout.getTime()) {
                            for (BookedRoomDTO br : listHistory.get(0).getListBookedRoom()) {
                                if (br.getRoomID().equals(result.getRoomDto().getRoomID())) {
                                    currentQuan = result.getRoomDto().getQuantity() - br.getQuantity();
                                    if (currentQuan >= inputQuantity) {
                                        pass = true;
                                        break;
                                    } else {
                                        pass = false;
                                        break;
                                    }
                                } else {
                                    currentQuan = result.getRoomDto().getQuantity();
                                    pass = true;
                                    break;
                                }
                            }
                        }
                    }
                } else {
                    currentQuan = result.getRoomDto().getQuantity();
                    pass = true;
                }
            }
        } else {
            currentQuan = result.getRoomDto().getQuantity();
            pass = true;
        }
        if (pass) {
            finalResult = result;
        }else{
            result.getRoomDto().setStatus("using");
            finalResult = result;
        }
        return finalResult;
    }

    public Date getMaxDate() throws NamingException, SQLException {
        Date maxDate = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT MAX(checkoutDate) as checkoutDate FROM Booking WHERE status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "active");
                rs = stm.executeQuery();
                if (rs.next()) {
                    maxDate = rs.getDate("checkoutDate");
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

        return maxDate;
    }

    public Date getMinDate() throws NamingException, SQLException {
        Date minDate = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT MIN(checkinDate) as checkinDate FROM Booking WHERE status = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "active");
                rs = stm.executeQuery();
                if (rs.next()) {
                    minDate = rs.getDate("checkinDate");
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

        return minDate;
    }

    public RandIDTO loadRoomInCart(String roomID, int quantity) throws NamingException, SQLException {
        RandIDTO riDto = null;
        ImageDAO iDao = new ImageDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT Room.roomID, Room.quantity, Room.price, Room.type ,Room.description, Room.emailOfOwner, Room.address, Room.status, Hotel.name "
                        + "  FROM Room "
                        + "  LEFT JOIN Hotel ON Room.emailOfOwner = Hotel.emailOfOwner AND Room.address = Hotel.address "
                        + "  WHERE Hotel.status LIKE ? AND Room.roomID LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "available");
//                stm.setString(2, "available");
//                stm.setInt(2, quantity);
                stm.setString(2, roomID);

                rs = stm.executeQuery();
                if (rs.next()) {
                    roomID = rs.getString("roomID");
                    quantity = rs.getInt("quantity");
                    double price = rs.getDouble("price");
                    String type = rs.getString("type");
                    String description = rs.getString("description");
                    String emailOfOwner = rs.getString("emailOfOwner");
                    String name = rs.getString("name");
                    //emailOfOwner = name in roomDTO
                    String address = rs.getString("address");
                    String status = rs.getString("status");

                    RoomDTO rDto = new RoomDTO(roomID, quantity, price, type, description, name, address, status);
                    List<ImageDTO> iDto = iDao.loadImageForRoom(emailOfOwner, address, roomID);
                    riDto = new RandIDTO(rDto, iDto, 0);
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

        return riDto;
    }

    public boolean subtractRoom(String roomID, int quantity) throws NamingException, SQLException {
        boolean result = false;
        int option = checkQuantity(roomID, quantity);

        String sql = "";

        if (option == -1) {
            return false;
        } else if (option == 1) {
            sql = "UPDATE Room SET quantity = quantity - ? WHERE roomID LIKE ? AND status LIKE ?";
        } else if (option == 0) {
            sql = "UPDATE Room SET quantity = quantity - ? , status = 'unavailable' WHERE roomID LIKE ? AND status LIKE ?";
        }

        try {
            con = DBHelper.getConnection();
            if (con != null) {

                stm = con.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setString(2, roomID);
                stm.setString(3, "available");
                int row = stm.executeUpdate();
                if (row > 0) {
                    result = true;
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    private int checkQuantity(String roomID, int quantity) throws NamingException, SQLException {
        int result = -1;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT quantity FROM Room WHERE roomID LIKE ? AND status LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, roomID);
                stm.setString(2, "available");
                rs = stm.executeQuery();
                if (rs.next()) {
                    int roomQuantity = rs.getInt("quantity");
                    if (roomQuantity > quantity) {
                        result = 1;
                    } else if (roomQuantity < quantity) {
                        result = -1;
                    } else if (roomQuantity == quantity) {
                        result = 0;
                    }
                }
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
        return result;
    }

    public void increaseRoom(String roomID, int quantity) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE Room SET quantity = quantity + ? ,status = ? WHERE roomID LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, quantity);
                stm.setString(2, "available");
                stm.setString(3, roomID);
                stm.executeUpdate();
            }
        } finally {
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }
}
