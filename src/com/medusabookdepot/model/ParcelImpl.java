/**
 * 
 */
package com.medusabookdepot.model;

import java.util.Map;
import com.medusabookdepot.modelInterface.Parcel;
import com.medusabookdepot.modelInterface.StandardBook;

/**
 * @author Marcello_Feroce
 *
 */
public class ParcelImpl implements Parcel {

    private String trackingnumber;
    private Map<StandardBook,Integer> books;
    
    public ParcelImpl(String trackingnumber, Map<StandardBook,Integer> books) {
        this.trackingnumber=trackingnumber;
        this.books=books;
    }
    @Override
    public String getTrackingNumber() {
        return this.trackingnumber;
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
            if(libro.getISBN().equals(book.getISBN())) {
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
        this.trackingnumber=trackingnumber;
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

}
