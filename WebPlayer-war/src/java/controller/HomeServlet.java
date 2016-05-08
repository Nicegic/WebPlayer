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

    /**
     * ist obsolet
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
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

    /**
     * wird nur beim allerersten Aufruf genutzt, um den Browser auf die
     * Homepage.jsp weiterzuleiten
     *
     * @param request --> Die HTTP-Anfrage
     * @param response --> Die HTTP-Antwort
     * @throws ServletException --> an das Servlet gebundene Exception
     * @throws IOException --> Exception bei Lese-/Schreibvorgängen
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("homepage.jsp").forward(request, response);

    }

    /**
     * wird bei allen anderen Aufrufen aufgerufen. Dabei werden alle notwendigen
     * Parameter im Request als Parameter übergeben. Im Response ist dann der
     * HTML-Code, der per JScript/JQuery in die Seite eingebunden wird.
     *
     * @param request --> kann die Parameter action, username, playlistno,
     * songno, songid, stars, removes, playlistname und search enthalten
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");
        PrintWriter pw = response.getWriter();
        String username = request.getParameter("username");
        long playlistno,songno,songid;
        int stars;
        playlistno=songno=songid=0L;
        stars=0;
        try {
            playlistno = Long.parseLong(request.getParameter("playlistno"));
        } catch (NumberFormatException nfe) {
        }
        try {
            songno = Long.parseLong(request.getParameter("songno"));
        } catch (NumberFormatException nfe) {
        }
        try {
            songid = Long.parseLong(request.getParameter("songid"));
        } catch (NumberFormatException nfe) {
        }
        try {
            stars = Integer.parseInt(request.getParameter("stars"));
        } catch (NumberFormatException nfe) {
        }
        String removes = request.getParameter("remove");
        String playlistname = request.getParameter("playlistname");
        String search = request.getParameter("search");
        switch (action) {
            case "login":
                homebean.userLoggedIn(username);
                break;
            case "logout":
                homebean.userLoggedOut(username);
                break;
            case "loadNext":
                pw.append(homebean.loadNext(username));
                pw.flush();
                break;
            case "playPlaylistSong":
                pw.append(homebean.playPlaylistSong(username, playlistno, songno));
                pw.flush();
                break;
            case "review":
                homebean.reviewSong(songid, stars, username);
                break;
            case "loadPlaylistSongInfo":
                pw.append(homebean.loadPlaylistSongInfo(username));
                pw.flush();
                break;
            case "edit":
                request.getRequestDispatcher("playlistedit.jsp").forward(request, response);
                break;
            case "showsongs":
                pw.append(homebean.showSongs());
                pw.flush();
                break;
            case "loadPlaylists":
                pw.append(homebean.loadPlaylists(username));
                pw.flush();
                break;
            case "loadplaylist":
                pw.append(homebean.loadPlaylist(playlistno, username));
                pw.flush();
                break;
            case "editplaylist":
                boolean remove = false;
                if (removes.equals("true")) {
                    remove = true;
                }
                homebean.editPlaylist(songid, playlistno, username, remove);
                break;
            case "addPlaylist":
                homebean.addPlaylist(username, playlistname);
                break;
            case "info":
                pw.append(homebean.loadSongInfo((long) songid));
                pw.flush();
                break;
            case "play":
                pw.append(homebean.playSong(songid));
                pw.flush();
                break;
            case "suggest":
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
                break;
            case "search":
                request.getRequestDispatcher("searchresult.jsp").forward(request, response);
                break;
            case "searchresults":
                pw.append(homebean.search(search));
                pw.flush();
                break;
        }
    }

    /**
     * nicht notwendig
     *
     * @return
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
