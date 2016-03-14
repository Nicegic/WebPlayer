/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import javax.ejb.Stateless;
import data.Benutzer;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nicolas
 */
@Stateless
public class LoginBean implements LoginBeanLocal {
    @PersistenceContext(unitName="WebPlayer-ejbPU") EntityManager em;
    
    @Override
    public boolean checkAccess(String username, String pwhash){
        Benutzer actUser = em.find(Benutzer.class, username);
        if(pwhash.equals(actUser.getPwHash()))
            return true;
        else
            return false;
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public boolean register(Benutzer user) {
        Benutzer actUser = user;
        if(!actUser.getName().equals(user.getName())){
            em.persist(actUser);
            return true;
        }else
            return false;
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
