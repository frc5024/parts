<head>
    <jsp:include page="layouts/head.jsp" />
</head>
<body>
    <p>Checking Auth..</p>


    <jsp:include page="layouts/footer.jsp" />
    <%-- Login handler --%>
    <script src="static/js/options.js"></script>
    <script>
    if(options.loggedIn != undefined){

        // Set cookies
        setCookie("loggedIn", (options.loggedIn == "true")?1:0, 28);
        setCookie("admin", (options.admin == "true")?1:0, 28);

    }

    window.location = "/parts/app"
    
    </script>
</body>