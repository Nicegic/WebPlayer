/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author Nicolas
 */
@Entity
public class Bewertung implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Song song;
    @ManyToOne
    private Benutzer nutzer;
    private int bewertung;

    public Bewertung() {
    }
    
    public Bewertung(Song song, Benutzer benutzer, int bewertung){
        this.song=song;
        this.nutzer = benutzer;
        this.bewertung=bewertung;
    }

    public Long getId() {
        return id;
    }
    
    public int getBewertung(){
        return bewertung;
    }
    
    public Benutzer getNutzer(){
        return nutzer;
    }
    
    public Song getSong(){
        return song;
    }
    
    public long getSongID(){
        return song.getId();
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setSong(Song song) {
        this.song = song;
    }

    public void setNutzer(Benutzer nutzer) {
        this.nutzer = nutzer;
    }
    
    public void setBewertung(int bewertungneu){
        bewertung = bewertungneu;
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
        if (!(object instanceof Bewertung)) {
            return false;
        }
        Bewertung other = (Bewertung) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Bewertung[ id=" + id + " ]";
    }
    
}
