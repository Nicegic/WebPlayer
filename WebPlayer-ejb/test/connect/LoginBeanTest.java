/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package connect;

import data.Benutzer;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
*
* @author Patricia
*/
public class LoginBeanTest {
    
    public LoginBeanTest() {
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
     * Test of checkAccess method, of class LoginBean.
     */
    @Test
    public void testCheckAccess() throws Exception {
        System.out.println("check Access");
        String username = "pata3110";
        byte[] pwhash = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        LoginBeanLocal instance = (LoginBeanLocal)container.getContext().lookup("java:global/classes/LoginBean");
        boolean expResult = false;
        boolean result = instance.checkAccess(username, pwhash);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSalt method, of class LoginBean.
     */
    @Test
    public void testGetSalt() throws Exception {
        System.out.println("getSalt");
        String username = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        LoginBeanLocal instance = (LoginBeanLocal)container.getContext().lookup("java:global/classes/LoginBean");
        byte[] expResult = null;
        byte[] result = instance.getSalt(username);
        assertArrayEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of register method, of class LoginBean.
     */
    @Test
    public void testRegister() throws Exception {
        System.out.println("register");
        String username = "";
        byte[] pwhash = null;
        byte[] salt = null;
        String email = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        LoginBeanLocal instance = (LoginBeanLocal)container.getContext().lookup("java:global/classes/LoginBean");
        instance.register(username, pwhash, salt, email);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of find method, of class LoginBean.
     */
    @Test
    public void testFind() throws Exception {
        System.out.println("find");
        Benutzer user = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        LoginBeanLocal instance = (LoginBeanLocal)container.getContext().lookup("java:global/classes/LoginBean");
        Benutzer expResult = null;
        Benutzer result = instance.find(user);
        assertEquals(expResult, result);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of remove method, of class LoginBean.
     */
    @Test
    public void testRemove() throws Exception {
        System.out.println("remove");
        Benutzer user = null;
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        LoginBeanLocal instance = (LoginBeanLocal)container.getContext().lookup("java:global/classes/LoginBean");
        instance.remove(user);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of changeAddress method, of class LoginBean.
     */
    @Test
    public void testChangeAddress() throws Exception {
        System.out.println("changeAddress");
        String email = "";
        EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        LoginBeanLocal instance = (LoginBeanLocal)container.getContext().lookup("java:global/classes/LoginBean");
        instance.changeAddress(email);
        container.close();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
