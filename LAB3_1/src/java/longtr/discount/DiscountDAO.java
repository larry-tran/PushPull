/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.discount;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.naming.NamingException;
import longtr.util.DBHelper;

/**
 *
 * @author Admin
 */
public class DiscountDAO implements Serializable {

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public DiscountDTO getCode(String address, String code, Date expDate) throws NamingException, SQLException {
        DiscountDTO dto = null;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT discountCode, detail, expDate, status, email,address FROM Discount WHERE address LIKE ? AND status LIKE ? AND discountCode LIKE ? AND expDate >= ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, address);
                stm.setString(2, "active");
                stm.setString(3, code);
                stm.setDate(4, expDate);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String discountCode = rs.getString("discountCode");
                    int detail = rs.getInt("detail");
                    expDate = rs.getDate("expDate");
                    String status = rs.getString("status");
                    String email = rs.getString("email");
                    address = rs.getString("address");

                    dto = new DiscountDTO(discountCode, detail, expDate, status, email, address);
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
        return dto;
    }

    public boolean checkExistCode(String code) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT discountCode FROM Discount WHERE status LIKE ? AND discountCode LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "active");
                stm.setString(2, code);
                rs = stm.executeQuery();
                if (rs.next()) {
                    result = true;
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
        return result;
    }

    public void addCode(DiscountDTO dto) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO Discount(discountCode, detail, expDate, status, email, address) VALUES(?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getDiscountCode());
                stm.setInt(2, dto.getDetail());
                stm.setDate(3, dto.getExpDate());
                stm.setString(4, dto.getStatus());
                stm.setString(5, dto.getEmail());
                stm.setString(6, dto.getAddress());
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
    
    public void inactiveCode(Date currentDate) throws NamingException, SQLException{
        try {
            con = DBHelper.getConnection();
            if(con!=null){
                String sql = "UPDATE Discount SET status = ? WHERE expDate = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, "inactive");
                stm.setDate(2,currentDate);
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
