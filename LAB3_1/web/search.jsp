<%-- 
    Document   : search
    Created on : Oct 21, 2020, 10:56:29 PM
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

        <title>Search Page</title>
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

        <form action="SearchOptionServlet">
            <div class="container mt-5">
                <div class="card">
                    <h5 class="card-header">View Hotel</h5>
                    <div class="card-body">
                        <input list="location" name="txtLocation" id="locations" class="custom-select" placeholder="City" required>
                        <datalist id="location">
                            <c:forEach var="locate" items="${sessionScope.LISTLOCATION}">
                                <option value="${locate}">
                                </c:forEach>
                        </datalist>
                        <br/>
                        <div class="row mt-4">
                            <div class="col">
                                <input type="date" name="checkinDate" class="form-control" id="checkin" onclick="lockCheckin()" required/>
                                <!--<input type="date" name="checkinDate" class="form-control" id="checkin" onclick="{document.getElementById('checkout').disabled = false;}" required/>-->
                            </div>
                            <div class="col">
                                <input type="date" name="checkoutDate" class="form-control"id="checkout" onclick="lockCheckout()" disabled="true"  required/>
                            </div>
                        </div>
                        <font color="red">${requestScope.ERRCHECKIN}</font>
                        <font color="red">${requestScope.ERRCHECKOUT}</font>
                        <input type="number" min="1" name="txtQuantity" class="form-control mt-4" placeholder="Amount of room" onKeyDown="return false" required />
                        <button class="btn btn-dark mt-4">Find hotel</button>
                    </div>
                </div>
            </div>
        </form>
    </body>
</html>
