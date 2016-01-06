/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.sql.Date;
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
    public static ArrayList<Transfer> transfers;//List that contains all transfers alive
    
    public TransferrerImpl(String name) {
        this.name=name;
    }
    @Override
    public String getName() {
        return this.name;
    }
    
    
    public static List<? extends Transfer>getAllTransfers(){
        return TransferrerImpl.transfers;
        
    }
    public static void addTransfer(Transfer transfer) {
        TransferrerImpl.transfers.add(transfer);
        
    }
    public static void addTransfer(Transferrer sender,Transferrer receiver, Date leavingDate,Map<StandardBook,Integer> books) {
        TransferrerImpl.transfers.add(new TransferImpl(sender, receiver, leavingDate, books));
        
    }
    @Override
    public void setName(String name) {
        this.name=name;
    }
}
