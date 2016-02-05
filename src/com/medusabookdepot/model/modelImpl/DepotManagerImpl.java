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

public class DepotManagerImpl implements DepotManager {

    private static DepotManager single=null;//singleton
    private List<Depot> depots;//List that contains all depots alive
    private String defaultFileName;
    
    private DepotManagerImpl() {
        //costruttore privato!
        this.defaultFileName="depositi.dat";
        this.depots=getDepotsFromFile(this.defaultFileName);
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
        this.writeDepotOnFile(this.defaultFileName,depot);
    }
    @Override
    public void addDepot(String name, Map<StandardBook, Integer> books) {
        Depot depot=new DepotImpl(name, books);
        this.depots.add(depot);
        this.writeDepotOnFile(this.defaultFileName,depot);
    }

    @Override
    public void removeDepot(Depot depot) {
        this.depots.remove(depot);
        this.removeDepotFromFile(this.defaultFileName,depot);
    }
    @Override
    public void removeDepot(int index) {
        this.depots.remove(index);
        this.removeDepotFromFile(this.defaultFileName,index);
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
    private void removeDepotFromFile(String fileName, Depot depot) {
        try {
            List<Depot> deps=getDepotsFromFile(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(getFilePath(fileName)));
            deps.remove(depot);
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
            return new ArrayList<>();
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

    @Override
    public void setDefaultFileName(String DefaultFileName) {
        this.defaultFileName=DefaultFileName;
        
    }

    @Override
    public String getDefaultFileName() {
        return this.defaultFileName;
    }
    public static void main(String...args) {
        Map<StandardBook, Integer>mm=new HashMap<>();
        mm.put(new StandardBookImpl("iiiinb ", "threads", 2010, 43,"info", "sisos", "io", 23), Integer.valueOf(5));
        mm.put(new StandardBookImpl("iiiissnb ", "javas", 2011, 32,"info", "oop", "io", 40), Integer.valueOf(9));
        Depot trad=new DepotImpl("D1", mm);
        
        Map<StandardBook, Integer>mm2=new HashMap<>();
        mm2.put(new StandardBookImpl("evdfb", "gauss", 2040, 20,"mate", "calcolo", "fabrizio caselli", 234), Integer.valueOf(8));
        mm2.put(new StandardBookImpl("eerdfs", "lambdas", 2051, 50,"labo", "oopm", "lionel Ritchie", 400), Integer.valueOf(20));
        Depot trad2=new DepotImpl("sw", mm2);
        
        DepotManagerImpl.getInstanceOfDepotManger().addDepot(trad);
        DepotManagerImpl.getInstanceOfDepotManger().addDepot(trad2);
        System.out.println(DepotManagerImpl.getInstanceOfDepotManger().getAllDepots().get(1).getQuantityFromAuthor("dio"));
        DepotManagerImpl.getInstanceOfDepotManger().removeDepot(0);
        System.out.println(DepotManagerImpl.getInstanceOfDepotManger().getAllDepots().get(0).getBooksAsString());
        System.out.println(DepotManagerImpl.getInstanceOfDepotManger().getAllDepots().size());

    }
}
