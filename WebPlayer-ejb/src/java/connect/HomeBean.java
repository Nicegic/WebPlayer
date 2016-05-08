/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import data.Benutzer;
import data.Bewertung;
import data.Song;
import data.Playlist;
import java.text.DecimalFormat;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ejb.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nicolas
 */
@Singleton
public class HomeBean implements HomeBeanLocal {

    @PersistenceContext(unitName = "WebPlayer-ejbPU")
    EntityManager em;
    long[] recentlysuggested = new long[10];
    int actindex = 0;
    DecimalFormat dcf = new DecimalFormat("00.00 min");
    final Object lock = new Object();
    /**
     * activeUsers: Die Liste aller aktiven Benutzer der Website
     * actualPlaylist: Die aktuelle playlist des i-ten Benutzers
     * actualSong: Der aktuelle Song in der i-ten Playlist des i-ten Benutzers
     */
    CopyOnWriteArrayList<Benutzer> activeUsers;
    CopyOnWriteArrayList<Long> actualPlaylist;
    CopyOnWriteArrayList<Long> actualSong;

    public HomeBean() {
        activeUsers = new CopyOnWriteArrayList<>();
        actualPlaylist = new CopyOnWriteArrayList<>();
        actualSong = new CopyOnWriteArrayList<>();
    }

    /**
     * Überprüfung, ob der User bereits in der Liste der aktiven User vorhanden ist
     * @param username --> Username des Nutzers
     * @return boolean 
     */
    private boolean alreadyloggedin(String username) {
        boolean there = false;
        for (int i = 0; i < activeUsers.size(); i++) {
            if (activeUsers.get(i).getName().equals(username)) {
                there = true;
            }
        }
        return there;
    }

    @Override
    public void userLoggedIn(String username) {
        synchronized (lock) {
            if (!alreadyloggedin(username)) {
                Benutzer b = em.find(Benutzer.class, username);
                activeUsers.add(b);
                actualPlaylist.add(-1L);
                actualSong.add(-1L);
                System.out.println(b.getName() + " logged in");
            }
        }
    }

    @Override
    public void userLoggedOut(String username) {
        synchronized (lock) {
            if (alreadyloggedin(username)) {
                Benutzer b = searchFor(username);
                int editindex = getUserIndex(username);
                activeUsers.remove(b);
                actualPlaylist.remove(editindex);
                actualSong.remove(editindex);
                System.out.println(b.getName() + " logged out");
            }
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
            info.append(song.getJahr() + " - ");
            info.append(song.getBewertungGesamt()+"</p>");
            info.append("<input type=\"hidden\" value=\"" + song.getId() + "\"/>");
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
                info.append("<p> Fehler beim Laden, keine Daten vorhanden!</p>");
            } else {
                info.append("<p>" + song.getName() + "</p>");
                info.append("<p>" + song.getInterpret() + "</p>");
                /*info.append("<div>");
                int i=0;
                while(i<song.getBewertungGesamt()){
                    info.append("<span class=\"ratingsel\">&#9733;</span>");
                    i++;
                }
                while(i<5){
                    info.append("<span class=\"ratingnotsel\">&#9733;</span>");
                }
                info.append("</div>");*/
                info.append("<p>" + song.getBewertungGesamt() + "</p>");
                info.append("<p>" + song.getJahr() + "</p>");
                info.append("<input type=\"hidden\" value=\"" + song.getId() + "\"/>");
            }
        } catch (Exception e) {
        }
        return info.toString();
    }

    @Override
    public String playPlaylistSong(String username, long playlistno, long pos) {
        synchronized (lock) {
            StringBuilder sb = new StringBuilder();
            Benutzer b = searchFor(username);
            Playlist p = findPlaylist(playlistno, b);
            int editindex = getUserIndex(username);
            System.out.println(editindex + "play");
            if (p == null) {

            } else {
                editindex = getUserIndex(username);
                System.out.println(editindex + "play");
                actualPlaylist.set(editindex, playlistno);
                editindex = getUserIndex(username);
                System.out.println(editindex + "play");
                Song s = findSong(pos, p);
                editindex = getUserIndex(username);
                System.out.println(editindex + "play");
                if (s != null) {
                    sb.append(s.getSongPfad());
                    actualSong.set(editindex, (long) pos);
                    editindex = getUserIndex(username);
                    System.out.println(editindex + "play");
                }
            }
            return sb.toString();
        }
    }

    @Override
    public String loadNext(String username) {
        synchronized (lock) {
            StringBuilder sb = new StringBuilder();
            Benutzer b = searchFor(username);
            int index = getUserIndex(username);
            Playlist p = findPlaylist(actualPlaylist.get(index), b);
            long actSong = actualSong.get(index);
            actSong++;
            if (actSong == p.getSongs().size()) {
                actSong = 0L;
            }
            actualSong.set(index, actSong);
            Song s = findSong(actualSong.get(index), p);
            sb.append(s.getSongPfad());
            return sb.toString();
        }
    }

    @Override
    public String loadPlaylistSongInfo(String username) {
        synchronized (lock) {
            StringBuilder sb = new StringBuilder();
            Benutzer b = searchFor(username);
            int index = getUserIndex(username);
            System.out.println(index + "info");
            Playlist p = findPlaylist(actualPlaylist.get(index), b);
            index = getUserIndex(username);
            System.out.println(index + "info");
            Song s = findSong(actualSong.get(index), p);
            index = getUserIndex(username);
            System.out.println(index + "info");
            sb.append("<p>" + s.getName() + " - ");
            sb.append(s.getInterpret() + " - ");
            sb.append(s.getJahr() + " - ");
            sb.append(s.getBewertungGesamt()+"</p>");
            return sb.toString();
        }
    }

    @Override
    public void reviewSong(long songid, int stars, String username) {
        Song s = em.find(Song.class, songid);
        Benutzer b = searchFor(username);
        Bewertung bw = new Bewertung(s, b, stars);
        LinkedHashSet<Bewertung> wertungen = b.getBewertungen();
        Iterator it = wertungen.iterator();
        Bewertung that=null;
        boolean alreadyexisting = false;
        long bewertungsid=0;
        while (it.hasNext()) {
            that = (Bewertung) it.next();
            if (that.getSongID() == bw.getSongID()) {
                bewertungsid=that.getId();
                alreadyexisting = true;
                break;
            }
        }
        if (alreadyexisting) {
            that=em.find(Bewertung.class, bewertungsid);
            s.removeBewertung(that);
            s.addBewertung(bw);
            b.removeBewertung(that);
            b.addBewertung(bw);
            em.remove(that);
            em.persist(bw);
            em.merge(s);
            em.merge(b);
        } else {
            s.addBewertung(bw);
            b.addBewertung(bw);
            em.persist(bw);
            em.merge(s);
            em.merge(b);
        }
    }

    @Override
    public String loadPlaylists(String username) {
        synchronized (lock) {
            StringBuilder out = new StringBuilder();
            Benutzer b = searchFor(username);
            if (b == null) {
                out.append("<div class=\"playlist\">");
                out.append("Es ist ein interner Fehler aufgetreten. Bitte versuche es sp&auml;ter erneut!");
                out.append("<button onclick=\"loadPlaylists();\">retry</button>");
                out.append("</div>");
            } else {
                LinkedHashSet<Playlist> lists = b.getPlaylists();
                int i = 0;
                for (Playlist p : lists) {
                    out.append("<div class=\"playlist\">");
                    out.append(p.getName());
                    out.append("<form name=\"edit\" action=\"home\" target=\"home\" method=\"post\">");
                    out.append("<input type=\"hidden\" name=\"action\" value=\"edit\"/>");
                    out.append("<input type=\"hidden\" name=\"playlist\" value=\"" + i + "\"/>");
                    out.append("<input type=\"hidden\" name=\"username\" value=\"" + username + "\"/>");
                    out.append("<button class=\"editbutton\">Edit</button></form>");
                    boolean first = true;
                    if (p.getSongs().size() < 1) {
                        out.append("<div class=\"hint\"> Es sind bisher keine Lieder in der Playlist enthalten.<br>"
                                + "Klicke auf \"edit\", um welche zu adden.</div>");
                    } else {
                        int k = 0;
                        for (Song s : p.getSongs()) {
                            if (first) {
                                out.append("<div class=\"songfirst\" onclick=\"playSongInPlaylist(" + k + "," + i + ")\">");
                                first = false;
                            } else {
                                out.append("<div class=\"song\" onclick=\"playSongInPlaylist(" + k + "," + i + ")\">");
                            }
                            out.append(s.getName());
                            out.append("<input type=\"hidden\" value=\"" + s.getId() + "\"/>");
                            out.append("</div>");
                            k++;
                        }
                    }
                    out.append("</div>");
                    i++;
                }
            }
            return out.toString();
        }
    }

    @Override
    public void addPlaylist(String username, String playlistname) {
        synchronized (lock) {
            Benutzer b = searchFor(username);
            Playlist p = new Playlist(b, playlistname);
            b.addPlaylist(p);
            em.merge(b);
            em.persist(p);
        }
    }

    @Override
    public String loadPlaylist(long no, String username) {
        synchronized (lock) {
            System.out.println("LoadPlaylist wird ausgeführt!");
            StringBuilder sb = new StringBuilder();
            Benutzer b = searchFor(username);
            if (b == null) {
                sb.append("<div class=\"suggest\">Es ist ein Fehler aufgetreten, bitte versuche es sp&auml;ter erneut!</div>");
                sb.append("<button class=\"buttons\" onclick=\"loadPlaylist()\">retry </button>");
            } else {
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
                        sb.append("<button class=\"adder\" onclick=\"editPlaylist(" + s.getId() + ",true);\">delete</button>");
                        sb.append("<input type=\"hidden\" value=\"" + s.getId() + "\"/>");
                    }
                }
            }
            return sb.toString();
        }
    }

    @Override
    public void editPlaylist(long songid, long no, String username, boolean remove) {
        synchronized (lock) {
            System.out.println("editPlaylist mit songid" + songid + ", playlistno" + no + ", username " + username + " und " + remove + " aufgerufen");
            Benutzer b = searchFor(username);
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
                    em.merge(b);
                    System.out.println("Song wurde hinzugefügt");
                } else {
                    p.removeSong(s);
                    s.removeFromPlaylist(p);
                    em.merge(s);
                    em.merge(p);
                    em.merge(b);
                    System.out.println("Song wurde entfernt");
                }
            } else {
                System.out.println("Der Song wurde nicht gefunden!");
            }
        }
    }
    
    @Override
    public String search(String search) {
        StringBuilder result = new StringBuilder();
        List<Song> songs = em.createQuery("SELECT s FROM Song s WHERE s.name LIKE :search").setParameter("search", "%" + search + "%").getResultList();
        for (Song song : songs) {
            result.append("<div class=\"result\">");
            result.append(song.getName() + "<br>");
            result.append(song.getInterpret() + "<br>");
            result.append(song.getBewertungGesamt() + "<br>");
            result.append(song.getJahr() + "<br>");
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
            sb.append("<button class=\"buttons\" onclick=\"editPlaylist(" + s.getId() + ",false)\">add</button>");
            sb.append("<input type=\"hidden\" value=\"" + s.getId() + "\"/>");
            sb.append("</div>");
        }
        return sb.toString();
    }
    
    /**
     * überprüft, ob die gewählte Songid bereits vorgeschlagen wurde
     * @param index --> Die ID des neu ausgewählten Songs
     * @return boolean --> ob die ID bereits genutzt wird
     */
    private boolean checkUsed(long index) {
        for (int i = 0; i < 10; i++) {
            if (recentlysuggested[i] == index) {
                return true;
            }
        }
        return false;
    }

    /**
     * sucht in der Liste der aktiven Nutzer nach dem angegebenen User und gibt diesen zurück
     * @param username --> Username des gesuchten Nutzers
     * @return Benutzer --> Der gesuchte User oder null, wenn dieser nicht gefunden werden kann.
     */
    private synchronized Benutzer searchFor(String username) {
        synchronized (lock) {
            System.out.println("Es scheinen " + activeUsers.size() + " Benutzer angemeldet zu sein");
            for (int i = 0; i < activeUsers.size(); i++) {
                if (activeUsers.get(i).getName().equals(username)) {
                    Benutzer b = activeUsers.get(i);
                    return b;
                }
            }
            return null;
        }
    }

    /**
     * Gibt die Position des gesuchten Nutzers in der Liste der angemeldeten Benutzer zurück.
     * @param username --> Username des Nutzers
     * @return int --> Die Position in der Liste, an der der User steht
     */
    private synchronized int getUserIndex(String username) {
        synchronized (lock) {
            for (int i = 0; i < activeUsers.size(); i++) {
                if (activeUsers.get(i).getName().equals(username)) {
                    return i;
                }
            }
            return -1;
        }
    }

    /**
     * Gibt die gesuchte Playlist von dem angegebenen Nutzer zurück.
     * @param playlistno --> die wievielte Playlist des Benutzers gesucht ist
     * @param b --> Der Besucher, dessen Playlist gesucht wird
     * @return Playlist --> die gesuchte Playlist oder null, wenn diese nicht gefunden wird
     */
    private Playlist findPlaylist(long playlistno, Benutzer b) {
        synchronized (lock) {
            LinkedHashSet<Playlist> lists = b.getPlaylists();
            Iterator it = lists.iterator();
            if (lists.size() > 0) {
                for (long i = 0; i < playlistno; i++) {
                    it.next();
                }
                Playlist p = (Playlist) it.next();
                return p;
            }
            return null;
        }
    }

    /**
     * Sucht den pos-ten Song in der angegebenen Playlist und gibt diesen zurück.
     * @param pos --> die Position, an der sich der Song befinden soll
     * @param p --> die Playlist, in der der Song vorhanden sein soll
     * @return Song --> der gesuchte Song oder null, wenn dieser nicht gefunden werden kann
     */
    private Song findSong(long pos, Playlist p) {
        synchronized (lock) {
            LinkedHashSet<Song> songs = p.getSongs();
            if (songs.size() > 0) {
                Iterator it = songs.iterator();
                for (int i = 0; i < pos; i++) {
                    it.next();
                }
                Song s = (Song) it.next();
                return s;
            }
            return null;
        }
    }
}
