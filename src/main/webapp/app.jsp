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
            <form class="form-inline" style="max-width:350;margin:auto;" method="POST">
                <input class="form-control mr-sm-2" type="search" name="search" placeholder="Search">
                <button class="btn btn-outline-success my-2 my-sm-0" type="submit">Search</button>
            </form>

            <div style="height:5vh;"></div>

            <%-- Items list --%>
            <div class="items">
                <%-- <div class="inv-item card border-secondary" >
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

                        <ul>
                        <li><a onclick="delLocation('RoboRIO', 'Darth Raider');" href="#"><span class="badge badge-danger">X </span></a> Darth Raider</li>
                        <li><a onclick="delLocation('RoboRIO', 'Practice Bot');" href="#"><span class="badge badge-danger">X </span></a> Practice Bot</li>
                        <li><a onclick="delLocation('RoboRIO', 'MiniBot');" href="#"><span class="badge badge-danger">X </span></a> MiniBot</li>
                        </ul>

                        <div class="form-inline my-2 my-lg-0">
                            <input class="form-control mr-sm-2" type="search" placeholder="Add Location" id="RoboRIO-location-add" aria-label="Search">
                            <button class="btn btn-outline-success my-2 my-sm-0" onclick="addLocation('RoboRIO');">+</button>
                        </div>
                        
                    </div>
            </div> --%>

            ${items}
            </div>


        </div>

        <jsp:include page="layouts/footer.jsp" />

        <script src="static/js/manageitems.js"></script>
    </body>
</html>

