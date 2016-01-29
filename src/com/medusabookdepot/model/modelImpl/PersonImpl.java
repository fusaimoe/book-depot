/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.Person;

/**
 * @author Marcello_Feroce
 *
 */
public class PersonImpl extends CustomerImpl implements Person , Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1881387138018701672L;
    public PersonImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
        this.isHuman=true;
        this.isLibrary=false;
    }
    public String toString() {
        return this.name+","+this.address+","+this.telephoneNumber+"CH";
        
    }
    
}
