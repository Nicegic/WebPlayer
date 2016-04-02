/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import data.Song;
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

    @Override
    public String playSong(int songID) {
        String songpfad;
        Song song = em.find(Song.class, songID);
        if (song == null) {
            songpfad = "Song ist falsch!!!!";
        } else {
            songpfad = "http://localhost:8080/Webplayer-war/songs/" + song.getSongPfad();
        }
        return songpfad;
    }

    @Override
    public String[] loadSuggest() {
        long max = (long)em.createQuery("SELECT MAX(s.id) FROM Song s").getSingleResult();
        long index = (long) ((Math.random() * max) + 1);
        Song song = em.find(Song.class, index);
        String[] songinfo=null;
        if (song == null) {
            System.out.println("Keinen Song unter Index 1 gefunden!!!");
        } else {
            songinfo=new String[5];
            songinfo[0]=song.getName();
            songinfo[1]=song.getInterpret();
            songinfo[2]=""+song.getDauer();
            songinfo[3]=song.getGenre();
            songinfo[4]=""+song.getBewertungGesamt();
        }
        return songinfo;
    }

    @Override
    public void playSuggest(int songID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadPlaylist() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void loadPlaylist(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void playPlaylist(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void editPlaylist(int id, boolean remove) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
