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
public class PersonImpl extends CustomerImpl implements Customer, Serializable {// strategy

    /**
     * 
     */
    private static final long serialVersionUID = -1881387138018701672L;
    public PersonImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }
    @Override
    public String toString() {
        return "Persona: " + this.name.get() + "\n" + this.address.get() + "\n" + this.telephoneNumber.get() + "\n";

    }
    /**
     * always return false
     */
    @Override
    public boolean isAPrinter() {
        return false;
    }
    /**
     * always return true
     */
    @Override
    public boolean isAPerson() {
        return true;
    }
    /**
     * always return false
     */
    @Override
    public boolean isALibrary() {
        return false;
    }

}
