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

/**
 *
 * @author Nicolas
 */
@Entity
public class Song implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String interpret;
    private String album;
    private String genre;
    private double dauer;
    private int bewertungUser;
    private int bewertungGesamt;
    
    public Song(){}
    
    public Song(String name, String interpret, String album, String genre, double dauer){
        this.name=name;
        this.interpret=interpret;
        this.album=album;
        this.genre=genre;
        this.dauer=dauer;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInterpret() {
        return interpret;
    }

    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public double getDauer() {
        return dauer;
    }

    public void setDauer(double dauer) {
        this.dauer = dauer;
    }

    public int getBewertungUser() {
        return bewertungUser;
    }

    public void setBewertungUser(int bewertungUser) {
        this.bewertungUser = bewertungUser;
    }

    public int getBewertungGesamt() {
        return bewertungGesamt;
    }

    public void setBewertungGesamt(int bewertungGesamt) {
        this.bewertungGesamt = bewertungGesamt;
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
        if (!(object instanceof Song)) {
            return false;
        }
        Song other = (Song) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "data.Song[ id=" + id + " ]";
    }
    
}
