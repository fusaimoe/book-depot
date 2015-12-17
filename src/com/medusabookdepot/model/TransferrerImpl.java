/**
 * 
 */
package com.medusabookdepot.model;

import java.util.List;
import java.util.Optional;

import com.medusabookdepot.modelInterface.Parcel;
import com.medusabookdepot.modelInterface.Transfer;
import com.medusabookdepot.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public abstract class TransferrerImpl implements Transferrer {

    protected Optional<String> name;
    public static List<Transfer> transfers;
    public TransferrerImpl(String name) {
        this.name=Optional.of(name);
    }
    @Override
    public String getName() {
        return this.name.get();
    }

    @Override
    public abstract void doTransfer(Transferrer transferrer, boolean sender,Parcel parcel);


    @Override
    public void setName(String name) {
        this.name=Optional.ofNullable(name);
    }
}
