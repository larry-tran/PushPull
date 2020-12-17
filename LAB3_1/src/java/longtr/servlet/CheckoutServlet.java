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
import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDTO;
import longtr.bookedroom.BookedRoomDAO;
import longtr.bookedroom.BookedRoomDTO;
import longtr.booking.BookingDAO;
import longtr.booking.BookingDTO;
import longtr.cart.CartDTO;
import longtr.prebooking.PreBookingDTO;
import longtr.randi.RandIDAO;
import longtr.randi.RandIDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CheckoutServlet", urlPatterns = {"/CheckoutServlet"})
public class CheckoutServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(CheckoutServlet.class);

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

        Date tempDate = new Date(System.currentTimeMillis());
        Date currentDate = Date.valueOf(tempDate.toString());
        String bookingCode = (String) session.getAttribute("BOOKINGCODE");

        String code = request.getParameter("txtBookingCode");

        String url = "order.jsp";

        try {
            if (code.length() > 0) {
                if (code.equals(bookingCode)) {
                    AccountDTO aDto = (AccountDTO) session.getAttribute("ACCOUNT");
                    CartDTO cart = (CartDTO) session.getAttribute("CART");
                    PreBookingDTO preBooking = (PreBookingDTO) session.getAttribute("PREBOOKING");
                    List<RandIDTO> listRI = (List<RandIDTO>) session.getAttribute("LISTCART");
                    double total = (double) session.getAttribute("NEWTOTAL");

                    BookedRoomDAO brDao = new BookedRoomDAO();
                    BookingDAO bgDao = new BookingDAO();
                    RandIDAO riDao = new RandIDAO();

                    boolean error = false;
                    Timestamp bookingDate = new Timestamp(System.currentTimeMillis());
                    if (cart != null) {
                        Map<String, Integer> mapCart = cart.getRooms();
                        if (mapCart != null) {
                            BookingDTO bDto = new BookingDTO(bookingDate, preBooking.getCheckinDate(), preBooking.getCheckoutDate(), aDto.getEmail(), total, "active");
                            if(currentDate.compareTo(preBooking.getCheckinDate()) == 0){
                                bDto.setStatus("using");
                            }
                            bgDao.addBooking(bDto);
                            for (RandIDTO room : listRI) {
                                BookedRoomDTO brDto = new BookedRoomDTO(bookingDate, room.getRoomDto().getRoomID(), room.getRoomDto().getType(), mapCart.get(room.getRoomDto().getRoomID()), room.getRoomDto().getEmailOfOwner(), room.getSum());
                                if (currentDate.compareTo(preBooking.getCheckinDate())==0) {
                                    boolean check = riDao.subtractRoom(room.getRoomDto().getRoomID(), mapCart.get(room.getRoomDto().getRoomID()));
                                    if (!check) {
                                        LOGGER.error("CheckoutServlet_Can not subtract room");
                                    }
                                }
                                brDao.addBookedRoom(brDto);
                            }

                            if (!error) {
                                url = "LoadBookingServlet";
                                session.removeAttribute("CART");
                                session.removeAttribute("TOTAL");
                                session.removeAttribute("NEWTOTAL");
                                session.removeAttribute("BOOKINGCODE");
                                session.removeAttribute("LISTCART");
                            }
                        }
                    }
                } else {
                    request.setAttribute("ERRBOOKINGCODE", "Wrong code");
                }
            } else {
                request.setAttribute("ERRBOOKINGCODE", "Fill verify code");
            }

        } catch (NamingException e) {
            LOGGER.error("CheckoutServlet_Naming: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("CheckoutServlet_SQL: " + e.getMessage());
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
