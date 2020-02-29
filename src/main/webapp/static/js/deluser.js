function delUser(user) {
    fetch("/parts/adduser?username=" + user, {
        method: 'delete',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ username: user })
    }).then(() => {
        location.reload();
    })
}