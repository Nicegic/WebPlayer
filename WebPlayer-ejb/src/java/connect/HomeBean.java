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
    long[] recentlysuggested = new long[10];
    int actindex=0;

    @Override
    public String[] playSong(long songID) {
        String[] songpfad= new String[4];
        Song song = em.find(Song.class, songID);
        if (song == null) {
            songpfad = null;
        } else {
            songpfad[0] = song.getSongPfad();
            songpfad[1] = song.getName();
            songpfad[2] = song.getInterpret();
            songpfad[3] = "2015";
        }
        return songpfad;
    }

    @Override
    public String[] loadSuggest() {
        String[] songinfo=null;
        try{long max = (long)em.createQuery("SELECT MAX(s.id) FROM Song s").getSingleResult();
        long index = (long) ((Math.random() * max) + 1);
        /*while(checkUsed(index)){
            index = (long) (Math.random() * max)+1);
        }
        recentlysuggested[actindex]=index;
        actindex++;
        if(actindex>9)
            actindex=0;
        */
        Song song = em.find(Song.class, index);
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
        }catch(Exception e){}
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
    
    private boolean checkUsed(long index){
        for(int i=0;i<10;i++){
            if(recentlysuggested[i]==index){
                return true;
            }
        }
        return false;
    }

}
