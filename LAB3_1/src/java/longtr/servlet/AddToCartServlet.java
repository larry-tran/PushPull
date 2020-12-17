/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDTO;
import longtr.cart.CartDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddToCartServlet", urlPatterns = {"/AddToCartServlet"})
public class AddToCartServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(AddToCartServlet.class);

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
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession(false);
        AccountDTO aDto = (AccountDTO) session.getAttribute("ACCOUNT");

        String url = "invalid.jsp";
        try {
            /* TODO output your page here. You may use following sample code. */

            CartDTO cart = (CartDTO) session.getAttribute("CART");
            if (cart == null) {
                cart = new CartDTO();
            }

            String roomQuantity = request.getParameter("roomQuantity");
            String storeQuantity = request.getParameter("txtStoreQuan");

            String roomID = request.getParameter("txtRoomID");
            int quantity = Integer.parseInt(roomQuantity);
            int storeQuan = Integer.parseInt(storeQuantity);
            Map<String, Integer> mapCart = cart.getRooms();
            if (mapCart != null) {
                if (mapCart.get(roomID) != null) {
                    if (storeQuan >= (quantity + mapCart.get(roomID))) {
                        cart.addRoomToCart(roomID, quantity);
                    }
                } else {
                    cart.addRoomToCart(roomID, quantity);
                }
            } else {
                cart.addRoomToCart(roomID, quantity);
            }

            session.setAttribute("CART", cart);
            session.setAttribute("BACKPAGE", "show.jsp");

            if (aDto != null) {
                if (aDto.getRole().equals("member")) {
                    url = "show.jsp";
                } else {
                    url = "invalid.jsp";
                }
            } else {
                url = "invalid.jsp";
            }

        } catch (NumberFormatException e) {
            LOGGER.error("AddToCartServlet_Number: " + e.getMessage());
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
