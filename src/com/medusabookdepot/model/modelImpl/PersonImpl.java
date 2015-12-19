/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.sql.Date;
import java.time.LocalDate;

import com.medusabookdepot.model.modelInterface.Parcel;
import com.medusabookdepot.model.modelInterface.Person;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class PersonImpl extends CustomerImpl implements Person {

    public PersonImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }
    /*can a person just receive?*/
    @Override
    public void doTransfer(Transferrer opposite, boolean sender, Parcel parcel) {
        transfers.add(new TransferImpl(opposite, this, parcel, Date.valueOf(LocalDate.now())));
        
    }
    
}
