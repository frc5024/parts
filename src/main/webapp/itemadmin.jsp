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

        <h2>Edit Items</h2>
        <hr>
        <br>

        <table class="table">
        <thead>
            <tr>
            <td>Item Name</td>
            <td>Cost</td>
            <td>Quantity</td>
            <td>Home</td>
            <td>Locations</td>
            <td>Description</td>
            <td>Edit</td>
            </tr>
        </thead>
        <tbody>
        ${items}
        </tbody>
        </table>

        <%-- Admin Actions --%>
        <button  class="btn btn-primary mb-2" onclick="document.location = '/parts/additem';">Add Item</button>
        
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />
        <script src="/parts/static/js/manageitems.js"></script>
    </body>
</html>

