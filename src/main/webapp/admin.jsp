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

        <h2>Admin Panel</h2>
        <hr>
        <p>Some basic actions can be performed from the admin panel. Advanced operations can be executed from the SQL control panel.</p>

        <%-- Admin Actions --%>
        <button  class="btn btn-primary mb-2" onclick="document.location = '/parts/useradmin';">Edit Users</button>
        <button  class="btn btn-primary mb-2" onclick="document.location = '/parts/itemadmin';">Edit Items</button>
        <button  class="btn btn-danger mb-2" onclick="document.location = '/parts/admin/kill';">Shut Down</button>
        
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />
    </body>
</html>

