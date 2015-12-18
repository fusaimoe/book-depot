/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.sql.Date;
import java.time.LocalDate;

import com.medusabookdepot.model.modelInterface.Customer;
import com.medusabookdepot.model.modelInterface.Parcel;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class CustomerImpl extends TransferrerImpl implements Customer {

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
    public void doTransfer(Transferrer opposite, boolean sender,Parcel parcel) {
        transfers.add(new TransferImpl(sender? this:opposite, sender==false? opposite:this, parcel, Date.valueOf(LocalDate.now())));
    }

}
