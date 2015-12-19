/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.util.List;

import com.medusabookdepot.model.modelInterface.Parcel;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 * @param <X>
 *
 */
public abstract class TransferrerImpl implements Transferrer{

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
