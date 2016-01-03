/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.util.Map;

import com.medusabookdepot.model.modelInterface.Parcel;
import com.medusabookdepot.model.modelInterface.StandardBook;

/**
 * @author Marcello_Feroce
 *
 */
public class ParcelImpl implements Parcel {

    private String trackingNumber;
    private Map<StandardBook,Integer> books;
    
    public ParcelImpl(String trackingnumber, Map<StandardBook,Integer> books) {
        this.trackingNumber=trackingnumber;
        this.books=books;
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
        return this.trackingNumber+"\n"+this.getQuantity();
    }
}
