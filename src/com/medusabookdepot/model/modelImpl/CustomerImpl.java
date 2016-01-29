/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.Customer;

/**
 * @author Marcello_Feroce
 *
 */
public abstract class CustomerImpl extends TransferrerImpl implements Customer,Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -9063812037630746920L;
    protected String address;
    protected String telephoneNumber;
    protected boolean isHuman;
    protected boolean isLibrary;
    public CustomerImpl(String name,String address, String telephoneNumber) {
        super(name);
        this.address=address;
        this.telephoneNumber=telephoneNumber;
        this.isDepot=false;
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
    abstract public String toString();
}
