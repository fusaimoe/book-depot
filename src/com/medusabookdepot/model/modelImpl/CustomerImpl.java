/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.Customer;

import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public abstract class CustomerImpl extends TransferrerImpl implements Customer,Serializable {//template method

    /**
     * 
     */
    private static final long serialVersionUID = -9063812037630746920L;
    protected String address;
    protected String telephoneNumber;
    public CustomerImpl(String name,String address, String telephoneNumber) {
        super(name);
        this.address=address;
        this.telephoneNumber=telephoneNumber;
    }
    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public String getTelephoneNumber() {
        return this.telephoneNumber;
    }
    @Override
    public void setAddress(String address) {
        this.address=address;
    }

    @Override
    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber=telephoneNumber;
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
    public StringProperty getAddressProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public StringProperty getTelephoneNumberProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    abstract public String toString();
}
