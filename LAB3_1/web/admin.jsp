<%-- 
    Document   : admin
    Created on : Oct 29, 2020, 10:24:54 PM
    Author     : Admin
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
        <script>
            function lockExp() {
                var exp = new Date();
                var nextDay = new Date(exp);
                nextDay.setDate(exp.getDate() + 1);
                var y = nextDay.getFullYear();
                var m = nextDay.getMonth() + 1;
                var d = nextDay.getDate();
                if (d.toString().length === 1) {
                    d = "0" + nextDay.getDate();
                }
                var full = y + "-" + m + "-" + d;
                document.getElementById('expDate').setAttribute("min", full);
                document.getElementById('expDate').setAttribute("value", full);
            }
        </script>
        <title>Create Discount Code</title>
    </head>
    <body>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <a class="navbar-brand" href="admin.jsp">Welcome ${sessionScope.ACCOUNT.name}</a>
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item active">
                        <a class="nav-link" href="admin.jsp">Home</a>
                    </li>        
                    <li class="nav-item">
                        <a class="nav-link" href="ShowAllHotelServlet">Store</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="search.jsp">Search</a>
                    </li>
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
            </div>
        </nav>
        <div class="container mt-5">
            <div class="card" style="margin: auto;width: 60%;">
                <form action="AddDiscountServlet">
                    <h5 class="card-header">CREATE NEW DISCOUNT CODE</h5>
                    <div class="card-body">
                        <div class="form-group mt-2">
                            <label for="exampleFormControlInput1">Code</label>
                            <input type="text" name="txtCode" class="form-control" value="${param.txtCode}" required>
                        </div>

                        <div class="form-group mt-2">
                            <label for="exampleFormControlInput1">Percent</label>
                            <input type="number" min="15" max="100" step="5" value="15" name="txtPercent" class="form-control" max="${dto.roomDto.quantity}" onKeyDown="return false">
                        </div>

                        <div class="row mt-2">
                            <div class="col">
                                <label>Exp Date:</label>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col">
                                <input type="date" id="expDate" class="form-control" name="txtExpDate" onclick="lockExp()" required/>
                            </div>
                        </div>
                        <div class="form-group mt-2">
                            <label for="sel1">Select list:</label>
                            <select class="form-control" id="sel1" name="txtHotelID">
                                <c:forEach var="dto" items="${sessionScope.LISTHOTEL}">
                                    <option value="${dto.emailOfOwner}-${dto.address}">${dto.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <font color="red">${requestScope.ERRDIS}</font>

                        <button class="btn btn-dark mt-4">Add</button>
                    </div>
                </form>
            </div>
        </div>
    </body>
</html>
