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
        <p>The 5024 parts management tool uses Google APIs to read inventory data. 
        Please sign in with an account that can access the inventory sheet. Your account 
        can not be accessed by other users, and is only used for backend database setup.</p>

        <br>

        <a href=""><img src="static/img/google-auth.png"></a>

        
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />
    </body>
</html>

