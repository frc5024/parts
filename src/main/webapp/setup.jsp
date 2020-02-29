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

        <h2>Database Setup</h2>
        <hr>
        <p>The 5024 parts management tool uses a local database stored in your "User" folder. Please configure an admin user account to get started. <strong>If a database is already set up, it will be overwritten.</strong></p>

         <form class="form" method="POST">
            <div class="form-group mb-2">
                <label for="username" class="sr-only">Username</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Username">
            </div><br>
            <div class="form-group mb-2">
                <label for="password" class="sr-only">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            </div><br>
            <button type="submit" class="btn btn-primary mb-2">Create User</button>
        </form>

        
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />
    </body>
</html>

