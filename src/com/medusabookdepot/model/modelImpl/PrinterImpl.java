/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.sql.Date;

import com.medusabookdepot.model.modelInterface.Printer;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
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
    public void doTransfer(Transferrer opposite, boolean sender,Map<StandardBook,Integer> books) {
        transfers.add(new TransferImpl(this, opposite,Date.valueOf(LocalDate.now()),this.getNewTrackingNumber(),books));
    }
    public String toString() {
        return this.name+"\n"+this.telephoneNumber+"\n"+this.address;
    }
}
