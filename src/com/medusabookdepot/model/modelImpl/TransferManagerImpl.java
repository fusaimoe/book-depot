package com.medusabookdepot.model.modelImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.TransferManager;
import com.medusabookdepot.model.modelInterface.Transferrer;

public class TransferManagerImpl implements TransferManager {

    private static TransferManager sing=null;//singleton
    private List<Transfer> transfers;//List that contains all transfers alive
    
    private TransferManagerImpl() {
        //costruttore vuoto e privato!
        this.transfers=getTransfersFromFile("trasferimenti.txt");
    }
    
    public static TransferManager getInstanceOfTransferManger() {
        if(sing==null) {
            return new TransferManagerImpl();
        }
        else {
            return TransferManagerImpl.sing;
        }
    }

    @Override
    public List<? extends Transfer> getAllTransfers() {
        return this.transfers;
    }

    @Override
    public void addTransfer(Transfer transfer) {
        this.transfers.add(transfer);
        this.writeTransferOnFile("trasferimenti.txt",transfer);

    }
    private String getFilePath(String fileName){
        String filepath = System.getProperty("user.home")+System.getProperty("file.separator")+ fileName;
        return filepath;
    }
    private void writeTransferOnFile(String fileName,Transfer transfer) {
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
            e.printStackTrace();
        }
    }
    @Override
    public void addTransfer(Transferrer sender, Transferrer receiver, Date leavingDate, Map<StandardBook, Integer> books) {
        Transfer trans=new TransferImpl(sender, receiver, leavingDate, books);
        this.transfers.add(trans);
        this.writeTransferOnFile("trasferimenti.txt",trans);

    }
    private Transferrer convertStringToTransferrer(String string){
        char c=string.charAt(0);
        String name="";
        Transferrer trans = null;
        int x=0;
        
        while(!Character.valueOf(c).equals(',')){
            x++;
            name=name.concat(String.valueOf(c));
            c=string.charAt(x);
        }
        x++;
        c=string.charAt(x);
        if(Character.valueOf(c).equals('{')) {
            String books="";
            trans=new DepotImpl(name, null);
            x++;
            c=string.charAt(x);
            while(!Character.valueOf(c).equals('}')){
                x++;
                books=books.concat(String.valueOf(c));
                c=string.charAt(x);
            }
            trans=new DepotImpl(name, convertStringToBooks(books));
        }
        else{
            String address="";
            String telephone="";
            while(!Character.valueOf(c).equals(',')) {
                x++;
                address=address.concat(String.valueOf(c));
                c=string.charAt(x);
            }
            x++;
            c=string.charAt(x);
            while(!Character.valueOf(c).equals('C')) {
                x++;
                telephone=telephone.concat(String.valueOf(c));
                c=string.charAt(x);
            }
            x++;
            c=string.charAt(x);
            if(Character.valueOf(c).equals('P')){
                trans=new PrinterImpl(name, address, telephone);
            }
            if(Character.valueOf(c).equals('H')){
                trans=new PersonImpl(name, address, telephone);
            }
            if(Character.valueOf(c).equals('L')){
                trans=new LibraryImpl(name, address, telephone);
            }
        }
        return trans;
        
    }
    
    private Map<StandardBook,Integer> convertStringToBooks(String string) {
        char c=string.charAt(0);
        Map<StandardBook,Integer> mappa=new HashMap<>();
        
        int nBooks=0;
        for(int y=0;y<string.length();y++) {
            c=Character.valueOf(string.charAt(0));
            if(Character.valueOf(c).equals(',')){
                nBooks++;
            }
        }

        int x=0;
        String bookString;
        String intString;
        boolean nowSym=false;
        boolean nowInt=false;
        for(int y=0;y<nBooks;y++) {
            c=string.charAt(x);
            bookString="";
            intString="";
            while(c!=','){
                if(nowSym)nowInt=true;
                if(Character.valueOf(c).equals('>')) nowSym=true;
                if(!nowSym) {
                    bookString=bookString.concat(String.valueOf(c));
                }
                if(nowInt){
                    intString=intString.concat(String.valueOf(c));
                }
                x++;
                c=Character.valueOf(string.charAt(x));
            }
            x++;
            mappa.put(StandardBookImpl.getStandardBookFromString(bookString), Integer.parseInt(intString));
        }
        return mappa;
        
    }
    
    @Override
    public List<Transfer> getTransfersFromFile(String filePath) {
        List<Transfer> transes=new ArrayList<>();
        if(!Files.exists(Paths.get(filePath))) {
            try {
                Files.createFile(Paths.get(filePath));
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try {
            FileReader fr=new FileReader(filePath);
            BufferedReader br=new BufferedReader(fr);
            try {
                String str=br.readLine();
                while(str!=null){
                    if(str.contains("*")) {
                        Transfer trans=new TransferImpl(null,null, null,null);
                        trans.setTrackingNumber(br.readLine());
                        trans.setBooks(this.convertStringToBooks(br.readLine()));
                        trans.setLeavingDate(Date.valueOf(br.readLine()));
                        trans.setSender(this.convertStringToTransferrer(br.readLine()));
                        trans.setReceiver(this.convertStringToTransferrer(br.readLine()));
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
    public static void main(String ...strings) {
        Map<StandardBook, Integer>mm=new HashMap<>();
        mm.put(new StandardBookImpl("iiis", "divina commedia", "dante"), Integer.valueOf(5));
        Map<StandardBook, Integer>mmq=new HashMap<>();
        mmq.put(new StandardBookImpl("iiiees", "decamerone", "boccaccio"), Integer.valueOf(7));
        TransferManagerImpl.getInstanceOfTransferManger().addTransfer(new TransferImpl(new DepotImpl("mmm",mm ), new PersonImpl("bocc", "via san gavino 4", "333 334 422"), new Date(2012, 10, 27),mmq));
    }

}
