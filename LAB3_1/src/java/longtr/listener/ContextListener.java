/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package longtr.listener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author Admin
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        Map<String, List<String>> authMap = loadUrl();

        context.setAttribute("AUTHMAP", authMap);

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.removeAttribute("AUTHMAP");
    }

    public Map<String, List<String>> loadUrl() {
        Map<String, List<String>> authMap = new HashMap<>();

        List<String> urlCustomer = new ArrayList<>();
        List<String> urlAdmin = new ArrayList<>();
        List<String> urlGuest = new ArrayList<>();

        urlAdmin.add("LogoutServlet");
        urlAdmin.add("AddDiscountServlet");
        urlAdmin.add("LoadHotelAdminServlet");
        urlAdmin.add("admin.jsp");
        urlAdmin.add("AddInfoServlet");
        urlAdmin.add("SignupServlet");
        urlAdmin.add("ResetPasswordServlet");
        urlAdmin.add("SearchOptionServlet");
        urlAdmin.add("SearchServlet");
        urlAdmin.add("VerifyServlet");
        urlAdmin.add("ShowRoomsServlet");
        urlAdmin.add("ShowAllHotelServlet");
        urlAdmin.add("StartupServlet");
        urlAdmin.add("rooms.jsp");
        urlAdmin.add("search.jsp");
        urlAdmin.add("show.jsp");
        urlAdmin.add("invalid.jsp");

        urlCustomer.add("AddInfoServlet");
        urlCustomer.add("BookingVerifyServlet");
        urlCustomer.add("CheckInfoServlet");
        urlCustomer.add("CheckoutServlet");
        urlCustomer.add("DeleteBookingServlet");
        urlCustomer.add("LoadBookingServlet");
//        urlCustomer.add("LoginServlet");
        urlCustomer.add("ResetPasswordServlet");
        urlCustomer.add("SearchBookingServlet");
        urlCustomer.add("SearchOptionServlet");
        urlCustomer.add("SearchServlet");
        urlCustomer.add("UpdateDeleteCartServlet");
        urlCustomer.add("VerifyBookingServlet");
        urlCustomer.add("ViewCartServlet");
        urlCustomer.add("VerifyServlet");
        urlCustomer.add("ShowRoomsServlet");
        urlCustomer.add("ShowAllHotelServlet");
        urlCustomer.add("AddToCartServlet");
        urlCustomer.add("StartupServlet");
        urlCustomer.add("LogoutServlet");
        urlCustomer.add("rooms.jsp");
        urlCustomer.add("search.jsp");
        urlCustomer.add("show.jsp");
//        urlCustomer.add("signup.jsp");
        urlCustomer.add("order.jsp");
//        urlCustomer.add("login.jsp");
        urlCustomer.add("cart.jsp");
        urlCustomer.add("booking.jsp");
        urlCustomer.add("invalid.jsp");
        urlCustomer.add("error.html");

        urlGuest.add("AddInfoServlet");
        urlGuest.add("AddToCartServlet");
        urlGuest.add("SignupServlet");
        urlGuest.add("ResetPasswordServlet");
        urlGuest.add("SearchOptionServlet");
        urlGuest.add("SearchServlet");
        urlGuest.add("VerifyServlet");
        urlGuest.add("ShowRoomsServlet");
        urlGuest.add("ShowAllHotelServlet");
        urlGuest.add("StartupServlet");
        urlGuest.add("rooms.jsp");
        urlGuest.add("search.jsp");
        urlGuest.add("show.jsp");
        urlGuest.add("invalid.jsp");

        authMap.put("admin", urlAdmin);
        authMap.put("member", urlCustomer);
        authMap.put("guest", urlGuest);

        return authMap;
    }
}
