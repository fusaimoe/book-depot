package com.medusabookdepot.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
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

    private boolean result;

    @org.junit.Test
    public void test() {
        Map<StandardBookImpl, Integer> mm = new HashMap<>();
        StandardBookImpl b = new StandardBookImpl("iiiinb", "il fantasma", 2010, 43, "la casa degli spettri", "orrore",
                "stephen King", 23);
        StandardBookImpl b2 = new StandardBookImpl("iiiissnb", "l'orso nella casa blu", 2010, 32, "bimbi cattivi",
                "infanzia rovinata", "quel mattachione dell'orso bear", 40);
        mm.put(b, Integer.valueOf(45));
        mm.put(b2, Integer.valueOf(93));
        Depot dep = new DepotImpl("Jellyfish", mm);
        assertTrue(dep.isADepot());
        assertTrue(dep.containsBooks(mm));
        assertTrue(dep.getBooks().equals(mm));
        Customer printo = new PrinterImpl("non solo stampe", "via della carta 3", "0712074248");
        assertTrue(printo.getName().equals("non solo stampe") && printo.getAddress().equals("via della carta 3")
                && printo.getTelephoneNumber().equals("0712074248"));
        assertTrue(!printo.isADepot());
        assertTrue(printo.isAPrinter() && !printo.isAPerson() && !printo.isALibrary());
        List<String> lisIsbn = dep.getStandardBooksIsbns().subList(0, 1);
        dep.removeBooks(dep.getBooksFromStandardBookIsbn(lisIsbn));
        assertTrue(dep.getBooks().size() == 1);

        Depot dep2 = new DepotImpl("Shell", mm);
        List<Pair<String, Integer>> lisap=new ArrayList<>();
        List<Pair<String, Integer>> lis = dep2.getBookIsbnsAsListOfPair();
        for(Pair<String, Integer> pa:lis) {
            if(pa.getFirst().equals("iiiissnb")) {
                Pair<String, Integer> p=new Pair<String, Integer>(pa.getFirst(), 11);
                lisap.add(p);
            }
        }
        lis=lisap;
        dep2.removeBooks(dep2.getBooksFromStandardBookIsbnAndQuantity(lis));
        assertTrue(dep2.getQuantity() <= (mm.get(b) + mm.get(b2)) && dep2.getQuantity() == 127
                && 127 == dep2.getQuantityFromYear(2010));

        StandardBookImpl book = new StandardBookImpl("eeqqrq", "la fabbrica di cioccolato", 1994, 122, "dolci storie",
                "infanzia", "Roahl Dahl", 20);
        dep2.addBook(new Pair<StandardBookImpl, Integer>(book, 42));
        assertTrue(dep2.getQuantity() == 169);
        Calendar cal = Calendar.getInstance();
        cal.set(2016, 02, 23);
        Date date = cal.getTime();

        List<String> lis2 = dep2.getStandardBooksIsbns();
        List<String> lisap2=new ArrayList<>();
        for(String s:lis2) {
            if(s.equals(b.getIsbn())||s.equals(b2.getIsbn())) {
                lisap2.add(s);
            }
        }
        lis2=lisap2;
        Transfer tr = new TransferImpl(dep2, dep, date, dep2.getBooksFromStandardBookIsbn(lis2));
        assertTrue(!tr.isArrived());
        assertTrue(tr.getTotalPrice()==4315);
        tr.setArrived(true);
        assertTrue(tr.isArrived());
        this.result=true;
    }
    public boolean getResult(){
        return this.result;
    }
}