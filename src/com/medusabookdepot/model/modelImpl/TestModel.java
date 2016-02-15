package com.medusabookdepot.model.modelImpl;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Customer;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

public class TestModel {

    @org.junit.Test
    public void testTransfers() {
        Map<StandardBook, Integer> mm = new HashMap<>();
        StandardBook b=new StandardBookImpl("iiiinb", "il fantasma", 2010, 43, "la casa degli spettri", "orrore", "stephen King", 23);
        StandardBook b2=new StandardBookImpl("iiiissnb", "l'orso nella casa blu", 2011, 32, "bimbi cattivi", "infanzia rovinata", "quel mattachione dell'orso bear", 40);
        mm.put(b, Integer.valueOf(5));
        mm.put(b2, Integer.valueOf(9));
        Depot dep = new DepotImpl("Jellyfish", mm);
        assertTrue(dep.isADepot());
        assertTrue(dep.containsBooks(mm));
        assertTrue(dep.getBooks().equals(mm));
        System.out.println(dep);
        Customer printo=new PrinterImpl("non solo stampe", "via della carta 3", "0712074248");
        assertTrue(printo.getName().equals("non solo stampe")&&printo.getAddress().equals("via della carta 3")&&printo.getTelephoneNumber().equals("0712074248"));
        assertTrue(!printo.isADepot());
        assertTrue(printo.isAPrinter()&&!printo.isAPerson()&&!printo.isALibrary());
        List<StandardBook>lis=new ArrayList<>();
        lis=dep.getStandardBooksAsList();
        List<String>lisIsbn=new ArrayList<>();
        lisIsbn.add(lis.get(0).getIsbn());
        dep.removeBooks(dep.getBooksFromStandardBookIsbn(lisIsbn));
        assertTrue(dep.getBooks().size()==1);
    }

}
