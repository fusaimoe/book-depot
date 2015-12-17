/**
 * 
 */
package com.medusabookdepot.model;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;
import com.medusabookdepot.modelInterface.Depot;
import com.medusabookdepot.modelInterface.Parcel;
import com.medusabookdepot.modelInterface.StandardBook;
import com.medusabookdepot.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class DepotImpl extends TransferrerImpl implements Depot {

    private Map<StandardBook,Integer> books;
    
    public DepotImpl(String name,Map<StandardBook,Integer> books) {
        super(name);
        this.books=books;
    }
    
    @Override
    public String getName() {
        return this.name.get();
    }
    @Override
    public void doTransfer(Transferrer transferrer, boolean sender,Parcel parcel) {
        transfers.add(new TransferImpl(sender? this:transferrer, sender==false? transferrer:this, parcel, Date.valueOf(LocalDate.now())));
    }
    @Override
    public void setName(String name) {
        this.name=Optional.ofNullable(name);
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
    public int getQuantityFromStandardBook(StandardBook book) {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            if(libro.getISBN().equals(book.getISBN())) {
                x+=books.get(libro);
            }
        }
        return x;
    }
    @Override
    public int getQuantityFromTitle(String title) {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            if(libro.getName().equals(title)) {
                x+=books.get(libro);
            }
        }
        return x;
    }

    @Override
    public int getQuantityFromAuthor(String author) {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            if(libro.getAuthor().equals(author)) {
                x+=books.get(libro);
            }
        }
        return x;
    }
    
    @Override
    public int getQuantityFromYear(int year) {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            if(libro.getYear()==year) {
                x+=books.get(libro);
            }
        }
        return x;
    }

}
