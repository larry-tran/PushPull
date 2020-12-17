/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import longtr.account.AccountDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDTO;
import longtr.account.AccountErrorDTO;
import longtr.recaptcha.VerifyRecaptcha;
import longtr.util.DBHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    final static Logger LOGGER = Logger.getLogger(LoginServlet.class);
    final private static String ERROR_PAGE = "login.jsp";
    final private static String SEARCH_PAGE = "search.jsp";
    final private static String ADMIN_PAGE = "LoadHotelAdminServlet";

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
        HttpSession session = request.getSession();

        String email = request.getParameter("txtEmail");
        String password = request.getParameter("txtPassword");
        String gRecaptchaResponse = request.getParameter("g-recaptcha-response");
        boolean verify = VerifyRecaptcha.verify(gRecaptchaResponse);
        String encodePassword = DBHelper.encodePassword(password);

        String backPage = (String) session.getAttribute("BACKPAGE");

        AccountErrorDTO errDto = new AccountErrorDTO();

        String url = ERROR_PAGE;

        try {
            /* TODO output your page here. You may use following sample code. */
            boolean error = false;
            if (email.length() == 0) {
                error = true;
                errDto.setErrEmail("Fill your email");
            }
            if (password.length() == 0) {
                error = true;
                errDto.setErrPassword("Fill your password");
            }
            if (!verify) {
                error = true;
                errDto.setErrConfirm("You miss reCaptcha");
            }

            if (!error) {
                AccountDAO dao = new AccountDAO();
                AccountDTO dto = dao.checkLogin(email, encodePassword);
                if (dto != null) {

                    Cookie cookie = new Cookie("ACCOUNT", email + "=" + password);
                    response.addCookie(cookie);
                    session.setAttribute("ACCOUNT", dto);
                    if (dto.getRole().equals("admin")) {
                        url = ADMIN_PAGE;
                    } else if (dto.getRole().equals("member")) {
                        if (backPage != null) {
                            url = backPage;
                        } else {
                            url = SEARCH_PAGE;
                        }
                    }
                } else {
                    errDto.setErrPassword("Incorrect email and password");
                }
            }
            request.setAttribute("ERRORACCOUNT", errDto);
        } catch (NamingException | SQLException e) {
            LOGGER.error(e.getMessage());
        } finally {
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
