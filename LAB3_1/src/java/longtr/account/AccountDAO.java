/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.account;

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
public class AccountDAO implements Serializable {

    Connection con = null;
    PreparedStatement stm = null;
    ResultSet rs = null;

    public AccountDTO checkLogin(String email, String password) throws SQLException, NamingException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT email, password, name, phone,address, role,status,createDate FROM Account WHERE email LIKE ? and password LIKE ? and status LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, password);
                stm.setString(3, "active");
                rs = stm.executeQuery();

                if (rs.next()) {
                    email = rs.getString("email");
                    password = rs.getString("password");
                    String name = rs.getString("name");
                    String status = rs.getString("status");
                    String role = rs.getString("role");
                    String address = rs.getString("address");
                    String phone = rs.getString("phone");
                    Date createDate = rs.getDate("createDate");
                    AccountDTO dto = new AccountDTO(email, password, name, address, phone, role, status, createDate);
                    return dto;
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
        return null;
    }

    public boolean insertNewAccount(AccountDTO dto) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "INSERT INTO Account(email, password, name,address,phone, status, role, createDate) VALUES (?,?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, dto.getEmail());
                stm.setString(2, dto.getPassword());
                stm.setString(3, dto.getName());
                stm.setString(4, dto.getAddress());
                stm.setString(5, dto.getPhone());
                stm.setString(6, dto.getStatus());
                stm.setString(7, dto.getRole());
                stm.setDate(8, dto.getCreateDate());

                int row = stm.executeUpdate();
                if (row > 0) {
                    return true;
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
        return false;
    }

    public boolean checkEmail(String email) throws NamingException, SQLException {
        boolean result = false;
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "SELECT email FROM Account WHERE email = ? AND status LIKE ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, email);
                stm.setString(2, "active");
                rs = stm.executeQuery();
                if (rs.next()) {
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

    public void updatePassword(String email ,String password) throws NamingException, SQLException {
        try {
            con = DBHelper.getConnection();
            if (con != null) {
                String sql = "UPDATE Account SET password = ? WHERE email = ?";
                stm = con.prepareStatement(sql);
                stm.setString(1, password);
                stm.setString(2, email);
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
