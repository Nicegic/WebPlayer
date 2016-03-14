/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Nicolas
 */
@Entity
public class Benutzer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    private String id;
    private String pwhash;
    private String salt;
    private String email;    
    
    public Benutzer(){}
    
    public Benutzer(String name, String pwhash, String salt, String email){
        this.id=name;
        this.pwhash=pwhash;
        this.salt=salt;
        this.email=email;
    }
    public String getName() {
        return id;
    }

    public void setName(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Benutzer)) {
            return false;
        }
        Benutzer other = (Benutzer) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
    
        public String getPwHash(){
        return pwhash;
    }
    
    public String getSalt(){
        return salt;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email=email;
    }


    @Override
    public String toString() {
        return "data.User[ name=" + id + ", email=" + email+ " ]";
    }
    
}
