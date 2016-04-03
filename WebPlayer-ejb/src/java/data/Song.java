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
    
    public Song(){}
    
    public Song(String name, String interpret, String album, String genre, double dauer, String imagepfad, String songpfad){
        this.name=name;
        this.interpret=interpret;
        this.album=album;
        this.genre=genre;
        this.dauer=dauer;
        this.imagepfad = imagepfad;
        this.songpfad = songpfad;
        playlists = new LinkedHashSet();
        bewertungen = new ArrayList();
    }

    public Long getId() {
        return id;
    }
    
    public String getSongPfad() {
        return songpfad;
    }
    
    public String getImagePfad(){
        return imagepfad;
    }
    
    public void setSongPfad(String songpfad){
        this.songpfad = songpfad;
    }
    
    public void setImagePfad(String imagepfad){
        this.imagepfad = imagepfad;
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

    public LinkedHashSet<Playlist> getPlaylists() {
        return playlists;
    }

    public void setPlaylists(LinkedHashSet<Playlist> playlists) {
        this.playlists = playlists;
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

    public ArrayList<Bewertung> getBewertungen() {
        return bewertungen;
    }

    public void setBewertungen(ArrayList<Bewertung> bewertungen) {
        this.bewertungen = bewertungen;
    }
    
    public void addBewertung(Bewertung b){
        bewertungen.add(b);
        berechneGesamtBewertung();
    }
    
    public void removeBewertung(Bewertung b){
        bewertungen.remove(b);
        berechneGesamtBewertung();
    }

    public int getBewertungGesamt() {
        return bewertungGesamt;
    }

    public void setBewertungGesamt(int bewertungGesamt) {
        this.bewertungGesamt = bewertungGesamt;
    }
    
    public void addToPlaylist(Playlist p){
        playlists.add(p);
    }
    
    public void removeFromPlaylist(Playlist p){
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
    
    private void berechneGesamtBewertung(){
        int sum=0;
        for(int i=0;i<bewertungen.size();i++){
            sum+=bewertungen.get(i).getBewertung();
        }
        if(bewertungen.size()>0)
            bewertungGesamt=(sum/bewertungen.size());
        else
            bewertungGesamt=0;
    }
    
}
