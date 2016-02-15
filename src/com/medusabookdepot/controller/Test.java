package com.medusabookdepot.controller;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.Pair;
import com.medusabookdepot.model.modelImpl.PrinterImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.model.modelInterface.Customer;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;

public class Test {

    @org.junit.Test
    public void test() {
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
        
        Depot dep2=new DepotImpl("Shell", mm);
        List<Pair<String,Integer>>lis=dep2.getBookIsbnsAsListOfPair();
        lis.set(0, new Pair<String, Integer>(lis.get(0).getFirst(), 11));
        lis=lis.subList(0, 1);
        dep2.removeBooks(dep2.getBooksFromStandardBookIsbnAndQuantity(lis));
        assertTrue(dep2.getQuantity()<=(mm.get(b)+mm.get(b2))&&dep2.getQuantity()==127&&127==dep2.getQuantityFromYear(2010));
        
        StandardBook book=new StandardBookImpl("eeqqrq", "la fabbrica di cioccolato", 1994, 122, "dolci storie","infanzia", "Roahl Dahl", 20);
        dep2.addBook(new Pair<StandardBook,Integer>(book, 42));
        assertTrue(dep2.getQuantity()==169);
        Calendar cal=Calendar.getInstance();
        cal.set(2016, 02, 23);
        Date date=cal.getTime();
        
        List<String>lis2=dep2.getStandardBooksIsbns().subList(0, 2);
        Transfer tr=new TransferImpl(dep2, dep, date, dep2.getBooksFromStandardBookIsbn(lis2));
        assertTrue(!tr.isArrived());
        assertTrue(tr.getTotalPrice()==4315);
        tr.setArrived(true);
        assertTrue(tr.isArrived());
    }

}
