/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.Customer;

/**
 * @author Marcello_Feroce
 *
 */
public class PersonImpl extends CustomerImpl implements Customer,Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -1881387138018701672L;
    public PersonImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
        this.isHuman=true;
        this.isLibrary=false;
    }
    @Override
    public String toString() {
        return "Persona: "+this.name+"\n"+this.address+"\n"+this.telephoneNumber+"\n";
        
    }
    @Override
    public boolean isAPrinter() {
        return false;
    }
    @Override
    public boolean isAPerson() {
        return true;
    }
    @Override
    public boolean isALibrary() {
        return false;
    }
    
}
