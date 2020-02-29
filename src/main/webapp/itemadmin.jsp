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
        <tr>
            <td><input type="text" class="form-control" value="RoboRIO" name="name"></td>
            <td><input type="text" class="form-control" value="450" name="name"></td>
            <td><input type="text" class="form-control" value="3" name="name"></td>
            <td><input type="text" class="form-control" value="Electronics bin" name="name"></td>
            <td>Darth Raider,Practice Bot,MiniBot</td>
            <td><textarea type="text" class="form-control" value="..." name="name" rows="3">...</textarea></td>
            <td><button type="submit" class="btn btn-primary">Update</button><br><button  class="btn btn-danger" onclick="delItem('RoboRIO');">Delete</button></td>
            </tr>
        ${items}
        </tbody>
        </table>

        <%-- Admin Actions --%>
        <button  class="btn btn-primary mb-2" onclick="document.location = '/parts/additem';">Add Item</button>
        
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />
        <script src="/parts/static/js/deluser.js"></script>
    </body>
</html>

