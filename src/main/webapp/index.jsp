<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="layouts/head.jsp" />
        <link rel="stylesheet" href="static/css/items.css">

    </head>
    <body>
        <jsp:include page="layouts/nav.jsp" />

        <div class="container">

            <div style="height:10vh;"></div>

            <%-- Search Bar --%>
            <form class="form-inline" style="max-width:350;margin:auto;">
                <input class="form-control mr-sm-2" type="search" placeholder="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>

            <div style="height:5vh;"></div>

            <%-- Items list --%>
            <div class="items">
                <div class="inv-item card border-secondary" >
                    <div class="card-header">RoboRIO</div>
                    <div class="card-body text-secondary">

                        <div class="item-params">
                            <p style="min-width:300px;"><span class="badge badge-primary">Storage location</span> Electronics bin</p>
                            <p><span class="badge badge-primary">Cost</span> $450</p>
                            <p><span class="badge badge-primary">Quantity</span> 3</p>
                        </div>
                        <p class="card-text">The RoboRIO is the main processor used on our robots. It interfaces with all other electronic devices, and handles driver inputs.</p>

                        <hr>
                        <h5 class="card-title">Known Locations</h5>

                        <form class="form-inline my-2 my-lg-0">
                            <input class="form-control mr-sm-2" type="search" placeholder="Add Location" aria-label="Search">
                            <button class="btn btn-outline-success my-2 my-sm-0" type="submit">+</button>
                        </form>
                        
                    </div>
            </div>
            </div>


        </div>

        <jsp:include page="layouts/footer.jsp" />

        <%-- Login handler --%>
        <script src="static/js/options.js"></script>
        <script>
        if(options.loggedIn != undefined){

            // Set cookies
            setCookie("loggedIn", (options.loggedIn == "true")?1:0, 28);
            setCookie("admin", (options.admin == "true")?1:0, 28);

            // Clear url
            document.location = "/parts";
        }
        </script>
    </body>
</html>

