<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="layouts/head.jsp" />
        <link rel="stylesheet" href="static/css/login.css">

    </head>
    <body>
        <jsp:include page="layouts/nav.jsp" />        
        
        <div class="container">
        <div style="height:10vh;"></div>
        <div class="login-container">

        <h2>Add an Item</h2>
        <hr>
        <p>Here, you can add items to the inventory. Please use only integers (<strong>no commas or decimals</strong>) for cost and quantity.</p>

         <form class="form" method="POST">
            <div class="form-group mb-2">
                <label for="name" class="sr-only">Name</label>
                <input type="text" class="form-control" id="name" name="name" placeholder="Name">
            </div><br>
            <div class="form-group mb-2">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <div class="input-group-text">#</div>
                    </div>
                    <label for="quantity" class="sr-only">Quantity</label>
                    <input type="number" class="form-control" id="quantity" name="quantity" placeholder="Quantity">
                </div>
            </div><br>
            <div class="form-group mb-2">
                <div class="input-group">
                    <div class="input-group-prepend">
                        <div class="input-group-text">$</div>
                    </div>
                    <label for="cost" class="sr-only">Cost</label>
                    <input type="number" class="form-control" id="cost" name="cost" placeholder="Cost">
                </div>
            </div><br>
            <div class="form-group mb-2">
                <label for="home" class="sr-only">Home</label>
                <input type="text" class="form-control" id="home" name="home" placeholder="Where it is stored">
            </div><br>
            <div class="form-group mb-2">
                <label for="description" class="sr-only">Description</label>
                <textarea type="text" class="form-control" id="description" name="description" rows="6">Write a description for this item here</textarea>
            </div><br>
            <button type="submit" class="btn btn-primary mb-2">Add Item</button>
        </form>

        
        </div>
        </div>

        <jsp:include page="layouts/footer.jsp" />
    </body>
</html>

