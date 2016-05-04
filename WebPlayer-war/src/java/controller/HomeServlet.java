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
        response.addHeader("username", request.getParameter("username"));
        request.getRequestDispatcher("homepage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        PrintWriter pw = response.getWriter();
        if (action == null) {
            
        } else if(action.equals("login")){
            String username=request.getParameter("username");
            homebean.userLoggedIn(username);
        }else if(action.equals("logout")){
            String username=request.getParameter("username");
            homebean.userLoggedOut(username);
        }else if(action.equals("edit")){
            request.getRequestDispatcher("playlistedit.jsp").forward(request, response);
        }else if(action.equals("showsongs")){
            pw.append(homebean.showSongs());
            pw.flush();
        }else if(action.equals("loadPlaylists")){
            String username=request.getParameter("username");
            pw.append(homebean.loadPlaylists(username));
            pw.flush();
        }else if(action.equals("loadplaylist")){
            String username=request.getParameter("username");
            long playlistno=Long.parseLong(request.getParameter("playlistno"));
            pw.append(homebean.loadPlaylist(playlistno, username));
            pw.flush();
        }else if(action.equals("editplaylist")){
            String username=request.getParameter("username");
            
        }else if(action.equals("addPlaylist")){
            String username=request.getParameter("username");
            String playlistname=request.getParameter("playlistname");
            homebean.addPlaylist(username, playlistname);
        }else if (action.equals("search")) {
            request.getRequestDispatcher("searchresult.jsp").forward(request, response);
        } else if(action.equals("searchresults")){
            String search = request.getParameter("search");
            pw.append(homebean.search(search));
            pw.flush();
        }else if (action.equals("info")) {
            int songid = Integer.parseInt(request.getParameter("songid"));
            pw.append(homebean.loadSongInfo((long) songid));
            pw.flush();
        } else if (action.equals("play")) {
            int songid = Integer.parseInt(request.getParameter("songid"));
            pw.append(homebean.playSong((long) songid));
            pw.flush();
        } else if (action.equals("suggest")) {
            String info = null;
            try {
                info = homebean.loadSuggestNew();
            } catch (Exception e) {
            }
            if (info == null || info.length() == 0) {
                pw.append("<p> Fehler beim Erstellen</p>");
                pw.append("<p> der Empfehlungen. </p>");
                pw.append("<p> Bitte Seite neu laden! </p>");
            } else {
                pw.append(info);
            }
            pw.flush();
        } else {
            processRequest(request, response);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
