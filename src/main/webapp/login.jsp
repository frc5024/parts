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
        <p>Team leads, Mentors, and Developers can log in to access management features</p>

        <form class="form" method="POST">
            <div class="form-group mb-2">
                <label for="email" class="sr-only">Email</label>
                <input type="text" class="form-control" id="email" placeholder="Email">
            </div><br>
            <div class="form-group mb-2">
                <label for="password" class="sr-only">Password</label>
                <input type="password" class="form-control" id="password" placeholder="Password">
            </div><br>
            <button type="submit" class="btn btn-primary mb-2">Authenticate</button>
        </form>
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />
    </body>
</html>

