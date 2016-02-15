package com.medusabookdepot.model.modelImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

public class TestModel {

    @org.junit.Test
    public void testTransfers() {/*
        Map<StandardBook, Integer> mm = new HashMap<>();
        StandardBook b=new StandardBookImpl("iiiinb", "eeee", 2010, 43, "infoblew", "sisos", "io", 23);
        StandardBook b2=new StandardBookImpl("iiiissnb", "fff", 2011, 32, "infoblew", "oop", "io", 40);
        mm.put(b, Integer.valueOf(5));
        mm.put(b2, Integer.valueOf(9));
        Transferrer per = new PersonImpl("joy", "via lazio 4", "333 332 332");
        Depot trad = new DepotImpl("D1", mm);
        DepotManagerImpl.getInstanceOfDepotManger().addDepot(trad);
        Calendar cal = Calendar.getInstance();
        cal.set(2013, 2, 2);
        List<String> isbns = new ArrayList<>();
        isbns.add("iiiinb");
        Map<StandardBook, Integer> mapps = trad.getBooksFromStandardBookIsbn(isbns);
        assertTrue(trad.containsBooks(mapps));
        assertTrue(mapps.get(b)==5);
        assertTrue(mapps.size()==1);
        List<Pair<String, Integer>> lis = new ArrayList<>();
        lis.add(new Pair<String, Integer>("iiiinb", 2));
        lis.add(new Pair<String, Integer>("iiiissnb", 6));
        mapps = trad.getBooksFromStandardBookIsbnAndQuantity(lis);
        assertTrue(mapps.get(b)==2&&mapps.get(b2)==6);
        assertTrue(mapps.size() == 2);
        Transfer tr = new TransferImpl(trad, per, cal.getTime(), mapps, "883737");
        TransferManagerImpl.getInstanceOfTransferManger().addTransfer(tr);*/
    }

}
