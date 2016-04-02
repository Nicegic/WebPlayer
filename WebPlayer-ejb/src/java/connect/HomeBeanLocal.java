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
    
    public String playSong(int songID);
    public String[] loadSuggest();
    public void playSuggest(int songID);
    public void loadPlaylist();
    public void loadPlaylist(int id);
    public void playPlaylist(int id);
    public void editPlaylist(int id, boolean remove);
    
}
