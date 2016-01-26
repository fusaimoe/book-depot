/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import com.medusabookdepot.model.modelInterface.Library;

/**
 * @author Marcello_Feroce
 *
 */
public class LibraryImpl extends CustomerImpl implements Library{

    public LibraryImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
        this.isHuman=false;
        this.isLibrary=true;
    }
    
    public String toString() {
        return this.name+","+this.address+","+this.telephoneNumber+"CL";
        
    }
}
