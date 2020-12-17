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
import java.util.Map;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.cart.CartDTO;
import longtr.discount.DiscountDAO;
import longtr.discount.DiscountDTO;
import longtr.prebooking.PreBookingDTO;
import longtr.randi.RandIDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "CheckInfoServlet", urlPatterns = {"/CheckInfoServlet"})
public class CheckInfoServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(CheckInfoServlet.class);

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
        Date expDate = new Date(System.currentTimeMillis());
        HttpSession session = request.getSession(false);

        List<RandIDTO> listRI = (List< RandIDTO>) session.getAttribute("LISTCART");

        DiscountDAO dDao = new DiscountDAO();
        String url = "cart.jsp";
        try {
            boolean error = true;
            DiscountDTO disDto = null;
            String code = request.getParameter("txtDiscountCode");
            if (code.length() == 0) {
                error = false;
            } else if (code.length() > 0) {
                if (listRI != null) {
                    for (RandIDTO room : listRI) {
                        disDto = dDao.getCode(room.getRoomDto().getAddress(), code.trim(), expDate);
                        if (disDto == null) {
                            error = true;
                            request.setAttribute("ERRCODE", "Invalid code");
                        } else {
                            error = false;
                            break;
                        }
                    }
                }
            }
            if (!error) {
                PreBookingDTO preDto = (PreBookingDTO) session.getAttribute("PREBOOKING");
                double total = 0;
                int night = preDto.getNight();
                CartDTO cart = (CartDTO) session.getAttribute("CART");
                if (cart != null) {
                    Map<String, Integer> mapCart = cart.getRooms();
                    if (mapCart != null) {
                        if (disDto != null) {
                            if (listRI != null) {
                                for (RandIDTO room : listRI) {
                                    if (room.getRoomDto().getAddress().equals(disDto.getAddress())) {
                                        double discount = (100 - disDto.getDetail()) * 0.01;
                                        room.setSum(mapCart.get(room.getRoomDto().getRoomID()) * room.getRoomDto().getPrice() * night * discount);
                                    }
                                    total += room.getSum();
                                }
                            }
                        } else {
                            if (listRI != null) {
                                for (RandIDTO room : listRI) {
                                    room.setSum(mapCart.get(room.getRoomDto().getRoomID()) * room.getRoomDto().getPrice() * night);
                                    total += room.getSum();
                                }
                            }
                        }
                        session.setAttribute("NEWTOTAL", total);
                        session.setAttribute("NEWLISTCART", listRI);
//                        session.removeAttribute("LISTCART");
                        url = "order.jsp";
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.error("CheckInfoServlet_SQL: " + e.getMessage());
        } catch (NamingException e) {
            LOGGER.error("CheckInfoServlet_Naming: " + e.getMessage());
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
