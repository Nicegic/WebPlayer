/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import connect.HomeBeanLocal;
import java.io.IOException;
import java.io.PrintWriter;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Aaron
 */
@WebServlet(name = "HomeServlet", urlPatterns = {"/HomeServlet"})
public class HomeServlet extends HttpServlet {

    @EJB
    HomeBeanLocal homebean;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet HomeServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet HomeServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String[] songinfo = homebean.loadSuggest();
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        out.append("<song>");
        out.append("<titel>"+songinfo[0]+"</titel>");
        out.append("<interpret>"+songinfo[1]+"</interpret>");
        out.append("<dauer>"+songinfo[2]+"</dauer>");
        out.append("<genre>"+songinfo[3]+"</genre<");
        out.append("<bewertung>"+songinfo[4]+"</bewertung>");
        out.append("</song>");
        out.println();
        out.flush();
        System.out.println("Must have written HTTP-Response");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String play = request.getParameter("play");
        String clickid = request.getParameter("id");
        int songID = Integer.parseInt(request.getParameter("songid"));
        String loeschen = request.getParameter("loeschen");
        if (play == null) {

        } else if (play.equals("play")) {
            if (clickid.matches("^r([0-9]||10)$")) {
                homebean.playSong(0);
            }
        } else {
            processRequest(request, response);
        }

        if (loeschen == null) {

        } else if (loeschen.equals("loeschen")) {
            request.getRequestDispatcher("index.html").forward(request, response);
        } else {
            processRequest(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
