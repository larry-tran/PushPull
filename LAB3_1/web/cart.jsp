<%-- 
    Document   : cart
    Created on : Oct 24, 2020, 9:59:28 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        <title>Cart Page</title>
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
        <c:set var="cart" value="${sessionScope.CART}"/>

        <c:set var="roomss" value="${requestScope.OUTLISTCART}"/>
        <c:if test="${not empty roomss}">
            <div class="container mt-5">
                <c:forEach var="dto" items="${roomss}">
                    <form action="UpdateDeleteCartServlet">
                        <div class="card mt-5">
                            <h4 class="card-title">${dto.roomDto.emailOfOwner} - ${dto.roomDto.address}</h4>
                            <div class="row no-gutters">
                                <input type="hidden" name="txtRoomID" value="${dto.roomDto.roomID}" />
                                <c:set var="image" value="${dto.listImage}"/>
                                <c:if test="${not empty image}">
                                    <!--<div class="col-auto">-->
                                    <img class="img-fluid" src="data:image/jpg;base64,${image.get(0).imageData}" style="filter:grayscale(100%)"/>
                                    <!--</div>-->
                                </c:if>
                                <div class="col">
                                    <div class="card-block px-2">                                      
                                        <h4 class="card-title">Room ${dto.roomDto.type}</h4>
                                        <p class="card-text">
                                            <small class="text-muted">Description: ${dto.roomDto.description}</small>
                                        </p>
                                        Price
                                        <p class="card-text" style="color:red;">
                                            ${dto.roomDto.price}/room/night
                                        </p>
                                        Quantity
                                        <input type="number" min="1" name="roomQuantity" class="form-control" value="${cart.getRooms().get(dto.roomDto.roomID)}" max="${dto.roomDto.quantity}" onKeyDown="return false">

                                        <p class="card-text">
                                            Check in date: ${sessionScope.PREBOOKING.checkinDate}
                                        </p>
                                        <p class="card-text">
                                            Check out date: ${sessionScope.PREBOOKING.checkoutDate}
                                        </p>
                                        <p class="card-text">
                                            Night: ${sessionScope.PREBOOKING.night}
                                        </p>
                                        <p class="card-text">
                                            Sum: ${dto.sum} $
                                        </p>

                                        <p class="card-text">
                                            Status: ${dto.roomDto.status}
                                        </p>

                                        <button type="submit" class="btn btn-dark mt-5" name="action" value="Delete" onclick="return confirm('Are you sure');">Delete</button>
                                        <button type="submit" class="btn btn-dark mt-5" name="action" value="Update">Update</button>

                                    </div>
                                </div>
                            </div>

                        </div>
                    </form>
                </c:forEach>
            </div>
        </c:if>

        <c:set var="rooms" value="${sessionScope.LISTCART}"/>
        <c:if test="${not empty rooms}">
            <div class="container mt-5">
                <c:forEach var="dto" items="${rooms}">
                    <form action="UpdateDeleteCartServlet">
                        <div class="card mt-5">
                            <h4 class="card-title">${dto.roomDto.emailOfOwner} - ${dto.roomDto.address}</h4>
                            <div class="row no-gutters">
                                <input type="hidden" name="txtRoomID" value="${dto.roomDto.roomID}" />
                                <c:set var="image" value="${dto.listImage}"/>
                                <c:if test="${not empty image}">
                                    <img class="img-fluid" src="data:image/jpg;base64,${image.get(0).imageData}"/>
                                </c:if>
                                <div class="col">
                                    <div class="card-block px-2">                                      
                                        <h4 class="card-title">Room ${dto.roomDto.type}</h4>
                                        <p class="card-text">
                                            <small class="text-muted">Description: ${dto.roomDto.description}</small>
                                        </p>
                                        Price
                                        <p class="card-text" style="color:red;">
                                            ${dto.roomDto.price}/room/night
                                        </p>
                                        Quantity
                                        <input type="number" min="1" name="roomQuantity" class="form-control" value="${cart.getRooms().get(dto.roomDto.roomID)}" max="${dto.roomDto.quantity}" onKeyDown="return false">

                                        <p class="card-text">
                                            Check in date: ${sessionScope.PREBOOKING.checkinDate}
                                        </p>
                                        <p class="card-text">
                                            Check out date: ${sessionScope.PREBOOKING.checkoutDate}
                                        </p>
                                        <p class="card-text">
                                            Night: ${sessionScope.PREBOOKING.night}
                                        </p>
                                        <p class="card-text">
                                            Sum: ${dto.sum} $
                                        </p>

                                        <p class="card-text">
                                            Status: ${dto.roomDto.status}
                                        </p>

                                        <button type="submit" class="btn btn-dark mt-5" name="action" value="Delete" onclick="return confirm('Are you sure');">Delete</button>
                                        <button type="submit" class="btn btn-dark mt-5" name="action" value="Update">Update</button>

                                    </div>
                                </div>
                            </div>

                        </div>
                    </form>
                </c:forEach>
            </div>

            <form action="CheckInfoServlet">
                <div class="container mt-5">
                    <div class="card">
                        <h5 class="card-header">Check out information</h5>
                        <div class="card-body">
                            <c:set var="rooms" value="${sessionScope.LISTCART}"/>
                            <c:if test="${not empty rooms}">
                                <c:forEach var="dto" items="${rooms}">
                                    <div class="form-group mt-2">
                                        <h4 class="card-title">${dto.roomDto.emailOfOwner} </h4>
                                        <h5 class="card-title">(${cart.getRooms().get(dto.roomDto.roomID)}x)${dto.roomDto.type} (${sessionScope.PREBOOKING.night} night) ${dto.sum} $</h5>
                                    </div>
                                </c:forEach>
                                <div class="form-group mt-5">
                                    <h4 class="card-title">Total: ${sessionScope.TOTAL} $</h4>
                                </div>

                            </c:if>

                            <div class="form-group mt-2">
                                <label for="exampleFormControlInput1">Discount Code</label>
                                <input type="text" name="txtDiscountCode" class="form-control" placeholder="${requestScope.ERRCODE}">
                                <!--                                <button class="btn btn-dark mt-4" name="action" value="Apply">Apply</button>-->
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
                                    <input type="date" name="checkinDate" class="form-control" value="${sessionScope.PREBOOKING.checkinDate}" disabled="true"/>
                                </div>
                                <div class="col">
                                    <input type="date" name="checkoutDate" class="form-control" value="${sessionScope.PREBOOKING.checkoutDate}" disabled="true"/>
                                </div>
                            </div>
                            <div class="form-group mt-2">
                                <label for="exampleFormControlInput1">Email address / Name</label>
                                <input type="email" name="txtEmail" class="form-control" placeholder="name@example.com" value="${sessionScope.ACCOUNT.email}" required disabled>
                            </div>
                            <div class="form-group mt-2">
                                <label for="exampleFormControlInput1">Phone Number</label>
                                <input type="tel" class="form-control" name="phone" value="${sessionScope.ACCOUNT.phone}" placeholder="0123456789" pattern="[0-9]{10}" required disabled>
                            </div>

                            <button class="btn btn-dark mt-4" name="action" value="Check">Check</button>
                        </div>
                    </div>
                </div>
            </form>
        </c:if>
    <center>
        <c:if test="${empty rooms && empty roomss}">
            <h1>Cart is empty</h1>
        </c:if>
    </center>
</body>
</html>
