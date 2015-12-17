/**
 * 
 */
package com.medusabookdepot.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Optional;

import com.medusabookdepot.modelInterface.Customer;
import com.medusabookdepot.modelInterface.Parcel;
import com.medusabookdepot.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class CustomerImpl extends TransferrerImpl implements Customer {

    private Optional<String> address;
    private Optional<String> telephonenumber;
    
    public CustomerImpl(String name,String address, String telephonenumber) {
        super(name);
        this.address=Optional.ofNullable(address);
        this.telephonenumber=Optional.ofNullable(telephonenumber);
    }
    public CustomerImpl(String name,String address) {
        super(name);
        this.address=Optional.ofNullable(address);
        this.telephonenumber=Optional.empty();
    }
    @Override
    public String getAddress() {
        return this.address.get();
    }

    @Override
    public String getTelephoneNumber() {
        return this.telephonenumber.get();
    }

    @Override
    public void setAddress(String address) {
        this.address=Optional.ofNullable(address);
    }

    @Override
    public void setTelephoneNumber(String telephonenumber) {
        this.telephonenumber=Optional.ofNullable(telephonenumber);
    }
    @Override
    public void doTransfer(Transferrer transferrer, boolean sender,Parcel parcel) {
        transfers.add(new TransferImpl(sender? this:transferrer, sender==false? transferrer:this, parcel, Date.valueOf(LocalDate.now())));
    }

}
