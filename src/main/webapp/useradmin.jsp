<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="layouts/head.jsp" />
        <link rel="stylesheet" href="static/css/adminpanel.css">

    </head>
    <body>
        <jsp:include page="layouts/nav.jsp" />

            
        
        
        <div class="container">
        <div style="height:10vh;"></div>
        <div class="admin-edit-container">

        <h2>Edit Users </h2>
        <hr>
        <br>

        <table class="table">
        <thead>
            <tr>
            <td>Username</td>
            <td>Password Hash</td>
            <td>Permissions</td>
            <td>Edit</td>
            </tr>
        </thead>
        <tbody>
        ${users}
        </tbody>
        </table>

        <%-- Admin Actions --%>
        <button  class="btn btn-primary mb-2" onclick="document.location = '/parts/adduser';">Add User</button>
        
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />
        <script src="/parts/static/js/deluser.js"></script>
    </body>
</html>

