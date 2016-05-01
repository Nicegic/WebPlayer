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
public class Playlist implements Serializable{
    @Id @GeneratedValue
    int id;
    @ManyToMany
    LinkedHashSet<Song> songs;
    @ManyToOne
    Benutzer nutzer;
    String name;
    
    public Playlist(){}
    
    public Playlist(Benutzer nutzer, String name){
        this.nutzer=nutzer;
        this.name=name;
        songs = new LinkedHashSet();
    }
    
    //get-Methoden
    
    public int getID(){
        return id;
    }
    
    public long getSongID(int position){
        Iterator it = songs.iterator();
        for(int i=0;i<position;i++){
            it.next();
        }
        Song song = (Song)it.next();
        return song.getId();
    }
    
    public String getSong(int position){
        Iterator it = songs.iterator();
        for(int i=0;i<position;i++){
            it.next();
        }
        Song song = (Song) it.next();
        return song.getName();
    }
    
    public Benutzer getUser(){
        return nutzer;
    }
    public String getName(){
        return name;
    }
    //set-Methoden
    public void setUser(Benutzer nutzer){
        this.nutzer = nutzer;
    }
    public void setName(String name){
        this.name = name;
    }
    
    //List-Arbeiten
    public void addSong(Song song){
        songs.add(song);
        song.addToPlaylist(this);
    }
    
    public void removeSong(Song song){
        songs.remove(song);
        song.removeFromPlaylist(this);
    }
    
    public LinkedHashSet<Song> getSongs(){
        return songs;
    }
    
    public void setSongs(LinkedHashSet<Song> songs){
        this.songs=songs;
    }
    
}
