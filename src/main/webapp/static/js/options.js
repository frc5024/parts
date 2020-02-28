var options = window.location.search.slice(1)
                      .split('&')
                      .reduce(function _reduce (/*Object*/ a, /*String*/ b) {
                        b = b.split('=');
                        a[b[0]] = decodeURIComponent(b[1]);
                        return a;
                      }, {});