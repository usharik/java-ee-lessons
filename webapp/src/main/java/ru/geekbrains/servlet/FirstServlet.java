package ru.geekbrains.servlet;

import javax.servlet.*;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;

public class FirstServlet implements Servlet, Serializable {

    private transient ServletConfig config;

    private transient Connection conn;

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
        this.conn = (Connection) this.config.getServletContext().getAttribute("dbConnection");
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.getWriter().println("<h1>Hello from first servlet</h1>");
        Boolean redirected = (Boolean) req.getAttribute("forwarded");
        if (redirected != null && redirected) {
            res.getWriter().println("<h2>Request was redirected</h2>");
        }
    }

    @Override
    public String getServletInfo() {
        return "First Servlet";
    }

    @Override
    public void destroy() {

    }
}
