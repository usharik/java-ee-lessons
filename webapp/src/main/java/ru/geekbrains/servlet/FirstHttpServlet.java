package ru.geekbrains.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(urlPatterns = "first/http/servlet/*")
public class FirstHttpServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        resp.getWriter().println("<h1>Hello from first HTTP servlet</h1>");
        resp.getWriter().println("<h2>URL parameters:</h2>");
        for (Map.Entry<String, String[]> param : req.getParameterMap().entrySet()) {
            resp.getWriter().printf("<p>%s = %s</p>", param.getKey(),
                    String.join( ", ", param.getValue()));
        }
        resp.getWriter().println("<h2>URL parts:</h2>");
        resp.getWriter().printf("<p>Context path %s</p>", req.getContextPath());
        resp.getWriter().printf("<p>Servlet path %s</p>", req.getServletPath());
        resp.getWriter().printf("<p>Path info %s</p>", req.getPathInfo());
        resp.getWriter().printf("<p>Query string %s</p>", req.getQueryString());
        resp.getWriter().printf("<p>Request URL %s</p>", req.getRequestURL());

        resp.setStatus(HttpServletResponse.SC_OK);
    }
}
