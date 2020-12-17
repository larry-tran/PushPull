<%-- 
    Document   : login
    Created on : Sep 28, 2020, 8:09:33 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css">
        <script src="https://www.google.com/recaptcha/api.js"></script>
        <script src="https://code.jquery.com/jquery-3.2.1.slim.min.js" integrity="sha384-KJ3o2DKtIkvYIK3UENzmM7KCkRr/rE9/Qpg6aAZGJwFDMVNA/GpGFF93hXpG5KkN" crossorigin="anonymous"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.12.9/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>

    </head>
    <body style="background: #48D1CC">
        <form action="ResetPasswordServlet">
            <!-- Modal -->
            <div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
                <div class="modal-dialog modal-dialog-centered" role="document">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLongTitle">Enter your email via Gmail</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                                <span aria-hidden="true">&times;</span>
                            </button>
                        </div>
                        <div class="modal-body">
                            <div class="form-group mt-2">
                                <input type="email" name="txtEmail" class="form-control" placeholder="name@example.com" required>
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

        <div class="container mt-5">
            <div class="card" style="margin: auto;width: 60%;">
                <form action="LoginServlet" method="POST">
                    <h5 class="card-header">LOGIN FORM</h5>
                    <div class="card-body">
                        <div class="form-group mt-2">
                            <label for="exampleFormControlInput1">Email address</label>
                            <input type="text" name="txtEmail" class="form-control" value="${param.txtEmail}" required>
                        </div>
                        <font color="red">${requestScope.ERRORACCOUNT.errEmail}</font><br/>

                        <div class="form-group mt-2">
                            <label for="exampleFormControlInput1">Password</label>
                            <input type="password" name="txtPassword" class="form-control" required>
                        </div>
                        <font color="red">${requestScope.ERRORACCOUNT.errPassword}</font><br/>
                        <div class="g-recaptcha"
                             data-sitekey="6Lc4pdwZAAAAAFA0ZsqY1vnJezrDR7ajygcqd7on"></div>
                        <font color="red">${requestScope.ERRORACCOUNT.errConfirm}</font><br/>

                        <button class="btn btn-dark mt-4">Login</button><br/>
                        Not a member? <a href="signup.jsp" class="btn btn-link">Signup now</a><br/>
                        <a href="ShowAllHotelServlet" class="btn btn-link">Back to store</a><br/>
                        <button type="button" class="btn btn-link" data-toggle="modal" data-target="#exampleModalCenter">Forgot password ?</button> ${requestScope.NOEXIST}

                    </div>
                </form>
            </div>
        </div>

    </body>
</html>
