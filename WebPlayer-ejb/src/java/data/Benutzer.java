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

    public Benutzer() {
    }

    /**
     * erstellt einen neuen Benutzer
     *
     * @param name --> username
     * @param pwhash --> der Hash-Wert des Passworts
     * @param salt --> der bei der Hashung beteiligte salt
     * @param email --> die E-Mail-Adresse des Nutzers
     */
    public Benutzer(String name, byte[] pwhash, byte[] salt, String email) {
        this.id = name;
        this.pwhash = pwhash;
        this.salt = salt;
        this.email = email;
        bewertungen = new LinkedHashSet();
        playlists = new LinkedHashSet();

    }

    /**
     * Setzt alle Bewertungen des Users
     *
     * @param bewertungen --> LinkedHashSet mit allen bewertungen, die
     * hinzugefügt werden sollen
     */
    public void setBewertungen(LinkedHashSet<Bewertung> bewertungen) {
        this.bewertungen = bewertungen;
    }

    /**
     * gibt den Namen des Benutzers zurück
     *
     * @return String --> username
     */
    public String getName() {
        return id;
    }

    /**
     * Gibt alle Playlists des Benutzers zurück
     *
     * @return LinkedHashSet<Playlist> --> alle Playlists des Benutzers
     */
    public LinkedHashSet<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * gibt alle Bewertungen des Benutzers zurück
     *
     * @return LinkedHashSet<Bewertung> --> alle Bewertungen des Benutzers
     */
    public LinkedHashSet<Bewertung> getBewertungen() {
        return bewertungen;
    }

    /**
     * fügt eine neue Bewertung hinzu
     *
     * @param b --> die hinzuzufügende bewertung
     */
    public void addBewertung(Bewertung b) {
        bewertungen.add(b);
    }

    /**
     * löscht die gewünschte Bewertung aus der Liste
     *
     * @param b --> die zu löschende Bewertung
     */
    public void removeBewertung(Bewertung b) {
        bewertungen.remove(b);
    }

    /**
     * fügt eine neue Playlist hinzu
     *
     * @param p --> die hinzuzufügende Playlist
     */
    public void addPlaylist(Playlist p) {
        playlists.add(p);
    }

    /**
     * entfernt die gewünschte Playlist aus der Liste
     *
     * @param p --> die zu löschende Playlist
     */
    public void removePlaylist(Playlist p) {
        playlists.remove(p);
    }

    /**
     * setzt alle Playlists des Benutzers
     *
     * @param playlists --> LinkedHashSet mit allen Playlists
     */
    public void setPlaylists(LinkedHashSet<Playlist> playlists) {
        this.playlists = playlists;
    }

    /**
     * gibt die Playlist unter dem angegebenen Namen zurück
     *
     * @param name --> Name der playlist
     * @return Playlist oder null, wenn die gesuchte Playlist nicht gefunden
     * wurde
     */
    public Playlist getPlaylist(String name) {
        Iterator it = playlists.iterator();
        while (it.hasNext()) {
            Playlist p = (Playlist) it.next();
            if (p.getName().equals(name)) {
                return p;
            }
        }
        return null;
    }

    /**
     * gibt die Bewertung für den speziellen Song zurück
     *
     * @param song
     * @return int --> die Bewertung (1-5), 0 wenn noch keine Bewertung
     * existiert
     */
    public int BewertungForSong(Song song) {
        Iterator it = bewertungen.iterator();
        while (it.hasNext()) {
            Bewertung now = (Bewertung) it.next();
            Song songnow = now.getSong();
            if (songnow.equals(song)) {
                return now.getBewertung();
            }
        }
        return 0;
    }

    /**
     * setzt den usernamen des Benutzers neu
     *
     * @param id --> der neue gewünschte Username
     */
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

    /**
     * gibt den Hash des Passworts zurück
     *
     * @return
     */
    public byte[] getPwHash() {
        return pwhash;
    }

    /**
     * gibt den Salt zurück
     *
     * @return
     */
    public byte[] getSalt() {
        return salt;
    }

    /**
     * gibt die E-Mail-Adresse zurück
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * setzt die E-Mail-Adresse neu
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "data.User[ name=" + id + ", email=" + email + " ]";
    }

}
