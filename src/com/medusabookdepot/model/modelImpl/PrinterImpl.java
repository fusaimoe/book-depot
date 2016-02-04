/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.Map;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Customer;
import com.medusabookdepot.model.modelInterface.Printer;
import com.medusabookdepot.model.modelInterface.StandardBook;

/**
 * @author Marcello_Feroce
 *
 */
public class PrinterImpl extends CustomerImpl implements Printer,Customer,CanSendTransferrer,Serializable {

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
    @Override
    public boolean contains(Map<StandardBook, Integer> books) {
        return true;
    }
}
