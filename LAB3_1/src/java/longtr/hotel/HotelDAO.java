/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.hotel;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class HotelDAO implements Serializable{
    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;
    
    public List<HotelDTO> loadHotel() throws NamingException, SQLException {
        List<HotelDTO> listHotel = new ArrayList<>();
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
                    listHotel.add(hDto);
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
        return listHotel;
    }

}
