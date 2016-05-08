/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import javax.ejb.Local;
import data.Benutzer;
import javax.persistence.EntityExistsException;

/**
 *
 * @author Nicolas
 */
@Local
public interface LoginBeanLocal {
    public boolean checkAccess(String username, byte[] pwhash);
    public void register(String username, byte[] pwhash, byte[] salt, String email) throws EntityExistsException;
    public byte[] getSalt(String username);
    public Benutzer find(Benutzer user);
    public void remove(Benutzer user);
    public void changeAddress(String email);
}
