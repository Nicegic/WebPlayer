/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Nicolas
 */
public class HomeBeanTest {

    public HomeBeanTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Testet die loadSuggest-Methode der HomeBean auf Funktionalität. Wird die
     * Fehlermeldung zurückgegeben, ist der Test fehlgeschlagen.
     *
     * @throws Exception
     */
    @Test
    public void loadSuggest() throws Exception {
        System.out.println("testing LoadSuggestNew()");
        try (EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer()) {
            HomeBeanLocal instance = (HomeBeanLocal) container.getContext().lookup("java:global/classes/HomeBean");
            String expResult = "<p> Fehler beim Laden, keine Daten vorhanden!</p>";
            String result = instance.loadSuggestNew();
            assertFalse("<p> Fehler beim Laden, keine Daten vorhanden!</p>", true);
        }

    }

    /**
     * Testet die loadSongInfo()-Methode der HomeBean. Beim Abfragen der Info zu
     * Song Nr. 1 der DB sollten die Informationen im expResult zurückgegeben
     * werden.
     *
     * @throws Exception
     */
    @Test
    public void testLoadSongInfo() throws Exception {
        System.out.println("testing loadSongInfo(1)");
        long songid = 1;
        try (EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer()) {
            HomeBeanLocal instance = (HomeBeanLocal) container.getContext().lookup("java:global/classes/HomeBean");
            String expResult = "<p>A Piano Piece - Nicolas Mischke - 2009</p><input type=\"hidden\" value=\"1\"/>";
            String result = instance.loadSongInfo(songid);
            assertEquals(expResult, result);
        }
    }

    /**
     * Testet die playSong()-Methode der HomeBean. Unter der angegebenen Song-ID
     * dürfte kein Song liegen, sodass als Ergebnis ein leerer Pfad
     * zurückgegeben werden sollte.
     *
     * @throws Exception
     */
    @Test
    public void testPlaySong() throws Exception {
        System.out.println("testing playSong(10000000)");
        long songid = 10000000;
        try (EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer()) {
            HomeBeanLocal instance = (HomeBeanLocal) container.getContext().lookup("java:global/classes/HomeBean");
            String expResult = "";
            String result = instance.playSong(songid);
            assertEquals(expResult, result);
        }
    }

    /**
     * Testet die Methode loadPlaylists() der HomeBean. Wenn der username leer
     * ist muss als Rückgabe der HTML-Code für den internen Fehler ausgegeben
     * werden.
     *
     * @throws Exception
     */
    @Test
    public void testLoadPlaylists() throws Exception {
        System.out.println("testing LoadPlaylists(\"\")");
        String username = "";
        try (EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer()) {
            HomeBeanLocal instance = (HomeBeanLocal) container.getContext().lookup("java:global/classes/HomeBean");
            String expResult = "<div class=\"playlist\">Es ist ein interner Fehler aufgetreten. Bitte versuche es sp&auml;ter erneut!<button onclick=\"loadPlaylists();\">retry</button></div>";
            String result = instance.loadPlaylists(username);
            assertEquals(expResult, result);
        }
    }
}
