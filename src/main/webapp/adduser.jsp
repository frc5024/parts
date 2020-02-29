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

        <h2>Add a User</h2>
        <hr>
        <p>Here, you can add users. Users with admin permissions can edit the inventory, and modify other users' accounts.</p>

         <form class="form" method="POST">
            <div class="form-group mb-2">
                <label for="username" class="sr-only">Username</label>
                <input type="text" class="form-control" id="username" name="username" placeholder="Username">
            </div><br>
            <div class="form-group mb-2">
                <label for="password" class="sr-only">Password</label>
                <input type="password" class="form-control" id="password" name="password" placeholder="Password">
            </div><br>
            <div class="form-group form-check">
                <input type="checkbox" class="form-check-input" id="admin" name="admin">
                <label class="form-check-label" for="admin">Admin</label>
            </div><br>
            <button type="submit" class="btn btn-primary mb-2">Create User</button>
        </form>

        
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />
    </body>
</html>

