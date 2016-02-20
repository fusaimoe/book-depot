/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Customer;
import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
@XmlRootElement(name="library")
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
    /**
     * always return false
     */
    @Override
    public boolean isAPrinter() {
        return false;
    }
    /**
     * always return false
     */
    @Override
    public boolean isAPerson() {
        return false;
    }
    /**
     * always return true
     */
    @Override
    public boolean isALibrary() {
        return true;
    }
    @Override
    public StringProperty getType() {
        return new SimpleStringProperty("Library");
    }

}
