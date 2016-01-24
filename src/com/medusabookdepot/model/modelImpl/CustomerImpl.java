/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.util.Map;

import com.medusabookdepot.model.modelInterface.Customer;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class CustomerImpl extends TransferrerImpl implements Customer {

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
}
