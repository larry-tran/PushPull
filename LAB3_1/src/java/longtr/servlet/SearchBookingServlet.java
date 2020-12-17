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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDTO;
import longtr.history.HistoryBookingDAO;
import longtr.history.HistoryBookingDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SearchBookingServlet", urlPatterns = {"/SearchBookingServlet"})
public class SearchBookingServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(SearchBookingServlet.class);

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

        HttpSession session = request.getSession(false);
        AccountDTO aDto = (AccountDTO) session.getAttribute("ACCOUNT");
        String txtSearch = request.getParameter("txtSearch");

        HistoryBookingDAO hbDao = new HistoryBookingDAO();

        List<HistoryBookingDTO> listBookingSearch = null;
        String url = "booking.jsp";

        try {
            /* TODO output your page here. You may use following sample code. */
            if (txtSearch.length() > 0) {
                if (txtSearch.length() == 10) {
                    Date date1 = Date.valueOf(txtSearch);
                    Date date2 = Date.valueOf(txtSearch);
                    date2.setDate(date1.getDate() + 1);

                    Timestamp time1 = new Timestamp(date1.getTime());
                    Timestamp time2 = new Timestamp(date2.getTime());

                    listBookingSearch = hbDao.searchByDate(time1, time2, aDto.getEmail());

                } else if (txtSearch.length() >= 19 && txtSearch.length() <= 23) {
                    Timestamp bookingID = Timestamp.valueOf(txtSearch);

                    HistoryBookingDTO hbDto = hbDao.searchByBookingID(bookingID, aDto.getEmail());
                    if (hbDto != null) {
                        listBookingSearch = new ArrayList<>();
                        listBookingSearch.add(hbDto);
                    }
                }

                request.setAttribute("LISTHISTORYBOOKING", listBookingSearch);
            }
        } catch (NamingException e) {
            LOGGER.error("SearchBookingServlet_Naming: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("SearchBookingServlet_SQL: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("SearchBookingServlet_Illegal: " + e.getMessage());
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
