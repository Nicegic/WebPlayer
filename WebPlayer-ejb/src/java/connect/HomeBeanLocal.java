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
    
    /**
     *  Lädt zu dem aktuellen Song die Informationen wie Jahr, Titel, Interpret,...
     * @param songid --> Die ID des gerade im Player befindlichen Songs
     * @return String --> HTML-Code für die Songinformationen
     */
    public String loadSongInfo(long songid);
    /**
     * Es wird aus der Datenbank eine neue Empfehlung für den Nutzer ausgewählt.
     * @return String - den HTML-Code für eine neue Empfehlung
     */
    public String loadSuggestNew();
    /**
     * Sucht in der Datenbank nach dem eingegebenen Suchbegriff (Achtung! Case-Sensitive!)
     * @param search --> Der Suchbegriff
     * @return String --> HTML-Code zur Anzeige der Suchresultate
     */
    public String search(String search);
    /**
     * Lädt den Song, der unter der eingegebenen ID in der Datenbank liegt, sodass er abgespielt werden kann
     * @param songid --> Die ID des gewünschten Songs
     * @return String --> Der Pfad zur Songdatei zum Einbinden in das Audio
     */
    public String playSong(long songid);
    /**
     * Lädt alle zu dem User vorhandenen Playlists
     * @param username --> Der Username des Nutzers
     * @return String --> Der HTML-Code zur Anzeige der Playlists inklusive der Songs
     */
    public String loadPlaylists(String username);
    /**
     * Lädt alle momentan in der Datenbank vorhandenen Songs.
     * @return String --> HTML-Code zur Anzeige aller Songs zum Editieren der Playlist
     */
    public String showSongs();
    /**
     * Lädt die gewünschte Playlist, sodass sie editiert werden kann.
     * @param no --> Die wievielte Playlist des Nutzers das ist.
     * @param username --> Der Username des Nutzers.
     * @return String --> Der HTML-Code zur Anzeige der Playlistsongs.
     */
    public String loadPlaylist(long no, String username);
    /**
     * Spezialfall von play(songid). Lädt den Song einer Playlist.
     * @param username --> Username des Nutzers
     * @param playlistno --> Die wievielte Playlist des Nutzers gewünscht ist
     * @param songno --> Der wievielte Song in der Playlist, der gespielt werden soll.
     * @return String --> Der Pfad zum Song zum Einbinden in das Audio.
     */
    public String playPlaylistSong(String username, long playlistno, long songno);
    /**
     * Lädt den nächsten Song der Playlist.
     * @param username --> Username des Nutzers
     * @return String --> Der Pfad zum nächsten Song zum Einbinden in das Audio.
     */
    public String loadNext(String username);
    /**
     * Lädt die Info zum aktuell spielenden Song in einer Playlist (Spezialfall zu loadSongInfo(songid)).
     * @param username --> Username des Nutzers
     * @return String --> Der HTML-Code mit den Informationen zum aktuellen Song.
     */
    public String loadPlaylistSongInfo(String username);
    /**
     * Registriert den neu eingeloggten Benutzer in der Liste der aktiven Benutzer.
     * @param username --> Username des Nutzers
     */
    public void userLoggedIn(String username);
    /**
     * entfernt den Nutzer beim Ausloggen aus der Liste der aktiven Nutzer.
     * @param username --> Username des Nutzers
     */
    public void userLoggedOut(String username);
    /**
     * Fügt den gewünschten Song zur gewünschten Playlist hinzu bzw. entfernt ihn.
     * @param songid --> ID des gewünschten Songs
     * @param pid --> Die wievielte Playlist des Nutzers die gewünschte ist
     * @param username --> Username des Nutzers
     * @param remove --> soll der Song hinzugefügt oder gelöscht werden?
     */
    public void editPlaylist(long songid, long pid, String username, boolean remove);
    /**
     * Fügt dem Nutzer eine neue Playlist hinzu.
     * @param username --> Username des Nutzers
     * @param playlistname --> Name der neuen Playlist
     */
    public void addPlaylist(String username, String playlistname);  
    /**
     * Bewertet den Song mit der angegebenen Bewertung unter dem angegebenen Usernamen.
     * @param songid --> ID des zu bewertenden Songs
     * @param stars --> Anzahl an Sternen, die vergeben werden soll
     * @param username --> Username des Nutzers
     */
    public void reviewSong(long songid, int stars, String username);
}
