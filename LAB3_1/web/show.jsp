<%-- 
    Document   : show
    Created on : Oct 22, 2020, 8:29:45 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
        <link rel="stylesheet" href="style2.css" />
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
        <script>
            function lockCheckout() {
                var checkin = new Date(document.getElementById('checkin').value);
                var nextDay = new Date(checkin);
                nextDay.setDate(checkin.getDate() + 1);

                var y = nextDay.getFullYear();
                var m = nextDay.getMonth() + 1;
                var d = nextDay.getDate();
                if (d.toString().length === 1) {
                    d = "0" + nextDay.getDate();
                }
                var full = y + "-" + m + "-" + d;
                document.getElementById('checkout').setAttribute("min", full);
                document.getElementById('checkout').setAttribute("value", full);
            }

            function lockCheckin() {
                document.getElementById('checkout').disabled = false;
                var t = new Date();
                var m = t.getMonth() + 1;
                var y = t.getFullYear();
                var d = t.getDate();
                if (d.toString().length === 1) {
                    d = "0" + t.getDate();
                }
                var full = y + "-" + m + "-" + d;
                document.getElementById('checkin').setAttribute("min", full);
                document.getElementById('checkin').setAttribute("value", full);
            }
        </script>

        <title>Show Page</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="search.jsp">Welcome ${sessionScope.ACCOUNT.name}</a>
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

        <div class="container mt-5">
            <div class="card-deck mt-5">
                <c:set var="hotel" value="${sessionScope.SEARCHRESULT}"/>
                <c:if test="${not empty hotel}">
                    <c:forEach var="dto" items="${hotel}">
                        <c:set var="emailOfOwner" value="${dto.hotelDto.emailOfOwner}"/>
                        <c:set var="address" value="${dto.hotelDto.address}"/>
                        <div class="col-sm-6">
                            <div class="card">
                                <c:set var="image" value="${dto.listImage}"/>
                                <c:if test="${not empty image}">
                                    <img class="card-img-top" src="data:image/jpg;base64,${image.get(0).imageData}"/>
                                </c:if>
                                <div class="card-body">
                                    <h5 class="card-title">${dto.hotelDto.name}</h5>
                                    <p class="card-text">
                                        ${dto.hotelDto.description}
                                    </p>
                                    <p class="card-text">
                                        <small class="text-muted">Address: ${dto.hotelDto.address}</small>
                                    </p>
                                    <c:if test="${not empty sessionScope.PREBOOKING}">
                                        <a href="ShowRoomsServlet?emailOfOwner=${emailOfOwner}&address=${address}" class="btn btn-dark">View Hotel</a>
                                    </c:if>
                                    <c:if test="${empty sessionScope.PREBOOKING}">
                                        <button type="button" class="btn btn-dark" data-toggle="modal" data-target="#exampleModalCenter">View hotel</button>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
            </div>
        </div>

        <form action="AddInfoServlet">
            <!-- Modal -->
            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">Fill check in date and click add again</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <div class="col">
                                    <input type="date" name="checkinDate" class="form-control" id="checkin" onclick="lockCheckin()" required/>
                                </div>

                                <div class="col">
                                    <input type="date" name="checkoutDate" class="form-control" id="checkout" onclick="lockCheckout()" required disabled="true"/>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="submit" class="btn btn-primary">Continue</button>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
