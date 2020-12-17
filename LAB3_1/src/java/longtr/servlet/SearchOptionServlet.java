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
import java.util.concurrent.TimeUnit;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.handi.HandIDAO;
import longtr.handi.HandIDTO;
import longtr.prebooking.PreBookingDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "SearchOptionServlet", urlPatterns = {"/SearchOptionServlet"})
public class SearchOptionServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(SearchOptionServlet.class);

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
        String location = request.getParameter("txtLocation");
        String[] txtCheckinDate = request.getParameterValues("checkinDate");
        String[] txtCheckoutDate = request.getParameterValues("checkoutDate");

        String txtQuantity = request.getParameter("txtQuantity");
        HandIDAO hiDao = new HandIDAO();
        String url = "search.jsp";
        try {
            /* TODO output your page here. You may use following sample code. */
            boolean error = false;
            if (txtCheckinDate[0].length() == 0 || txtCheckoutDate[0].length() == 0) {
                error = true;
                request.setAttribute("ERRCHECKOUT", "Fill checkout");
            }

            if (!error) {
                Date checkinDate = Date.valueOf(txtCheckinDate[0]);
                Date checkoutDate = Date.valueOf(txtCheckoutDate[0]);
                if (checkinDate.before(checkoutDate)) {

                    int quantity = Integer.parseInt(txtQuantity);

                    long diffInMillies = Math.abs(checkoutDate.getTime() - checkinDate.getTime());
                    int night = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

                    List<HandIDTO> listHI = hiDao.searchHotelByOption(location, quantity);

                    PreBookingDTO preBookingDto = new PreBookingDTO(checkinDate, checkoutDate, quantity, night);
                    session.setAttribute("SEARCHRESULT", listHI);
                    session.setAttribute("PREBOOKING", preBookingDto);

                    url = "show.jsp";
                } else {
                    request.setAttribute("ERRCHECKOUT", "check out must after check in");
                }
            }
        } catch (NumberFormatException e) {
            LOGGER.error("SearchOptionServlet_Numer: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("SearchOptionServlet_Illegal: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("SearchOptionServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error("SearchOptionServlet_Naming: " + e.getMessage());
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
