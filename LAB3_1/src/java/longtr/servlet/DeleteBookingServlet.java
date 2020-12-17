/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longtr.booking.BookingDAO;
import longtr.randi.RandIDAO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "DeleteBookingServlet", urlPatterns = {"/DeleteBookingServlet"})
public class DeleteBookingServlet extends HttpServlet {

        private Logger LOGGER = Logger.getLogger(DeleteBookingServlet.class);

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
        
        String txtBookingID = request.getParameter("txtBookingID");
        String url = "LoadBookingServlet";
        
        try {
            /* TODO output your page here. You may use following sample code. */
            BookingDAO bgDao = new BookingDAO();
            RandIDAO riDao = new RandIDAO();
            
            String[] txtRoomID = request.getParameterValues("txtRoomID");
            String[] txtQuantity = request.getParameterValues("txtQuantity");
            
            Timestamp bookingID = Timestamp.valueOf(txtBookingID);
            boolean check = bgDao.cancelBooking(bookingID);
            if(check){
                for(int i = 0 ; i < txtRoomID.length ; i++){
                    int quantity = Integer.parseInt(txtQuantity[i]);
                    riDao.increaseRoom(txtRoomID[i],quantity);
                }
            }
        }catch (NamingException e) {
            LOGGER.error("DeleteBookingServlet_Naming: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("DeleteBookingServlet_SQL: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("DeleteBookingServlet_Illegal: " + e.getMessage());
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
