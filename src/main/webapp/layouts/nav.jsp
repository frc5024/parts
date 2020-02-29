<nav class="navbar navbar-light bg-light">
    <div class="container">
        <a class="navbar-brand" href="/parts">5024 Inventory</a>
        
        <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
            <li class="nav-item active">
                <a class="nav-link" href="/parts/admin" id="admin-btn-nav" hidden><span class="badge badge-danger">Admin</span></a>
            </li>
        </ul>

        <button class="btn btn-outline-warning " onclick="document.location = '/parts/login?logout=1'" id="logout-btn-nav">Logout</button>
        <button class="btn btn-outline-success " onclick="document.location = '/parts/login'" id="login-btn-nav">Login</button>
    </div>
</nav>