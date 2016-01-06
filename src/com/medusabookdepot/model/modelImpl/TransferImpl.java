/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class TransferImpl implements Transfer {

    private Transferrer sender;
    private Transferrer receiver;
    private Date leavingDate;
    private String trackingNumber;
    private Map<StandardBook,Integer> books;
    
    public  TransferImpl(Transferrer sender,Transferrer receiver,Date leavingDate, Map<StandardBook,Integer> books) {
        this.sender=sender;
        this.receiver=receiver;
        this.leavingDate=leavingDate;
        this.trackingNumber=this.getNewTrackingNumber();
        this.books=books;
    }
    public String getNewTrackingNumber() {
        List<String>allTrackings=new ArrayList<>();
        for(Transfer trans:TransferrerImpl.transfers){
            allTrackings.add(trans.getTrackingNumber());
        }
        Random rm=new Random();
        String tr=String.valueOf(rm.nextInt(1000000));
        while(!allTrackings.contains(tr)) {
            tr=String.valueOf(rm.nextInt(1000000));
        }
        return tr;
    }
    @Override
    public Transferrer getSender() {
        return this.sender;
    }

    @Override
    public Transferrer getReceiver() {
        return this.receiver;
    }

    @Override
    public Map<StandardBook,Integer> getBooks() {
        return this.books;
    }

    @Override
    public Date getLeavingDate() {
        return this.leavingDate;
    }

    @Override
    public void setSender(Transferrer sender) {
        this.sender=sender;
    }

    @Override
    public void setReceiver(Transferrer receiver) {
        this.receiver=receiver;
    }

    @Override
    public void setBooks(Map<StandardBook,Integer> books) {
        this.books=books;
    }

    @Override
    public void setLeavingDate(Date leavingDate) {
        this.leavingDate=leavingDate;
    }
    @Override
    public String getTrackingNumber() {
        return this.trackingNumber;
    }
    @Override
    public int getQuantity() {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            x+=books.get(libro);
        }
        return x;
    }

    
    @Override
    public int getQuantityFromBook(StandardBook book) {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            if(libro.getIsbn().equals(book.getIsbn())) {
                x+=books.get(libro).intValue();
            }
        }
        return x;
    }

    
    @Override
    public int getTotalPrice() {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            x+=libro.getPrice();
        }
        return x;
    }

    
    @Override
    public void setTrackingNumber(String trackingnumber) {
        this.trackingNumber=trackingnumber;
    }

    
    @Override
    public void setQuantityFromBook(StandardBook book,int quantity) {
        this.books.replace(book, books.get(book), Integer.valueOf(quantity));

    }

    
    @Override
    public void replaceBook(StandardBook oldBook, StandardBook newBook) {
        Integer x=books.get(oldBook);
        this.books.remove(oldBook);
        this.books.put(newBook, x);

    }
    public String toString() {
        return this.leavingDate+"\n"+this.receiver+"\n"+this.sender+"\n"+this.trackingNumber+"\n"+this.getQuantity();
    }
}
