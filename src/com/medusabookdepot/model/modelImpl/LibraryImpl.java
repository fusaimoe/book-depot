/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.Library;

/**
 * @author Marcello_Feroce
 *
 */
public class LibraryImpl extends CustomerImpl implements Library, Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 1297118859791514521L;

    public LibraryImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
        this.isHuman=false;
        this.isLibrary=true;
    }
    @Override
    public String toString() {
        return this.name+"\n"+this.address+"\n"+this.telephoneNumber+"\n";
        
    }
}
