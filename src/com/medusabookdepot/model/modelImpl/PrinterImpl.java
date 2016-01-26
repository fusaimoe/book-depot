/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import com.medusabookdepot.model.modelInterface.Printer;

/**
 * @author Marcello_Feroce
 *
 */
public class PrinterImpl extends CustomerImpl implements Printer {

    public PrinterImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
        this.isHuman=false;
        this.isLibrary=false;
    }
    
    public String toString() {
        return this.name+","+this.telephoneNumber+","+this.address+"CP";
    }
}
