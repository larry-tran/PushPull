<%-- 
    Document   : booking
    Created on : Oct 28, 2020, 8:56:20 AM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <link rel="stylesheet" href="style2.css" />
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

        <title>Booking History</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="ShowAllHotelServlet">Welcome ${sessionScope.ACCOUNT.name}</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a href="ViewCartServlet" class="material-icons" style="font-size:36px;color:black;">shopping_cart</a>
                    </li>
                    <li class="nav-item">
                        <a href="ViewCartServlet" class="nav-link"> ${sessionScope.CART.getNumberOfRoom()}</a>
                    </li>

                    <li class="nav-item active">
                        <a class="nav-link" href="search.jsp">Home <span class="sr-only">(current)</span></a>
                    </li>

                    <li class="nav-item">
                        <a class="nav-link" href="ShowAllHotelServlet">View All Hotel</a>
                    </li>

                    <c:if test="${not empty sessionScope.ACCOUNT && sessionScope.ACCOUNT.role eq 'member'}">

                        <li class="nav-item">
                            <a class="nav-link" href="LoadBookingServlet">History Booking</a>
                        </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.ACCOUNT}">
                        <li class="nav-item">
                            <a class="nav-link" href="LogoutServlet">Log out</a>
                        </li>
                    </c:if>
                    <c:if test="${empty sessionScope.ACCOUNT}">
                        <li class="nav-item">
                            <a class="nav-link" href="login.jsp">Log in</a>
                        </li>
                    </c:if>
                </ul>
                <form class="form-inline my-2 my-lg-0" action="SearchBookingServlet">
                    <input class="form-control mr-sm-2" type="search" placeholder="yyyy-mm-dd (hh-mm-ss)" aria-label="Search" name="txtSearch">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                </form>

            </div>
        </nav>


        <c:set var="history" value="${requestScope.LISTHISTORYBOOKING}"/>
        <c:if test="${not empty history}">
            <c:forEach var="booking" items="${history}">
                <form action="DeleteBookingServlet">
                    <div class="container mt-5">
                        <div class="card">
                            <input type="hidden" name="txtBookingID" value="${booking.bookingDto.bookingID}" />
                            <h5 class="card-header">${booking.bookingDto.bookingID}</h5>
                            <div class="card-body">
                                <c:forEach var="bookedRoom" items="${booking.listBookedRoom}">
                                    <input type="hidden" name="txtRoomID" value="${bookedRoom.roomID}" />
                                    <input type="hidden" name="txtQuantity" value="${bookedRoom.quantity}" />

                                    <div class="form-group mt-2">
                                        <h4 class="card-title">${bookedRoom.hotelName} </h4>
                                        <h5 class="card-title">(${bookedRoom.quantity}x)${bookedRoom.roomName} ${bookedRoom.sum} $</h5>
                                    </div>
                                </c:forEach>
                                <div class="form-group mt-5">
                                    <h4 class="card-title">Total: ${booking.bookingDto.total} $</h4>
                                </div>

                                <div class="row">
                                    <div class="col">
                                        <label>Check in</label>
                                    </div>
                                    <div class="col">
                                        <label>Check out</label>
                                    </div>
                                </div>

                                <div class="row">
                                    <div class="col">
                                        <input type="date" name="checkinDate" class="form-control" value="${booking.bookingDto.checkinDate}" disabled="true"/>
                                    </div>
                                    <div class="col">
                                        <input type="date" name="checkoutDate" class="form-control" value="${booking.bookingDto.checkoutDate}" disabled="true"/>
                                    </div>
                                </div>

                                <button class="btn btn-dark mt-4" onclick="return confirm('Are you sure')">Cancel</button>
                            </div>
                        </div>
                    </div>
                </form>
            </c:forEach>
        </c:if>
    <center>
        <c:if test="${empty history}">
            <h1><a href="ShowAllHotelServlet">Back</a></h1>
        </c:if>
    </center>
    </body>
</html>
