<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="layouts/head.jsp" />

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

