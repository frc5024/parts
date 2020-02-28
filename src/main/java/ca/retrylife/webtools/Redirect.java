package ca.retrylife.webtools;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class Redirect {
    public static void redirTo(HttpServletResponse resp, String dest) throws IOException {
        
        // Build an HTML redirector
        String doc = String.format(
                "<head><title>Redirecting</title></head><body><p>Redirecting to %s</p><script>document.location = '%s';</script></body>", dest, dest);


        // Write to response
        resp.getWriter().print(doc);
    }
}