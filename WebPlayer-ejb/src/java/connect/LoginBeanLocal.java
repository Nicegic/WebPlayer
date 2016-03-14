/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connect;

import javax.ejb.Local;
import data.Benutzer;

/**
 *
 * @author Nicolas
 */
@Local
public interface LoginBeanLocal {
    public boolean checkAccess(String username, String pwhash);
    public boolean register(Benutzer user);
    public Benutzer find(Benutzer user);
    public void remove(Benutzer user);
    public void changeAddress(String email);
}
