/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import javax.ejb.Stateless;
import data.Benutzer;
import java.util.Arrays;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nicolas
 */
@Stateless
public class LoginBean implements LoginBeanLocal {

    @PersistenceContext(unitName = "WebPlayer-ejbPU")
    EntityManager em;

    @Override
    public boolean checkAccess(String username, byte[] pwhash) {
        Benutzer actUser = em.find(Benutzer.class, username);
        if (actUser == null) {
            return false;
        }
        return Arrays.equals(pwhash, actUser.getPwHash());
    }

    @Override

    public byte[] getSalt(String username) {
        Benutzer actUser = em.find(Benutzer.class, username);
        if (actUser == null) {
            return null;
        } else {
            return actUser.getSalt();
        }
    }

    @Override
    public void register(String username, byte[] pwhash, byte[] salt, String email) throws EntityExistsException {
        Benutzer actUser = new Benutzer(username, pwhash, salt, email);
        em.persist(actUser);
    }

    @Override
    public Benutzer find(Benutzer user) {
        return em.find(Benutzer.class, user);
    }

    @Override
    public void remove(Benutzer user) {
        em.remove(user);
    }

    @Override
    public void changeAddress(String email) {
        Benutzer user = em.find(Benutzer.class, email);
        user.setEmail(email);
        em.merge(user);
    }
}
