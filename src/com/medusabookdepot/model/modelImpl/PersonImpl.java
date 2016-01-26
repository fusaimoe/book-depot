/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import com.medusabookdepot.model.modelInterface.Person;

/**
 * @author Marcello_Feroce
 *
 */
public class PersonImpl extends CustomerImpl implements Person {

    public PersonImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
        this.isHuman=true;
        this.isLibrary=false;
    }
    public String toString() {
        return this.name+","+this.address+","+this.telephoneNumber+"CH";
        
    }
    
}
