/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.Customer;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public abstract class CustomerImpl extends TransferrerImpl implements Customer, Serializable {// template
                                                                                              // method

    /**
     * 
     */
    private static final long serialVersionUID = -9063812037630746920L;
    protected StringProperty address;
    protected StringProperty telephoneNumber;
    public CustomerImpl(String name, String address, String telephoneNumber) {
        super(name);
        this.address = new SimpleStringProperty(address);
        this.telephoneNumber = new SimpleStringProperty(telephoneNumber);
    }
    @Override
    public String getAddress() {
        String addr=new String(this.address.get());//copia difensiva
        return addr;
    }

    @Override
    public String getTelephoneNumber() {
        String tel=new String(this.telephoneNumber.get());//copia difensiva
        return tel;
    }
    @Override
    public void setAddress(String address) {
        this.address.set(address);
    }

    @Override
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber.set(telephoneNumber);
    }
    @Override
    public boolean isADepot() {
        return false;
    }
    @Override
    public abstract boolean isAPrinter();
    @Override
    public abstract boolean isAPerson();
    @Override
    public abstract boolean isALibrary();
    @Override
    public StringProperty AddressProperty() {
        StringProperty addr=new SimpleStringProperty(this.address.get());//copia difensiva
        return addr;
    }
    @Override
    public StringProperty TelephoneNumberProperty() {
        StringProperty tel=new SimpleStringProperty(this.telephoneNumber.get());//copia difensiva
        return tel;
    }
    @Override
    abstract public String toString();
}
