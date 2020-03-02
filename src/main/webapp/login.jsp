<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="layouts/head.jsp" />
        <link rel="stylesheet" href="static/css/login.css">

    </head>
    <body>
        <jsp:include page="layouts/nav.jsp" />       
        
        <div class="container">
        <div style="height:20vh;"></div>
        <div class="login-container">

        <h2>Login</h2>
        <hr>
        <div class="alert alert-danger container" role="alert" id="loginfail" hidden > 
            Login failed: Invalid credentials

            <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
            </button>
        </div>

        <p>Team leads, Mentors, and Developers can log in to access management features</p>

        <form class="form" method="POST">
            <div class="form-group mb-2">
                <label for="username" class="sr-only">Username</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Username">
            </div><br>
            <div class="form-group mb-2">
                <label for="password" class="sr-only">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            </div><br>
            <button type="submit" class="btn btn-primary mb-2">Authenticate</button>
        </form>
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />

        <%-- Handle Alert for login --%>
        <script src="static/js/options.js"></script>
        <script src="static/js/login.js"></script>
    </body>
</html>

