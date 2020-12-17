/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.handi;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longtr.bookedroom.BookedRoomDTO;
import longtr.hotel.HotelDTO;
import longtr.image.ImageDAO;
import longtr.image.ImageDTO;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class HandIDAO implements Serializable {

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public List<HandIDTO> loadHotel() throws NamingException, SQLException {
        List<HandIDTO> listHandI = new ArrayList<>();

        ImageDAO iDao = new ImageDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT emailOfOwner, address, phone , description, name, location, status FROM Hotel WHERE status =?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "available");
                rs = stm.executeQuery();
                while (rs.next()) {
                    String emailOfOwner = rs.getString("emailOfOwner");
                    String address = rs.getString("address");
                    String description = rs.getString("description");
                    String name = rs.getString("name");
                    String location = rs.getString("location");
                    String status = rs.getString("status");
                    HotelDTO hDto = new HotelDTO(emailOfOwner, address, name, description, name, location, status);
                    List<ImageDTO> iDto = iDao.loadImageForHotel(emailOfOwner, address);
                    listHandI.add(new HandIDTO(hDto, iDto));
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
        return listHandI;
    }

    public List<String> loadArea() throws NamingException, SQLException {
        List<String> listLocation = new ArrayList<>();

        List<HandIDTO> listHandI = loadHotel();

        for (HandIDTO handIDTO : listHandI) {
            if (!listLocation.contains(handIDTO.getHotelDto().getLocation())) {
                listLocation.add(handIDTO.getHotelDto().getLocation());
            }
        }
        return listLocation;
    }

    public List<HandIDTO> searchHotel(String txtSearch) throws NamingException, SQLException {
        List<HandIDTO> listHiDto = new ArrayList<>();
        ImageDAO iDao = new ImageDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT emailOfOwner, address, phone , description, name, location, status FROM Hotel WHERE status LIKE ? AND name LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "available");
                stm.setString(2, "%" + txtSearch + "%");
                rs = stm.executeQuery();
                while (rs.next()) {
                    String emailOfOwner = rs.getString("emailOfOwner");
                    String address = rs.getString("address");
                    String description = rs.getString("description");
                    String phone = rs.getString("phone");
                    String name = rs.getString("name");
                    String location = rs.getString("location");
                    String status = rs.getString("status");
                    HotelDTO hDto = new HotelDTO(emailOfOwner, address, phone, description, name, location, status);
                    List<ImageDTO> iDto = iDao.loadImageForHotel(emailOfOwner, address);
                    listHiDto.add(new HandIDTO(hDto, iDto));
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
        return listHiDto;
    }

    public List<HandIDTO> searchHotelByOption(String location, int quantity) throws NamingException, SQLException {
        List<HandIDTO> listHiDto = new ArrayList<>();
        ImageDAO iDao = new ImageDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "  SELECT Hotel.emailOfOwner, Hotel.address, Hotel.name, Hotel.description, Hotel.location, Hotel.phone, Hotel.status "
                        + "  FROM Hotel "
                        + "  RIGHT JOIN Room ON Hotel.emailOfOwner = Room.emailOfOwner AND Hotel.address = Room.address "
                        + "  WHERE Hotel.status = ? AND Room.status = ? AND Hotel.location LIKE ? AND Room.quantity >= ? "
                        + "  GROUP BY Hotel.address, Hotel.emailOfOwner, Hotel.name, Hotel.description, Hotel.location, Hotel.phone, Hotel.status";

                stm = con.prepareStatement(sql);
                stm.setString(1, "available");
                stm.setString(2, "available");
                stm.setString(3, "%" + location + "%");
                stm.setInt(4, quantity);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String emailOfOwner = rs.getString("emailOfOwner");
                    String address = rs.getString("address");
                    String description = rs.getString("description");
                    String phone = rs.getString("phone");
                    String name = rs.getString("name");
                    location = rs.getString("location");
                    String status = rs.getString("status");

                    HotelDTO hDto = new HotelDTO(emailOfOwner, address, phone, description, name, location, status);
                    List<ImageDTO> iDto = iDao.loadImageForHotel(emailOfOwner, address);
                    listHiDto.add(new HandIDTO(hDto, iDto));
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
        return listHiDto;
    }

    public List<HandIDTO> searchHotelByOption1(String location, int inputQuantity, List<BookedRoomDTO> listRemoved) throws NamingException, SQLException {
        List<HandIDTO> listHiDto = new ArrayList<>();
        ImageDAO iDao = new ImageDAO();
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "  SELECT Hotel.emailOfOwner, Hotel.address, Hotel.name, Hotel.description, Hotel.location, Hotel.phone, Hotel.status, Room.roomID , Room.quantity "
                        + "  FROM Hotel "
                        + "  RIGHT JOIN Room ON Hotel.emailOfOwner = Room.emailOfOwner AND Hotel.address = Room.address "
                        + "  WHERE Hotel.status = ? AND Room.status = ? AND Hotel.location LIKE ? ";
//                        + "  GROUP BY Hotel.address, Hotel.emailOfOwner, Hotel.name, Hotel.description, Hotel.location, Hotel.phone, Hotel.status";

                stm = con.prepareStatement(sql);
                stm.setString(1, "available");
                stm.setString(2, "available");
                stm.setString(3, "%" + location + "%");
//                stm.setInt(4, inputQuantity);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String emailOfOwner = rs.getString("emailOfOwner");
                    String address = rs.getString("address");
                    String description = rs.getString("description");
                    String phone = rs.getString("phone");
                    String name = rs.getString("name");
                    location = rs.getString("location");
                    String status = rs.getString("status");
                    String roomID = rs.getString("roomID");
                    int quantity = rs.getInt("quantity");

                    HotelDTO hDto = new HotelDTO(emailOfOwner, address, phone, description, name, location, status);
                    List<ImageDTO> iDto = iDao.loadImageForHotel(emailOfOwner, address);
                    for (BookedRoomDTO remove : listRemoved) {
                        if (roomID.equals(remove.getRoomID())) {
                            if (quantity - remove.getQuantity() >= inputQuantity) {
                                listHiDto.add(new HandIDTO(hDto, iDto));

                            }
                        } else {
                            listHiDto.add(new HandIDTO(hDto, iDto));
                        }
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
        return listHiDto;
    }

}
