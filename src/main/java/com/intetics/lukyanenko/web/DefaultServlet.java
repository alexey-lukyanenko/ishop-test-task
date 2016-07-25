package com.intetics.lukyanenko.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class DefaultServlet
        extends HttpServlet
{
  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
          throws ServletException, IOException
  {
    resp.setContentType("text/html");
    PrintWriter out = resp.getWriter();
    //
    out.println("<html><head>");
    out.println("<title>A Sample Servlet!</title>");
    out.println("</head>");
    out.println("<body>");
    out.println("<h1>Start page</h1>");
    out.println("</body></html>");
    out.close();
  }
}

