/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.time.LocalDate;
import java.sql.Date;

import com.medusabookdepot.model.modelInterface.Parcel;
import com.medusabookdepot.model.modelInterface.Printer;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class PrinterImpl extends CustomerImpl implements Printer {

    public PrinterImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }

    @Override
    public void doTransfer(Transferrer opposite, boolean sender, Parcel parcel) {
        transfers.add(new TransferImpl(this, opposite, parcel, Date.valueOf(LocalDate.now())));
    }
    public String toString() {
        return this.name+"\n"+this.telephoneNumber+"\n"+this.address;
    }
}
