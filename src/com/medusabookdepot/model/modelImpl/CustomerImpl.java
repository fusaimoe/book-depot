/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.Customer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public abstract class CustomerImpl extends TransferrerImpl implements Customer,Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9063812037630746920L;
    protected StringProperty address;
    protected StringProperty telephoneNumber;
    protected boolean isHuman;
    protected boolean isLibrary;
    public CustomerImpl(String name,String address, String telephoneNumber) {
        super(name);
        this.address=new SimpleStringProperty(address);
        this.telephoneNumber=new SimpleStringProperty(telephoneNumber);
        this.isDepot=false;
    }
    @Override
    public String getAddress() {
        return this.getAddressProperty().get();
    }

    @Override
    public String getTelephoneNumber() {
        return this.getTelephoneNumberProperty().get();
    }

    @Override
    public StringProperty getAddressProperty() {
        return this.address;
        
    }
    @Override
    public StringProperty getTelephoneNumberProperty() {
        return this.telephoneNumber;
        
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
    abstract public String toString();
}
