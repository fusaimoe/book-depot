/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
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
    public abstract void doTransfer(Transferrer transferrer, boolean sender, Map<StandardBook,Integer> books);


    @Override
    public void setName(String name) {
        this.name=name;
    }
    public String getNewTrackingNumber() {
        List<String>allTrackings=new ArrayList<>();
        for(Transfer trans:transfers){
            allTrackings.add(trans.getTrackingNumber());
        }
        Random rm=new Random();
        String tr=String.valueOf(rm.nextInt(1000000));
        while(!allTrackings.contains(tr)) {
            tr=String.valueOf(rm.nextInt(1000000));
        }
        return tr;
    }
}
