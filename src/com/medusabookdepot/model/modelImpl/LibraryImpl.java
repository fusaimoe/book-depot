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

import com.medusabookdepot.model.modelInterface.Library;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
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
    public void doTransfer(Transferrer opposite, boolean sender,Map<StandardBook,Integer> books) {
        List<String>allTrackings=new ArrayList<>();
        for(Transfer trans:transfers){
            allTrackings.add(trans.getTrackingNumber());
        }
        Random rm=new Random();
        String tr=String.valueOf(rm.nextInt(1000000));
        while(!allTrackings.contains(tr)) {
            tr=String.valueOf(rm.nextInt(1000000));
        }
        transfers.add(new TransferImpl(sender? this:opposite, sender==false? opposite:this, Date.valueOf(LocalDate.now()),tr,books));
        
    }
    public String toString() {
        return this.name+"\n"+this.address+"\n"+this.telephoneNumber;
        
    }
}
