/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.time.LocalDate;
import java.sql.Date;

import com.medusabookdepot.model.modelInterface.Library;
import com.medusabookdepot.model.modelInterface.Parcel;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class LibraryImpl extends CustomerImpl implements Library{

    public LibraryImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }

    @Override
    public void doTransfer(Transferrer opposite, boolean sender, Parcel parcel) {
        transfers.add(new TransferImpl(sender? this:opposite, sender==false? opposite:this, parcel, Date.valueOf(LocalDate.now())));
        
    }
    public String toString() {
        return this.name+"\n"+this.address+"\n"+this.telephoneNumber;
        
    }
}
