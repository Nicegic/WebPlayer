/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashSet;

/**
 *
 * @author leonmelnik
 */
@Entity
public class Playlist implements Serializable {

    @Id
    @GeneratedValue
    int id;
    @ManyToMany
    LinkedHashSet<Song> songs;
    @ManyToOne
    Benutzer nutzer;
    String name;

    public Playlist() {
    }

    /**
     * erstellt eine neue Playlist
     *
     * @param nutzer --> Username des Nutzers
     * @param name --> Name der Playlist
     */
    public Playlist(Benutzer nutzer, String name) {
        this.nutzer = nutzer;
        this.name = name;
        songs = new LinkedHashSet();
    }

    /**
     * gibt die ID der Playlist zurück
     *
     * @return
     */
    public int getID() {
        return id;
    }

    /**
     * gibt die ID zum Song an der gewünschten Position zurück
     *
     * @param position
     * @return long --> Songid
     */
    public long getSongID(int position) {
        Iterator it = songs.iterator();
        for (int i = 0; i < position; i++) {
            it.next();
        }
        Song song = (Song) it.next();
        return song.getId();
    }

    /**
     * gibt den Namen des Songs an der gewünschten Position zurück
     *
     * @param position
     * @return String --> der Name des Songs
     */
    public String getSong(int position) {
        Iterator it = songs.iterator();
        for (int i = 0; i < position; i++) {
            it.next();
        }
        Song song = (Song) it.next();
        return song.getName();
    }

    /**
     * gibt den User zurück, der die Playlist erstellt hat
     *
     * @return
     */
    public Benutzer getUser() {
        return nutzer;
    }

    /**
     * gibt den Namen der Playlist zurück
     *
     * @return String --> Name der Playlist
     */
    public String getName() {
        return name;
    }

    /**
     * setzt den Benutzer neu
     *
     * @param nutzer
     */
    public void setUser(Benutzer nutzer) {
        this.nutzer = nutzer;
    }

    /**
     * setzt den Namen neu
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * fügt der Playlist einen Song hinzu
     *
     * @param song
     */
    public void addSong(Song song) {
        songs.add(song);
        song.addToPlaylist(this);
    }

    /**
     * entfernt einen Song aus der Playlist
     *
     * @param song
     */
    public void removeSong(Song song) {
        songs.remove(song);
        song.removeFromPlaylist(this);
    }

    /**
     * gibt alle Songs in der Playlist zurück
     *
     * @return LinkedHashSet<Song> --> alle Songs in der Playlist
     */
    public LinkedHashSet<Song> getSongs() {
        return songs;
    }

    /**
     * setzt alle Songs der Playlist
     *
     * @param songs
     */
    public void setSongs(LinkedHashSet<Song> songs) {
        this.songs = songs;
    }

}
