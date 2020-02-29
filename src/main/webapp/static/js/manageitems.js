function delItem(name) {
    fetch("/parts/additem?name=" + name, {
        method: 'delete',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name })
    }).then(() => {
        location.reload();
    })
}

function addLocation(name) {
    var loc = document.getElementById(name + "-location-add").value;

    fetch("/parts/app?name=" + name +"&location="+loc, {
        method: 'put',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name, location:loc })
    }).then(() => {
        location.reload();
        document.getElementById(name + "-location-add").value = '';
    })
}

function delLocation(name, loc) {
    fetch("/parts/app?name=" + name +"&location="+loc, {
        method: 'delete',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name: name, location:loc })
    }).then(() => {
        location.reload();
    })
    
}