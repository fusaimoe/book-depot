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
@XmlRootElement(name="printer")
public class PrinterImpl extends CustomerImpl implements Customer, CanSendTransferrer, Serializable {// strategy

    /**
     * 
     */
    private static final long serialVersionUID = -8700734085567092049L;
    public PrinterImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }
    @Override
    public String toString() {
        return "Stampa: " + this.name.get() + "\n" + this.telephoneNumber.get() + "\n" + this.address.get() + "\n";
    }
    @Override
    public boolean containsBooks(Map<StandardBook, Integer> books) {
        return true;
    }
    /**
     * always return true
     */
    @Override
    public boolean isAPrinter() {
        return true;
    }
    /**
     * always return false
     */
    @Override
    public boolean isAPerson() {
        return false;
    }
    /**
     * always return false
     */
    @Override
    public boolean isALibrary() {
        return false;
    }
    @Override
    public StringProperty getType() {
        return new SimpleStringProperty("Printer");
    }
}
