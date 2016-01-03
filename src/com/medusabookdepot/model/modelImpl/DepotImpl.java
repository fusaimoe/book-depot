/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Map;

import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.Parcel;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transferrer;

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
    public void doTransfer(Transferrer transferrer, boolean sender,Parcel parcel) {
        transfers.add(new TransferImpl(sender? this:transferrer, sender==false? transferrer:this, parcel, Date.valueOf(LocalDate.now())));
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
            if(libro.getIsbn().equals(book.getIsbn())) {
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

    public String toString() {
        return this.name+"\n"+this.getQuantity();
    }
}
