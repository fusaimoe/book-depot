/**
 * 
 */
package com.medusabookdepot.modelImpl;

import java.util.List;
import com.medusabookdepot.modelInterface.Parcel;
import com.medusabookdepot.modelInterface.Transfer;
import com.medusabookdepot.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public abstract class TransferrerImpl implements Transferrer {

    protected String name;
    protected static List<Transfer> transfers;//List that contains all transfers alive
    
    public TransferrerImpl(String name) {
        this.name=name;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public abstract void doTransfer(Transferrer transferrer, boolean sender,Parcel parcel);


    @Override
    public void setName(String name) {
        this.name=name;
    }
}
