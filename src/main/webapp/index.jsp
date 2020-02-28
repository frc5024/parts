<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <jsp:include page="layouts/head.jsp" />

    </head>
    <body>
        <jsp:include page="layouts/nav.jsp" />

        <div class="container">

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

