/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
public class TransferrerImpl implements Transferrer{

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
    private static String getFilePath(String fileName){
        String filepath = System.getProperty("user.home")+System.getProperty("file.separator")+ fileName;
        return filepath;
    }
    private static void writeTransferOnFile(String fileName,Transfer transfer) {
        try {
            FileWriter fw=new FileWriter(getFilePath(fileName), true);
            BufferedWriter bw=new BufferedWriter(fw);
            bw.write("**********");
            bw.newLine();
            bw.write(transfer.getTrackingNumber());
            bw.newLine();
            bw.write(transfer.getBooksAsString());
            bw.newLine();
            bw.write(transfer.getQuantity());
            bw.newLine();
            bw.write(transfer.getTotalPrice());
            bw.newLine();
            bw.write(transfer.getLeavingDate().toString());
            bw.newLine();
            bw.write(transfer.getSender().toString());
            bw.newLine();
            bw.write(transfer.getReceiver().toString());
            bw.newLine();
            bw.close();
            
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
