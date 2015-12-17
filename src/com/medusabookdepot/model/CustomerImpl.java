/**
 * 
 */
package com.medusabookdepot.model;

import java.sql.Date;
import java.time.LocalDate;
import com.medusabookdepot.modelInterface.Customer;
import com.medusabookdepot.modelInterface.Parcel;
import com.medusabookdepot.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class CustomerImpl extends TransferrerImpl implements Customer {

    private String address;
    private String telephoneNumber;
    
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
