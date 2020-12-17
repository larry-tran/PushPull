/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longtr.account.AccountDAO;
import longtr.util.DBHelper;
import longtr.util.EmailUtil;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "ResetPasswordServlet", urlPatterns = {"/ResetPasswordServlet"})
public class ResetPasswordServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(ResetPasswordServlet.class);
    private String host = "smtp.gmail.com";
    private String port = "587";
    private String user = "longtran4949@gmail.com";
    private String pass = "larrytran49";

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        String email = request.getParameter("txtEmail");
        String url = "login.jsp";
        AccountDAO aDao = new AccountDAO();
        String errEmail = null;
        try {
            /* TODO output your page here. You may use following sample code. */
            boolean check = aDao.checkEmail(email);

            if (check) {
                String subject = "Reset Password";

                String password = EmailUtil.sendCode();
                String encode = DBHelper.encodePassword(password);

                EmailUtil.sendEmail(host, port, user, pass, email, subject, password);
                aDao.updatePassword(email, encode);

                errEmail = "Success";
            } else {
                errEmail = "Email is incorrect";
            }
        } catch (AddressException e) {
            LOGGER.error("ResetPasswordServlet_Address: " + e.getMessage());
            errEmail = "Illegal address";
        } catch (MessagingException e) {
            LOGGER.error("ResetPasswordServlet_Message: " + e.getMessage());
            errEmail = "Sending fail";
        } catch (NamingException e) {
            LOGGER.error("ResetPasswordServlet_Naming: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("ResetPasswordServlet_SQL: " + e.getMessage());
        } finally {
            request.setAttribute("NOEXIST", errEmail);
            RequestDispatcher rd = request.getRequestDispatcher(url);
            rd.forward(request, response);
            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
