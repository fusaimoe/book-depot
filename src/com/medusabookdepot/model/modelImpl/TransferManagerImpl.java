package com.medusabookdepot.model.modelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.DepotManager;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.TransferManager;
import com.medusabookdepot.model.modelInterface.Transferrer;

import javafx.collections.ObservableList;


public class TransferManagerImpl implements TransferManager {
    private static TransferManager sing;//singleton
    private List<Transfer> transfers;//List that contains all transfers alive
    private String defaultFileName;
    
    private TransferManagerImpl() {
        //costruttore privato!
        this.defaultFileName="trasferimenti.dat";
        File f=new File(System.getProperty("user.home")+System.getProperty("file.separator")+"filesMedusa");
        if(!f.exists()&&!f.isDirectory()) {
            f.mkdir();
        }
        this.transfers=getTransfersFromFile(this.defaultFileName);
        
    }
    
    public static TransferManager getInstanceOfTransferManger() {
        if(sing==null) {
            sing=new TransferManagerImpl();
            return sing;
        }
        else {
            return TransferManagerImpl.sing;
        }
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return this.transfers;
    }
    
    @Override
    public void addTransfer(Transfer transfer) {
        if(!this.transfers.isEmpty()) {
            List<String>allTrackings=new ArrayList<>();
            for(Transfer t:this.transfers) {
                allTrackings.add(t.getTrackingNumber());
            }
            while(allTrackings.contains(transfer.getTrackingNumber())) {//assegno un altro tracking number
                transfer=new TransferImpl(transfer.getSender(), transfer.getReceiver(),transfer.getLeavingDate(), transfer.getBooks());
            }
        }
        if(transfer.getSender().containsBooks(transfer.getBooks())) {
            DepotManager dm=DepotManagerImpl.getInstanceOfDepotManger();
            if(transfer.getSender().isADepot() && dm.hasIn((Depot) transfer.getSender())) {
                Depot dep=(Depot) transfer.getSender();
                System.out.println(dep);
                dm.removeDepot(dep);
                dep.removeBooks(transfer.getBooks());
                System.out.println(dep);
                dm.addDepot(dep);
            }
            if(transfer.getReceiver().isADepot() && dm.hasIn((Depot) transfer.getReceiver())) {
                Depot depo=(Depot) transfer.getReceiver();
                dm.removeDepot(depo);
                depo.addBooks(transfer.getBooks());
                dm.addDepot(depo);
            }
            this.transfers.add(transfer);
            this.writeTransferOnFile(this.defaultFileName,transfer);
        }
        
    }
    @Override
    public void addTransfer(CanSendTransferrer sender, Transferrer receiver, java.util.Date leavingDate,Map<StandardBook, Integer> books) {
        Transfer t=new TransferImpl(sender, receiver, leavingDate, books);
        this.addTransfer(t);
    }
    @Override
    public void removeTransfer(Transfer transfer) {
        this.transfers.remove(transfer);
        this.removeTransferFromFile(this.defaultFileName,transfer);
        
    }
    @Override
    public void removeTransfer(int index) {
        this.transfers.remove(index);
        this.removeTransferFromFile(this.defaultFileName,index);
        
    }

    private void writeTransferOnFile(String fileName, Transfer transfer) {
        try {
            List<Transfer> trans=getTransfersFromFile(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(fileName)));
            trans.add(transfer);
            oos.writeObject(trans);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void removeTransferFromFile(String fileName, Transfer transfer) {
        try {
            List<Transfer> trans=getTransfersFromFile(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(fileName)));
            trans.remove(transfer);
            oos.writeObject(trans);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    private void removeTransferFromFile(String fileName, int index) {
        try {
            List<Transfer> trans=getTransfersFromFile(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(fileName)));
            trans.remove(index);
            oos.writeObject(trans);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    private String getFilePath(String fileName) {
        String filepath = System.getProperty("user.home")+System.getProperty("file.separator")+"filesMedusa"+System.getProperty("file.separator")+fileName;
        return filepath;
    }

    @SuppressWarnings("unchecked")
    private List<Transfer> getTransfersFromFile(String fileName) {
        List<Transfer> trans=new ArrayList<>();
        File f = new File(getFilePath(fileName));
        if(!f.exists()) {
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
    @SuppressWarnings("unchecked")
    @Override
    public void registerTransfersFromFile(File f) {
        if(f.exists()&&!f.isDirectory()) {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(f));
                if(f.exists()&&!f.isDirectory()) {
                    List<Transfer> trans=(ArrayList<Transfer>) objectInputStream.readObject();
                    for(Transfer t:trans){
                        if(!this.getAllTrackings().contains(t.getTrackingNumber())) {
                            this.addTransfer(t);
                        }
                    }
                }
                objectInputStream.close();
                
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    private List<String> getAllTrackings() {
        List<String> lis=new ArrayList<>();
        for(Transfer t:this.getAllTransfers()) {
            lis.add(t.getTrackingNumber());
        }
        return lis;
    }
    @Override
    public ObservableList<? extends Transfer> getAllTransfersProperty() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setDefaultFileName(String DefaultFileName) {
        this.defaultFileName=DefaultFileName;
        
    }

    @Override
    public String getDefaultFileName() {
        return this.defaultFileName;
    }
    public static void main(String ...strings) {
        Map<StandardBook, Integer>mm=new HashMap<>();
        mm.put(new StandardBookImpl("iiiinb", "eeee", 2010, 43,"infoblew", "sisos", "io", 23), Integer.valueOf(5));
        mm.put(new StandardBookImpl("iiiissnb", "fff", 2011, 32,"infoblew", "oop", "io", 40), Integer.valueOf(9));
        Transferrer per=new PersonImpl("joy", "via lazio 4", "333 332 332");
        Depot trad=new DepotImpl("D1", mm);
        DepotManagerImpl.getInstanceOfDepotManger().addDepot(trad);
        Calendar cal =Calendar.getInstance();
        cal.set(2013, 2, 2);
        Map<StandardBook, Integer>mapps=trad.getBooksFromStandardBookIsbn("iiiissnb");
        //mapps=trad.getBooksFromStandardBookIsbnAndQuantity(new Pair<String, Integer>("iiiinb", 1),new Pair<String, Integer>("iiiissnb", 2));
        Transfer tr=new TransferImpl(trad, per,cal.getTime() , mapps, "883737");
        
        
        Map<StandardBook, Integer>mm2=new HashMap<>();
        mm2.put(new StandardBookImpl("evdfb ", "gauss", 2040, 20,"mate", "calcolo", "fabrizio caselli", 234), Integer.valueOf(8));
        mm2.put(new StandardBookImpl("eerdfs ", "lambdas", 2051, 50,"labo", "oop", "lionel Ritchie", 400), Integer.valueOf(20));
        CanSendTransferrer prin=new PrinterImpl("printer", "via roma 3", "07184939");
        Depot trad2=new DepotImpl("sw", null);
        DepotManagerImpl.getInstanceOfDepotManger().addDepot(trad2);
        
        Calendar cal2 =Calendar.getInstance();
        cal2.set(2015, 0, 2);
        
        Transfer tr2=new TransferImpl(prin, trad2, cal2.getTime(), mm2);
        
        Map<StandardBook, Integer>mm3=new HashMap<>();
        mm3.put(new StandardBookImpl("eddvdfb ", "bo1", 1933, 202,"chennes", "malvagità", "gente a caso", 214), Integer.valueOf(2));
        mm3.put(new StandardBookImpl("eerdddfs ", "bo2",1999, 520,"chennes", "ignoranza", "lionel messi", 4020), Integer.valueOf(11));
        Calendar cal3 =Calendar.getInstance();
        cal3.set(2015,5, 4);
        CanSendTransferrer l=new LibraryImpl("da rosi", "via mia 3", "07123422");
        Transfer tr3=new TransferImpl(l, trad2, cal3.getTime(), mm3);
        System.out.println(tr.getSender());
        TransferManagerImpl.getInstanceOfTransferManger().addTransfer(tr);
        TransferManagerImpl.getInstanceOfTransferManger().addTransfer(tr2);
        //TransferManagerImpl.getInstanceOfTransferManger().addTransfer(tr3);
        System.out.println(TransferManagerImpl.getInstanceOfTransferManger().getAllTransfers().get(0));
        System.out.println(TransferManagerImpl.getInstanceOfTransferManger().getAllTransfers().get(1));
        
    }

    

}
