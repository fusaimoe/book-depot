/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.Map;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Customer;
import com.medusabookdepot.model.modelInterface.StandardBook;

/**
 * @author Marcello_Feroce
 *
 */
public class LibraryImpl extends CustomerImpl implements Customer, CanSendTransferrer, Serializable {// strategy

    /**
     * 
     */
    private static final long serialVersionUID = 1297118859791514521L;

    public LibraryImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }
    @Override
    public String toString() {
        return "Libreria: " + this.name.get() + "\n" + this.address.get() + "\n" + this.telephoneNumber.get() + "\n";

    }
    @Override
    public boolean containsBooks(Map<StandardBook, Integer> books) {
        return true;
    }
    @Override
    public boolean isAPrinter() {
        return false;
    }
    @Override
    public boolean isAPerson() {
        return false;
    }
    @Override
    public boolean isALibrary() {
        return true;
    }

}
