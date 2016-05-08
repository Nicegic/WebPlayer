/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

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
    private int jahr;
    private String name;
    private String interpret;
    private String album;
    private String genre;
    private double dauer;
    private int bewertungGesamt;
    private String imagepfad;
    private String songpfad;
    @ManyToMany(mappedBy = "songs")
    private LinkedHashSet<Playlist> playlists;
    @OneToMany(mappedBy = "song")
    private ArrayList<Bewertung> bewertungen;

    public Song() {
    }

    /**
     * erstellt einen neuen Song
     *
     * @param name --> Name des Songs
     * @param interpret --> Interpret
     * @param jahr --> Jahr der Veröffentlichung
     * @param album --> Album
     * @param genre --> Genre
     * @param dauer --> Dauer des Songs
     * @param imagepfad --> Pfad zum optionalen Album-/Songcover
     * @param songpfad --> Pfad zur mp3-Datei
     */
    public Song(String name, String interpret, int jahr, String album, String genre, double dauer, String imagepfad, String songpfad) {
        this.name = name;
        this.interpret = interpret;
        this.jahr = jahr;
        this.album = album;
        this.genre = genre;
        this.dauer = dauer;
        this.imagepfad = imagepfad;
        this.songpfad = songpfad;
        playlists = new LinkedHashSet();
        bewertungen = new ArrayList();
    }

    /**
     * gibt die Songid zurück
     *
     * @return
     */
    public Long getId() {
        return id;
    }

    /**
     * gibt den Pfad zur Audiodatei zurück
     *
     * @return
     */
    public String getSongPfad() {
        return songpfad;
    }

    /**
     * gibt den Pfad zum Coverbild zurück
     *
     * @return
     */
    public String getImagePfad() {
        return imagepfad;
    }

    /**
     * setzt den Pfad zur Audiodatei neu
     *
     * @param songpfad
     */
    public void setSongPfad(String songpfad) {
        this.songpfad = songpfad;
    }

    /**
     * setzt den Pfad zum Coverbild neu
     *
     * @param imagepfad
     */
    public void setImagePfad(String imagepfad) {
        this.imagepfad = imagepfad;
    }

    /**
     * gibt das Veröffentlichungsjahr zurück
     *
     * @return
     */
    public int getJahr() {
        return jahr;
    }

    /**
     * setzt das Jahr neu
     *
     * @param jahr
     */
    public void setJahr(int jahr) {
        this.jahr = jahr;
    }

    /**
     * gibt den Namen des Songs zurück
     *
     * @return
     */
    public String getName() {
        return name;
    }

    /**
     * setzt den Namen des Songs neu
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * gibt den Namen des Interpreten zurück
     *
     * @return
     */
    public String getInterpret() {
        return interpret;
    }

    /**
     * setzt den Namen des Interpreten neu
     *
     * @param interpret
     */
    public void setInterpret(String interpret) {
        this.interpret = interpret;
    }

    /**
     * gibt eine Liste mit allen Playlists zurück, in denen der Song vorhanden
     * ist
     *
     * @return
     */
    public LinkedHashSet<Playlist> getPlaylists() {
        return playlists;
    }

    /**
     * setzt alle Playlists, in denen der Song vorhanden ist
     *
     * @param playlists
     */
    public void setPlaylists(LinkedHashSet<Playlist> playlists) {
        this.playlists = playlists;
    }

    /**
     * gibt das Album zurück
     *
     * @return
     */
    public String getAlbum() {
        return album;
    }

    /**
     * setzt das Album neu
     *
     * @param album
     */
    public void setAlbum(String album) {
        this.album = album;
    }

    /**
     * gibt das Genre zurück
     *
     * @return
     */
    public String getGenre() {
        return genre;
    }

    /**
     * setzt das Genre neu
     *
     * @param genre
     */
    public void setGenre(String genre) {
        this.genre = genre;
    }

    /**
     * gibt die Dauer des Songs zurück
     *
     * @return
     */
    public double getDauer() {
        return dauer;
    }

    /**
     * setzt die Dauer neu
     *
     * @param dauer
     */
    public void setDauer(double dauer) {
        this.dauer = dauer;
    }

    /**
     * gibt alle zu diesem Song abgegebenen Bewertungen zurück
     *
     * @return
     */
    public ArrayList<Bewertung> getBewertungen() {
        return bewertungen;
    }

    /**
     * setzt alle Bewertungen des Songs
     *
     * @param bewertungen
     */
    public void setBewertungen(ArrayList<Bewertung> bewertungen) {
        this.bewertungen = bewertungen;
    }

    /**
     * fügt eine neue Bewertung hinzu
     *
     * @param b
     */
    public void addBewertung(Bewertung b) {
        bewertungen.add(b);
        berechneGesamtBewertung();
    }

    /**
     * entfernt eine Bewertung
     *
     * @param b
     */
    public void removeBewertung(Bewertung b) {
        bewertungen.remove(b);
        berechneGesamtBewertung();
    }

    /**
     * berechnet aus allen Bewertungen eine Gesamtbewertung
     *
     * @return
     */
    public int getBewertungGesamt() {
        return bewertungGesamt;
    }

    /**
     * setzt die Gesamtbewertung
     *
     * @param bewertungGesamt
     */
    public void setBewertungGesamt(int bewertungGesamt) {
        this.bewertungGesamt = bewertungGesamt;
    }

    /**
     * fügt einen Verweis auf eine neue Playlist hinzu, in der der Song vorkommt
     *
     * @param p
     */
    public void addToPlaylist(Playlist p) {
        playlists.add(p);
    }

    /**
     * entfernt einen Verweis auf eine Playlist, in der der Song nicht mehr
     * vorkommt
     *
     * @param p
     */
    public void removeFromPlaylist(Playlist p) {
        playlists.remove(p);
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

    private void berechneGesamtBewertung() {
        int sum = 0;
        for (int i = 0; i < bewertungen.size(); i++) {
            sum += bewertungen.get(i).getBewertung();
        }
        if (bewertungen.size() > 0) {
            bewertungGesamt = (sum / bewertungen.size());
        } else {
            bewertungGesamt = 0;
        }
    }

}
