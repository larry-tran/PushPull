/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDAO;
import longtr.account.AccountDTO;
import longtr.discount.DiscountDAO;
import longtr.handi.HandIDAO;
import longtr.handi.HandIDTO;
import longtr.history.HistoryBookingDAO;
import longtr.util.DBHelper;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "StartupServlet", urlPatterns = {"/StartupServlet"})
public class StartupServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(StartupServlet.class);
//    private final String SHOW_PAGE = "ShowAllHotelServlet";
    private final String SHOW_PAGE = "search.jsp";
    private final String ADMIN_PAGE = "LoadHotelAdminServlet";

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
        String url = SHOW_PAGE;

        HandIDAO hiDao = new HandIDAO();

        try {
            /* TODO output your page here. You may use following sample code. */
            Cookie[] cookies = request.getCookies();
            String s = null;
            if (cookies != null) {
                for (Cookie cooky : cookies) {
                    if (cooky.getName().equals("ACCOUNT")) {
                        s = cooky.getValue();
                    }
                }
                if (s != null) {
                    String[] arr = s.split("=");
                    String username = arr[0];
                    String password = arr[1];

                    String encodePass = DBHelper.encodePassword(password);
                    AccountDAO dao = new AccountDAO();
                    AccountDTO dto = dao.checkLogin(username, encodePass);
                    if (dto != null) {
                        HttpSession session = request.getSession();
                        session.setAttribute("ACCOUNT", dto);

                        if (dto.getRole().equals("admin")) {
                            url = ADMIN_PAGE;
                        } else if (dto.getRole().equals("member")) {
//                            ServletContext sc = request.getServletContext();
                            List<HandIDTO> listHandI = hiDao.loadHotel();
                            List<String> listLocation = hiDao.loadArea();
                            session.setAttribute("SEARCHRESULT", listHandI);
                            session.setAttribute("LISTLOCATION", listLocation);

//                            CartDTO cart = (CartDTO) sc.getAttribute(dto.getEmail()+ "-CART");
//                            session.setAttribute("CART", cart);
                            url = SHOW_PAGE;
                        }
                    }
                }
            }

            Date currentDate = new Date(System.currentTimeMillis());
            HistoryBookingDAO hbDao = new HistoryBookingDAO();
            DiscountDAO dDao = new DiscountDAO();
            dDao.inactiveCode(currentDate);
            hbDao.subtractRoomAtCurrentDate(currentDate);
            hbDao.setUsingBooking(currentDate);
            hbDao.addRoomAtCurrentDate(currentDate);

        } catch (SQLException ex) {
            LOGGER.error("StartupServlet_SQL: " + ex.getMessage());
        } catch (NamingException ex) {
            LOGGER.error("StartupServlet_Naming: " + ex.getMessage());
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
