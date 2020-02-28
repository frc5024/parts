document.getElementById("login-btn-nav").hidden = getCookie("loggedIn") == "1";
document.getElementById("logout-btn-nav").hidden = getCookie("loggedIn") == "0";
document.getElementById("admin-btn-nav").hidden = getCookie("admin") == "0";