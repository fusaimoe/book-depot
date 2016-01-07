/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
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
        TransferrerImpl.writeTransferOnFile("trasferimenti.txt",transfer);
        
    }
    public static List<Transfer> getTransfersFromFile(String filePath){
        List<Transfer> transes=new ArrayList<>();
        try {
            FileReader fr=new FileReader(filePath);
            BufferedReader br=new BufferedReader(fr);
            try {
                String str=br.readLine();
                while(str!=null){
                    if(str.contains("*")) {
                        Transfer trans=new TransferImpl(null,null, null,null);
                        trans.setTrackingNumber(br.readLine());
                        trans.setBooks(TransferrerImpl.convertStringToBooks(br.readLine()));
                        trans.setLeavingDate(Date.valueOf(br.readLine()));
                        trans.setSender(TransferrerImpl.convertStringToTransferrer(br.readLine()));
                        trans.setSender(TransferrerImpl.convertStringToTransferrer(br.readLine()));
                        transes.add(trans);
                    }
                    str=br.readLine();
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
        return transes;
    }
    private static Transferrer convertStringToTransferrer(String string){
        return null;
        
    }
    private static Map<StandardBook,Integer> convertStringToBooks(String string) {
        return null;
        
    }
    public static void addTransfer(Transferrer sender,Transferrer receiver, Date leavingDate,Map<StandardBook,Integer> books) {
        Transfer trans=new TransferImpl(sender, receiver, leavingDate, books);
        TransferrerImpl.transfers.add(trans);
        TransferrerImpl.writeTransferOnFile("trasferimenti.txt",trans);
        
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
