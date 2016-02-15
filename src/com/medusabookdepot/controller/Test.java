package com.medusabookdepot.controller;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.Pair;
import com.medusabookdepot.model.modelImpl.PrinterImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.Customer;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;

public class Test {

    @org.junit.Test
    public void testModel() {
        Map<StandardBook, Integer> mm = new HashMap<>();
        StandardBook b=new StandardBookImpl("iiiinb", "il fantasma", 2010, 43, "la casa degli spettri", "orrore", "stephen King", 23);
        StandardBook b2=new StandardBookImpl("iiiissnb", "l'orso nella casa blu", 2010, 32, "bimbi cattivi", "infanzia rovinata", "quel mattachione dell'orso bear", 40);
        mm.put(b, Integer.valueOf(45));
        mm.put(b2, Integer.valueOf(93));
        Depot dep = new DepotImpl("Jellyfish", mm);
        assertTrue(dep.isADepot());
        assertTrue(dep.containsBooks(mm));
        assertTrue(dep.getBooks().equals(mm));
        Customer printo=new PrinterImpl("non solo stampe", "via della carta 3", "0712074248");
        assertTrue(printo.getName().equals("non solo stampe")&&printo.getAddress().equals("via della carta 3")&&printo.getTelephoneNumber().equals("0712074248"));
        assertTrue(!printo.isADepot());
        assertTrue(printo.isAPrinter()&&!printo.isAPerson()&&!printo.isALibrary());
        List<String>lisIsbn=dep.getStandardBooksIsbns();
        dep.removeBooks(dep.getBooksFromStandardBookIsbn(lisIsbn.subList(0, 1)));
        assertTrue(dep.getBooks().size()==1);
        
        dep=new DepotImpl("Shell", mm);
        List<Pair<String,Integer>>lis=dep.getBookIsbnsAsListOfPair();
        lis.set(1, new Pair<String, Integer>(lis.get(1).getFirst(), 11));
        lis=lis.subList(1, 2);
        dep.removeBooks(dep.getBooksFromStandardBookIsbnAndQuantity(lis));
        assertTrue(dep.getQuantity()<=(mm.get(b)+mm.get(b2))&&dep.getQuantity()==127&&127==dep.getQuantityFromYear(2010));
        System.out.println(dep.getBooksAsACoolString());
    }

}
