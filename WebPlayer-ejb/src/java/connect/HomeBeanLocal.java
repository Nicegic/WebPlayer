/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import javax.ejb.Local;

/**
 *
 * @author Nicolas
 */
@Local
public interface HomeBeanLocal {
    
    public String loadSongInfo(long songid);
    public String loadSuggestNew();
    public String search(String search);
    public String playSong(long songid);
    public String loadPlaylists(String username);
    public String showSongs();
    public String loadPlaylist(long no, String username);
    public void userLoggedIn(String username);
    public void userLoggedOut(String username);
    public void editPlaylist(long songid, long pid, String username, boolean remove);
    public void addPlaylist(String username, String playlistname);
    
}
