package com.medusabookdepot.model.modelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

public class TransferManagerImpl2 implements TransferManager {
    private static TransferManager sing=null;//singleton
    private List<Transfer> transfers;//List that contains all transfers alive
    
    private TransferManagerImpl2() {
        //costruttore privato!
        this.transfers=getTransfersFromFile("trasferimenti.txt");
    }
    
    public static TransferManager getInstanceOfTransferManger() {
        if(sing==null) {
            System.out.println("prima volta");
            sing=new TransferManagerImpl2();
            return sing;
        }
        else {
            System.out.println("c'è gia stata una prima volta");
            return TransferManagerImpl2.sing;
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

    private void writeTransferOnFile(String fileName, Transfer transfer) {
        try {
            List<Transfer> trans=getTransfersFromFile(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(fileName)));
            trans.add(transfer);
            oos.writeObject(trans);
            oos.close();
            System.out.println("scritto su file");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private String getFilePath(String string) {
        String filepath = System.getProperty("user.home")+System.getProperty("file.separator")+ string;
        return filepath;
    }

    @Override
    public void addTransfer(Transferrer sender, Transferrer receiver, Date leavingDate,
            Map<StandardBook, Integer> books) {
        
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Transfer> getTransfersFromFile(String fileName) {
        List<Transfer> trans=new ArrayList<>();
        File f = new File(getFilePath(fileName));
        if(!f.exists()) {
            System.out.println("file non esistente in getTransfersFromFile");
            return new ArrayList<Transfer>();
        }
        else {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(getFilePath(fileName)));
                try {
                    trans =(ArrayList<Transfer>) objectInputStream.readObject();
                    objectInputStream.close();
                    
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return trans;
        }
    }
    public static void main(String ...strings) {
        Map<StandardBook, Integer>mm=new HashMap<>();
        mm.put(new StandardBookImpl("iiiinb ", "ghini_merda", 2010, 43,"infoblew", "sisos", "io", 23), Integer.valueOf(5));
        mm.put(new StandardBookImpl("iiiissnb ", "viroli_merda", 2011, 32,"infoblew", "oopmerd", "io", 40), Integer.valueOf(9));
        Transferrer tra=new PersonImpl("joy", "via merda 1", "333 332 332");
        Transferrer trad=new DepotImpl("D1", mm);
        
        
        Transfer tr=new TransferImpl(trad, tra, new Date(1993, 12,5), mm, "883737");
        
        TransferManagerImpl2.getInstanceOfTransferManger().addTransfer(tr);
        System.out.println(TransferManagerImpl2.getInstanceOfTransferManger().getTransfersFromFile("trasferimenti.txt"));
    }

}
