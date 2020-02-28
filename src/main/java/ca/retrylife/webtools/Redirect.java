package ca.retrylife.webtools;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

public class Redirect {
    public static void redirTo(HttpServletResponse resp, String dest) throws IOException {
        
        // Build an HTML redirector
        String doc = String.format(
                "<head><title>Redirecting</title><meta http-equiv='Refresh' content='0; url=%s' /></head><body></body>", dest);


        // Write to response
        resp.getWriter().print(doc);
    }
}