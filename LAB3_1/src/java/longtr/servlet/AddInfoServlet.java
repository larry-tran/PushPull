/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.concurrent.TimeUnit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.prebooking.PreBookingDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddInfoServlet", urlPatterns = {"/AddInfoServlet"})
public class AddInfoServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(AddInfoServlet.class);

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

        String url = "show.jsp";
        try {
            /* TODO output your page here. You may use following sample code. */
            boolean error = false;

            String[] checkinDate = request.getParameterValues("checkinDate");
            String[] checkoutDate = request.getParameterValues("checkoutDate");
            if (checkinDate[0].length() == 0 || checkoutDate[0].length() == 0) {
                error = true;
            }

            if (!error) {
                Date checkin = Date.valueOf(checkinDate[0]);
                Date checkout = Date.valueOf(checkoutDate[0]);
                if (checkin.before(checkout)) {
                    int quantity = 1;
                    long diffInMillies = Math.abs(checkout.getTime() - checkin.getTime());
                    int night = (int) TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
                    PreBookingDTO preDto = new PreBookingDTO(checkin, checkout, quantity, night);
                    session.setAttribute("PREBOOKING", preDto);
                }
            }

        } catch (NumberFormatException e) {
            LOGGER.error("AddInfoServlet_Number: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("AddInfoServlet_Illegal: " + e.getMessage());
        } finally {
            response.sendRedirect(url);
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
