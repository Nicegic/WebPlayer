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
@WebServlet(name = "HomeServlet", urlPatterns = {"/home"})
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
        request.getRequestDispatcher("home.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        PrintWriter pw = response.getWriter();
        if (action == null) {

        } else if (action.equals("info")) {
            String songname = request.getParameter("songname");
            pw.append(homebean.loadSongInfo(songname));
            pw.flush();
        } else if (action.equals("play")) {
            String songname = request.getParameter("songname");
            pw.append(homebean.playSong(songname));
            pw.flush();
        } else if (action.equals("suggest")) {
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
        } else {
            processRequest(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
