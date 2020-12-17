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
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import longtr.discount.DiscountDAO;
import longtr.discount.DiscountDTO;
import org.apache.log4j.Logger;

/**
 *
 * @author Admin
 */
@WebServlet(name = "AddDiscountServlet", urlPatterns = {"/AddDiscountServlet"})
public class AddDiscountServlet extends HttpServlet {

    private Logger LOGGER = Logger.getLogger(AddDiscountServlet.class);

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
        
        String txtCode = request.getParameter("txtCode");

        DiscountDAO dDao = new DiscountDAO();
        String url = "admin.jsp";
        try {
            /* TODO output your page here. You may use following sample code. */
            boolean check = dDao.checkExistCode(txtCode);
            if (!check) {
                String txtPercent = request.getParameter("txtPercent");
                int percent = Integer.parseInt(txtPercent);
                String hotelID = request.getParameter("txtHotelID");
                String[] id = hotelID.split("-");
                String email = id[0];
                String address = id[1];
                String[] expDate = request.getParameterValues("txtExpDate");
                Date exp = Date.valueOf(expDate[0]);
                DiscountDTO dto = new DiscountDTO(txtCode, percent, exp, "active", email, address);
                dDao.addCode(dto);
                request.setAttribute("ERRDIS", "Successfully");
            } else {
                request.setAttribute("ERRDIS", "Duplicate!");
            }
        } catch (NamingException e) {
            LOGGER.error("AddDiscountServlet_Naming: " + e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("AddDiscountServlet_SQL: " + e.getMessage());
        } catch (NumberFormatException e) {
            LOGGER.error("AddDiscountServlet_Number: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            LOGGER.error("AddDiscountServlet_Ill: " + e.getMessage());
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
