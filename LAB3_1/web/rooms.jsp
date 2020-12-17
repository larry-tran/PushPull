<%-- 
    Document   : rooms
    Created on : Oct 24, 2020, 12:07:35 AM
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

        <title>Show Rooms Page</title>
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
                <form class="form-inline my-2 my-lg-0" action="SearchServlet">
                    <input class="form-control mr-sm-2" type="search" placeholder="Search Hotel" aria-label="Search" name="txtSearch">
                    <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
                </form>

            </div>
        </nav>
        <c:set var="rooms" value="${sessionScope.LISTROOM}"/>
        <c:if test="${not empty rooms}">
            <div class="container mt-5">
                <div class="card-deck" style="margin: auto;width: 60%;">
                    <c:forEach var="dto" items="${rooms}">
                        <form action="AddToCartServlet">
                            <input type="hidden" name="txtRoomID" value="${dto.roomDto.roomID}" />
                            <input type="hidden" name="txtStoreQuan" value="${dto.roomDto.quantity}" />
                            <div class="card mb-5">
                                <c:set var="image" value="${dto.listImage}"/>
                                <c:if test="${not empty image}">
                                    <img class="card-img-top" src="data:image/jpg;base64,${image.get(0).imageData}"/>
                                </c:if>
                                <div class="card-body">
                                    <h5 class="card-title">${dto.roomDto.type}</h5>
                                    Price
                                    <p class="card-text" style="color:red;">
                                        ${dto.roomDto.price}/room/night
                                    </p>
                                    Quantity
                                    <c:if test="${not empty sessionScope.PREBOOKING}">
                                        <input type="number" min="1" name="roomQuantity" class="form-control" value="${sessionScope.PREBOOKING.quantity}" max="${dto.roomDto.quantity}" onKeyDown="return false">
                                    </c:if>
                                    <c:if test="${empty sessionScope.PREBOOKING}">
                                        <input type="number" min="1" name="roomQuantity" class="form-control" value="1" max="${dto.roomDto.quantity}" onKeyDown="return false">
                                    </c:if>
                                    <p class="card-text">
                                        <small class="text-muted">Description: ${dto.roomDto.description}</small>
                                    </p>
                                    <button type="submit" class="btn btn-dark">Add to cart</button>
                                </div>
                            </div>
                        </form>
                    </c:forEach>
                </div>
            </div>
        </c:if>




    </body>
</html>
