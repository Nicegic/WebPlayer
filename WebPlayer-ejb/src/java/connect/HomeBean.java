/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import data.Song;
import data.Playlist;
import java.text.DecimalFormat;
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

    @Override
    public String loadSongInfo(String songname) {
        StringBuffer info = new StringBuffer();
        long songid = (long) em.createQuery("SELECT s.id FROM Song s WHERE s.name LIKE :songname").setParameter("songname", songname).getSingleResult();
        Song song = em.find(Song.class, songid);
        if (song == null) {
        } else {
            info.append("<td>" + song.getName() + "</td>");
            info.append("<td>" + song.getInterpret() + "</td>");
            info.append("<td>" + song.getJahr() + "</td>");
        }
        return info.toString();
    }

    @Override
    public String loadSuggest(int rowid) {
        StringBuilder info = new StringBuilder();
        try {
            long max = (long) em.createQuery("SELECT MAX(s.id) FROM Song s").getSingleResult();
            long index = (long) ((Math.random() * max) + 1);
            /*while(checkUsed(index)){
             index = (long) (Math.random() * max)+1);
             }
             recentlysuggested[actindex]=index;
             actindex++;
             if(actindex>9)
             actindex=0;                 --> aufgrund mangelnder Datengrundlage noch nicht aktiv
             */
            Song song = em.find(Song.class, index);
            if (song == null) {
                System.out.println("Keinen Song unter Index 1 gefunden!!!");
            } else {
                String dauer = dcf.format(song.getDauer());
                dauer=dauer.replace(',', ':');
                info.append("<td id=\"songname"+rowid+"\">" + song.getName() + "</td>");
                info.append("<td>" + song.getInterpret() + "</td>");
                info.append("<td>" + dauer + "</td>");
                info.append("<td>" + song.getGenre() + "</td>");
                info.append("<td>" + song.getBewertungGesamt() + "</td>");
            }
        } catch (Exception e) {
        }
        return info.toString();
    }

    @Override
    public String playSong(String songname) {
        long songid = (long) em.createQuery("SELECT s.id FROM Song s WHERE s.name LIKE :songname").setParameter("songname", songname).getSingleResult();
        Song song = em.find(Song.class, songid);
        if (song == null) {
            return "";
        } else {
            return song.getSongPfad();
        }
    }

    @Override
    public void loadPlaylist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadPlaylist(String name, String username) {
        
    }

    @Override
    public String playPlaylist(String name, String username) {
        long pid = (long)em.createQuery("SELECT id FROM Playlist p WHERE p.nutzer.id LIKE :username").setParameter("username", username).getSingleResult();
        Playlist p = em.find(Playlist.class, pid);
        return playSong(p.getSong(0));
    }

    @Override
    public void editPlaylist(String songname, String username, boolean remove) {
        long id = (long)em.createQuery("SELECT id FROM Song s WHERE s.name LIKE :songname").setParameter("songname",songname).getSingleResult();
        Song s = em.find(Song.class, id);
        long pid = (long)em.createQuery("SELECT id FROM Playlist p WHERE p.nutzer.id LIKE :username").setParameter("username", username).getSingleResult();
        Playlist p = em.find(Playlist.class, pid);
        if(remove)
            p.removeSong(s);
        else
            p.addSong(s);
    }

    private boolean checkUsed(long index) {
        for (int i = 0; i < 10; i++) {
            if (recentlysuggested[i] == index) {
                return true;
            }
        }
        return false;
    }

}
