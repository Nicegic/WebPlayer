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

    /**
     * erstellt eine neue Bewertung
     *
     * @param song --> Song, zu dem die Bewertung abgegeben wurde
     * @param benutzer --> Benutzer, der die Bewertung abgegeben hat
     * @param bewertung --> 1-5
     */
    public Bewertung(Song song, Benutzer benutzer, int bewertung) {
        this.song = song;
        this.nutzer = benutzer;
        this.bewertung = bewertung;
    }

    /**
     * gibt die ID der Bewertung zurück
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * gibt die Bewertung zurück (1-5)
     *
     * @return
     */
    public int getBewertung() {
        return bewertung;
    }

    /**
     * gibt den benutzer zurück, der die Bewertung abgegeben hat
     *
     * @return
     */
    public Benutzer getNutzer() {
        return nutzer;
    }

    /**
     * gibt den Song zurück, zu dem die Bewertung abgegeben wurde
     *
     * @return
     */
    public Song getSong() {
        return song;
    }

    /**
     * gibt die ID des Songs zurück
     *
     * @return
     */
    public long getSongID() {
        return song.getId();
    }

    /**
     * setzt die ID
     *
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * setzt den Song
     *
     * @param song
     */
    public void setSong(Song song) {
        this.song = song;
    }

    /**
     * setzt den User
     *
     * @param nutzer
     */
    public void setNutzer(Benutzer nutzer) {
        this.nutzer = nutzer;
    }

    /**
     * setzt den Wert der Bewertung
     *
     * @param bewertungneu
     */
    public void setBewertung(int bewertungneu) {
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
