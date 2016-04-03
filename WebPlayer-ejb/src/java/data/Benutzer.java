/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Nicolas
 */
@Entity
public class Benutzer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private byte[] pwhash;
    private byte[] salt;
    private String email;
    @OneToMany(mappedBy = "nutzer")
    private LinkedHashSet<Playlist> playlists;
    @OneToMany(mappedBy = "nutzer")
    private LinkedHashSet<Bewertung> bewertungen;
    
    public Benutzer(){}
    
    public Benutzer(String name, byte[] pwhash, byte[] salt, String email){
        this.id=name;
        this.pwhash=pwhash;
        this.salt=salt;
        this.email=email;
        bewertungen = new LinkedHashSet();
        playlists = new LinkedHashSet();
        
    }

    public void setBewertungen(LinkedHashSet<Bewertung> bewertungen) {
        this.bewertungen = bewertungen;
    }
    
    public String getName() {
        return id;
    }
    
    public LinkedHashSet<Playlist> getPlaylists(){
        return playlists;
    }
    
    public LinkedHashSet<Bewertung> getBewertungen(){
        return bewertungen;
    }
    
    public void addBewertung(Bewertung b){
        bewertungen.add(b);
    }
    
    public void removeBewertung(Bewertung b){
        bewertungen.remove(b);
    }
    
    public void addPlaylist(Playlist p){
        playlists.add(p);
    }
    
    public void removePlaylist(Playlist p){
        playlists.remove(p);
    }
    
    public void setPlaylists(LinkedHashSet<Playlist> playlists){
        this.playlists = playlists;
    }
    
    public Playlist getPlaylist(String name){
        Iterator it = playlists.iterator();
        while(it.hasNext()){
            Playlist p = (Playlist)it.next();
            if(p.getName().equals(name))
                return p;
        }
        return null;
    }
    
    public int BewertungForSong(Song song){
        Iterator it = bewertungen.iterator();
        while(it.hasNext()){
            Bewertung now = (Bewertung)it.next();
            Song songnow = now.getSong();
            if(songnow.equals(song)){
                return now.getBewertung();
            }
        }
        return -1;
    }

    public void setName(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Benutzer)) {
            return false;
        }
        Benutzer other = (Benutzer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
        public byte[] getPwHash(){
        return pwhash;
    }
    
    public byte[] getSalt(){
        return salt;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email=email;
    }


    @Override
    public String toString() {
        return "data.User[ name=" + id + ", email=" + email+ " ]";
    }
    
}
