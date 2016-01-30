package com.medusabookdepot.model.modelImpl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.DepotManager;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

public class DepotManagerImpl implements DepotManager {

    private static DepotManager single=null;//singleton
    private List<Depot> depots;//List that contains all transfers alive
    
    private DepotManagerImpl() {
        //costruttore privato!
        this.depots=getDepotsFromFile("depositi.txt");
    }
    
    public static DepotManager getInstanceOfDepotManger() {
        if(single==null) {
            single=new DepotManagerImpl();
            return single;
        }
        else {
            return DepotManagerImpl.single;
        }
    }
    
    

    @Override
    public List<? extends Depot> getAllDepots() {
        return this.depots;
    }

    @Override
    public void addDepot(Depot depot) {
        this.depots.add(depot);
        this.writeDepotOnFile("depositi.txt",depot);
    }

    

    @Override
    public void addDepot(String name, Map<StandardBook, Integer> books) {
        Depot depot=new DepotImpl(name, books);
        this.depots.add(depot);
        this.writeDepotOnFile("depositi.txt",depot);
    }

    @Override
    public void removeDepot(Transfer transfer) {
        this.depots.remove(transfer);
        this.removeDepotFromFile("depositi.txt",transfer);
    }
    @Override
    public void removeDepot(int index) {
        this.depots.remove(index);
        this.removeDepotFromFile("depositi.txt",index);
    }
    
    private void writeDepotOnFile(String fileName, Depot depot) {
        try {
            List<Depot> deps=getDepotsFromFile(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(fileName)));
            deps.add(depot);
            oos.writeObject(deps);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    

    private void removeDepotFromFile(String fileName, Transfer transfer) {
        try {
            List<Depot> deps=getDepotsFromFile(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(fileName)));
            deps.remove(transfer);
            oos.writeObject(deps);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    private void removeDepotFromFile(String fileName, int index) {
        try {
            List<Depot> deps=getDepotsFromFile(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(fileName)));
            deps.remove(index);
            oos.writeObject(deps);
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
    
    private String getFilePath(String fileName) {
        String filepath = System.getProperty("user.home")+System.getProperty("file.separator")+ fileName;
        return filepath;
    }
    
    @SuppressWarnings("unchecked")
    private List<Depot> getDepotsFromFile(String fileName) {
        List<Depot> deps=new ArrayList<>();
        File f = new File(getFilePath(fileName));
        if(!f.exists()) {
            return new ArrayList<Depot>();
        }
        else {
            try {
                ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(getFilePath(fileName)));
                try {
                    deps =(ArrayList<Depot>) objectInputStream.readObject();
                    objectInputStream.close();
                    
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return deps;
        }
    }
    public static void main(String...args) {
        Map<StandardBook, Integer>mm=new HashMap<>();
        mm.put(new StandardBookImpl("iiiinb ", "ghini_merda", 2010, 43,"infoblew", "sisos", "io", 23), Integer.valueOf(5));
        mm.put(new StandardBookImpl("iiiissnb ", "viroli_merda", 2011, 32,"infoblew", "oopmerd", "io", 40), Integer.valueOf(9));
        Depot trad=new DepotImpl("D1", mm);
        
        Map<StandardBook, Integer>mm2=new HashMap<>();
        mm2.put(new StandardBookImpl("evdfb ", "caselli_merda", 2040, 20,"mate", "calcolomer", "gesu", 234), Integer.valueOf(8));
        mm2.put(new StandardBookImpl("eerdfs ", "pianini merda", 2051, 50,"labo", "oopmerd", "dio", 400), Integer.valueOf(20));
        Depot trad2=new DepotImpl("sw", mm2);
        
        DepotManagerImpl.getInstanceOfDepotManger().addDepot(trad);
        DepotManagerImpl.getInstanceOfDepotManger().addDepot(trad2);
        System.out.println(DepotManagerImpl.getInstanceOfDepotManger().getAllDepots().get(1).getQuantityFromAuthor("dio"));
        DepotManagerImpl.getInstanceOfDepotManger().removeDepot(0);
        System.out.println(DepotManagerImpl.getInstanceOfDepotManger().getAllDepots().get(0).getQuantityFromAuthor("io"));
        System.out.println(DepotManagerImpl.getInstanceOfDepotManger().getAllDepots().size());


    }

}
