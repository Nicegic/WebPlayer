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
        int rowid = Integer.parseInt(request.getParameter("rowid"));
        String info = null;
        try {
            info = homebean.loadSuggest(rowid);
        } catch (Exception e) {
        }
        PrintWriter out = response.getWriter();
        if (info == null || info.length() == 0) {
            out.append("<td> Fehler </td>");
            out.append("<td> beim Erstellen </td>");
            out.append("<td> der </td>");
            out.append("<td> Empfehlungen! </td>");
            out.append("<td> Bitte Seite neu laden! </td>");
        } else {
            out.append(info);
        }
        out.flush();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String play = request.getParameter("action");
        String songname = request.getParameter("songname");
        String loeschen = request.getParameter("loeschen");
        PrintWriter pw = response.getWriter();
        if (play == null) {

        } else if (play.equals("info")) {
            pw.append(homebean.loadSongInfo(songname));
            pw.flush();
        } else if (play.equals("play")) {
            pw.append(homebean.playSong(songname));
            pw.flush();
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
