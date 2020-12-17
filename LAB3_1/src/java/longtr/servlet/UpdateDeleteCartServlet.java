/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import longtr.account.AccountDTO;
import longtr.cart.CartDTO;

/**
 *
 * @author Admin
 */
@WebServlet(name = "UpdateDeleteCartServlet", urlPatterns = {"/UpdateDeleteCartServlet"})
public class UpdateDeleteCartServlet extends HttpServlet {

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

        ServletContext sc = request.getServletContext();

        HttpSession session = request.getSession(false);

        CartDTO cart = (CartDTO) session.getAttribute("CART");
        AccountDTO aDto = (AccountDTO) session.getAttribute("ACCOUNT");

        String action = request.getParameter("action");
        String url = "ViewCartServlet";

        try {
            if (action.equals("Delete")) {
                if (cart != null) {
                    String roomID = request.getParameter("txtRoomID");
                    cart.removeSelectedRoom(roomID);
                    session.setAttribute("CART", cart);

                    if (cart.getRooms() == null) {
                        session.removeAttribute("LISTCART");
                    }
                }
            } else if (action.equals("Update")) {
                String roomID = request.getParameter("txtRoomID");
                String roomQuantity = request.getParameter("roomQuantity");
                int quantity = Integer.parseInt(roomQuantity);

                cart.updateCart(roomID, quantity);
                session.setAttribute("CART", cart);
                sc.setAttribute(aDto.getEmail() + "-CART", cart);
            }

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
