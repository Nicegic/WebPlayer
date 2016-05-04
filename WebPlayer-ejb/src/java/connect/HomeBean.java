/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import data.Benutzer;
import data.Playlist;
import data.Song;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nicolas
 */
@Stateless
public class HomeBean implements HomeBeanLocal {

    @PersistenceContext(unitName = "WebPlayer-ejbPU")
    EntityManager em;
    long[] recentlysuggested = new long[10];
    int actindex = 0;
    DecimalFormat dcf = new DecimalFormat("00.00 min");
    ArrayList<Benutzer> activeUsers;

    public HomeBean() {
        activeUsers = new ArrayList<>();
    }

    @Override
    public void userLoggedIn(String username) {
        if (!alreadyloggedin(username)) {
            Benutzer b = em.find(Benutzer.class, username);
            activeUsers.add(b);
            System.out.println(b.getName()+" logged in");
        }
    }

    @Override
    public void userLoggedOut(String username) {
        if (alreadyloggedin(username)) {
            Benutzer b = identify(username);
            activeUsers.remove(b);
            System.out.println(b.getName()+" logged out");
        }
    }

    @Override
    public String loadSongInfo(long songid) {
        StringBuffer info = new StringBuffer();
        Song song = em.find(Song.class, songid);
        if (song == null) {
        } else {
            info.append("<p>" + song.getName() + " - ");
            info.append(song.getInterpret() + " - ");
            info.append(song.getJahr() + "</p>");
        }
        return info.toString();
    }

    @Override
    public String playSong(long songid) {
        Song song = em.find(Song.class, songid);
        if (song == null) {
            return "";
        } else {
            return song.getSongPfad();
        }
    }

    @Override
    public String loadPlaylists(String username) {
        StringBuilder out = new StringBuilder();
        Benutzer b = identify(username);
        LinkedHashSet<Playlist> lists = b.getPlaylists();
        int i = 0;
        for (Playlist p : lists) {
            out.append("<div class=\"playlist\">");
            out.append(p.getName());
            out.append("<form name=\"edit\" action=\"home\" target=\"home\" method=\"post\">");
            out.append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
            out.append("<input type=\"hidden\" name=\"playlist\" value=\"" + i + "\"/>");
            out.append("<input type=\"hidden\" name=\"username\" value=\""+username+"\"/>");
            out.append("<button class=\"editbutton\">Edit</button></form>");
            boolean first = true;
            if(p.getSongs().size()<1){
                out.append("<div class=\"hint\"> Es sind bisher keine Lieder in der Playlist enthalten.<br>"
                        + "Klicke auf \"edit\", um welche zu adden.</div>");
            }else{
            for (Song s : p.getSongs()) {
                if (first) {
                    out.append("<div class=\"songfirst\">");
                    first = false;
                } else {
                    out.append("<div class=\"song\">");
                }
                out.append(s.getName());
                out.append("<input type=\"hidden\" value=\"" + s.getId() + "\"/>");
                out.append("</div>");
            }
            }
            out.append("</div>");
            i++;
        }
        return out.toString();
    }

    @Override
    public void addPlaylist(String username, String playlistname) {
        Benutzer b = identify(username);
        Playlist p = new Playlist(b,playlistname);
        b.addPlaylist(p);
        em.merge(b);
        em.persist(p);
    }

    @Override
    public String loadPlaylist(long no, String username) {
        StringBuilder sb = new StringBuilder();
        Benutzer b = identify(username);
        LinkedHashSet<Playlist> playlists = b.getPlaylists();
        Iterator it = playlists.iterator();
        for (int i = 0; i < no; i++) {
            it.next();
        }
        Playlist p = (Playlist) it.next();
        if (p.getSongs().size() < 1) {
            sb.append("Es sind bisher keine Lieder in der Playlist enthalten!");
        } else {
            for (Song s : p.getSongs()) {
                sb.append("<div class=\"suggest\">");
                sb.append(s.getName() + "<br>");
                sb.append(s.getInterpret() + "<br>");
                sb.append(s.getBewertungGesamt() + "<br>");
                sb.append(s.getJahr() + "</div>");
                sb.append("<input type=\"hidden\" value=\"" + s.getId() + "\"/>");
            }
        }
        return sb.toString();
    }

    @Override
    public void editPlaylist(long songid, long no, String username, boolean remove) {
        Benutzer b = identify(username);
        LinkedHashSet<Playlist> lists = b.getPlaylists();
        Iterator it = lists.iterator();
        for (int i = 0; i < no; i++) {
            it.next();
        }
        Playlist p = (Playlist) it.next();
        Song s = em.find(Song.class, songid);
        if (s != null) {
            if (!remove) {
                p.addSong(s);
                s.addToPlaylist(p);
                em.merge(s);
                em.merge(p);
            } else {
                p.removeSong(s);
                s.removeFromPlaylist(p);
                em.merge(s);
                em.merge(p);
            }
        }
    }

    private boolean checkUsed(long index) {
        for (int i = 0; i < 10; i++) {
            if (recentlysuggested[i] == index) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String loadSuggestNew() {
        StringBuilder info = new StringBuilder();
        Song song = null;
        try {
            long max = (long) em.createQuery("SELECT MAX(s.id) FROM Song s").getSingleResult();
            long index = (long) ((Math.random() * max) + 1);
            if (max > 10) {
                while (checkUsed(index)) {
                    index = (long) ((Math.random() * max) + 1);
                }
            }
            song = em.find(Song.class, index);
            recentlysuggested[actindex] = index;
            actindex++;
            if (actindex > 9) {
                actindex = 0;
            }
            if (song == null) {
                System.out.println("Keinen Song unter Index 1 gefunden!!!");
                info.append("<p> Fehler beim Laden, keine Daten vorhanden!");
            } else {
                info.append("<p>" + song.getName() + "</p>");
                info.append("<p>" + song.getInterpret() + "</p>");
                info.append("<p>" + song.getBewertungGesamt() + "</p>");
                info.append("<p>" + song.getJahr() + "</p>");
                info.append("<input type=\"hidden\" value=\"" + song.getId() + "\"/>");
            }
        } catch (Exception e) {
        }
        return info.toString();
    }

    @Override
    public String search(String search) {
        StringBuilder result = new StringBuilder();
        List<Song> songs = em.createQuery("SELECT s FROM Song s WHERE s.name LIKE :search").setParameter("search", "%" + search + "%").getResultList();
        for (Song song : songs) {
            result.append("<div class=\"result\">");
            result.append("<p>" + song.getName() + "</p>");
            result.append("<p>" + song.getInterpret() + "</p>");
            result.append("<p>" + song.getBewertungGesamt() + "</p>");
            result.append("<p>" + song.getJahr() + "</p>");
            result.append("</div>");
        }
        if (songs.size() < 1) {
            result.append("<div class=\"result\">");
            result.append("Leider konnten keine Ergebnisse zu deiner Suche gefunden werden!");
            result.append("</div>");
        }
        return result.toString();
    }

    @Override
    public String showSongs() {
        StringBuilder sb = new StringBuilder();
        List<Song> songs = em.createQuery("SELECT s FROM Song s").getResultList();
        for (Song s : songs) {
            sb.append("<div class=\"suggest\">");
            sb.append(s.getName() + "<br>");
            sb.append(s.getInterpret() + "<br>");
            sb.append(s.getJahr() + "<br>");
            sb.append(s.getBewertungGesamt() + "<br>");
            sb.append("<input type=\"hidden\" value=\"" + s.getId() + "\"/>");
            sb.append("</div>");
        }
        return sb.toString();
    }

    private boolean alreadyloggedin(String username) {
        boolean there = false;
        for (int i = 0; i < activeUsers.size(); i++) {
            if (activeUsers.get(i).getName().equals(username)) {
                there = true;
            }
        }
        return there;
    }

    private Benutzer identify(String username) {
        for (int i = 0; i < activeUsers.size(); i++) {
            if (activeUsers.get(i).getName().equals(username)) {
                return activeUsers.get(i);
            }
        }
        return null;
    }
}
