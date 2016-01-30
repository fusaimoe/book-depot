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

    /**
     * 
     */
    private static final long serialVersionUID = -8700734085567092049L;
    public PrinterImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
        this.isHuman=false;
        this.isLibrary=false;
    }
    @Override
    public String toString() {
        return this.name+"\n"+this.telephoneNumber+"\n"+this.address+"\n";
    }
}
