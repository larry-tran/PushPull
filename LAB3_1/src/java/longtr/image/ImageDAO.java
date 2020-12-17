/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.image;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.naming.NamingException;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class ImageDAO implements Serializable {

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public List<ImageDTO> loadImageForHotel(String emailOfOwner, String address) throws NamingException, SQLException {
        List<ImageDTO> listImage = new ArrayList<>();

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT imageID, imageData, emailOfOwner,address, type FROM Image WHERE status=? AND emailOfOwner=? AND address=? AND type=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "active");
                stm.setString(2, emailOfOwner);
                stm.setString(3, address);
                stm.setString(4, "main");
                rs = stm.executeQuery();
                while (rs.next()) {
                    byte[] image = rs.getBytes("imageData");
                    String base64 = Base64.getEncoder().encodeToString(image);
                    int imageID = rs.getInt("imageID");
                    emailOfOwner = rs.getString("emailOfOwner");
                    address = rs.getString("address");
                    String type = rs.getString("type");
                    listImage.add(new ImageDTO(imageID, base64, emailOfOwner, address, type, "active"));
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

        return listImage;
    }
    
    public List<ImageDTO> loadImageForRoom(String emailOfOwner, String address, String roomID) throws NamingException, SQLException {
        List<ImageDTO> listImage = new ArrayList<>();

        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT imageID, imageData, emailOfOwner,address, type FROM Image WHERE status=? AND emailOfOwner=? AND address=? AND type=?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "active");
                stm.setString(2, emailOfOwner);
                stm.setString(3, address);
                stm.setString(4, roomID);
                rs = stm.executeQuery();
                while (rs.next()) {
                    byte[] image = rs.getBytes("imageData");
                    String base64 = Base64.getEncoder().encodeToString(image);
                    int imageID = rs.getInt("imageID");
                    emailOfOwner = rs.getString("emailOfOwner");
                    address = rs.getString("address");
                    String type = rs.getString("type");
                    listImage.add(new ImageDTO(imageID, base64, emailOfOwner, address, type, "active"));
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

        return listImage;
    }
}
