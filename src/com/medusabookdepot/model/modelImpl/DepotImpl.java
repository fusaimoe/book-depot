/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.Map;
import java.util.Map.Entry;

import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public class DepotImpl extends TransferrerImpl implements Depot, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 47429766209693618L;
    private Map<StandardBook,Integer> books;
    public DepotImpl(String name,Map<StandardBook,Integer> books) {
        super(name);
        this.books=books;
        this.isDepot=true;
    }
    
    @Override
    public int getQuantity() {
        return this.getQuantityProperty().get();
    }

    @Override
    public IntegerProperty getQuantityProperty() {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            x+=books.get(libro);
        }
        return new SimpleIntegerProperty(x);
    }
    @Override
    public int getQuantityFromStandardBook(StandardBook book) {
        
        return this.getQuantityFromStandardBookProperty(book).get();
    }
    @Override
    public IntegerProperty getQuantityFromStandardBookProperty(StandardBook book) {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            if(libro.getIsbn().equals(book.getIsbn())) {
                x+=books.get(libro);
            }
        }
        return new SimpleIntegerProperty(x);
    }
    
    @Override
    public int getQuantityFromTitle(String title) {
        
        return this.getQuantityFromTitleProperty(title).get();
    }

    @Override
    public IntegerProperty getQuantityFromTitleProperty(String title) {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            if(libro.getName().equals(title)) {
                x+=books.get(libro);
            }
        }
        return new SimpleIntegerProperty(x);
    }

    @Override
    public int getQuantityFromAuthor(String author) {
        
        return this.getQuantityFromAuthorProperty(author).get();
    }
    @Override
    public IntegerProperty getQuantityFromAuthorProperty(String author) {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            if(libro.getAuthor().equals(author)) {
                x+=books.get(libro);
            }
        }
        return new SimpleIntegerProperty(x);
    }
    @Override
    public int getQuantityFromYear(int year) {
        
        return this.getQuantityFromYearProperty(year).get();
    }
    @Override
    public IntegerProperty getQuantityFromYearProperty(int year) {
        int x=0;
        for(StandardBook libro :this.books.keySet()) {
            if(libro.getYear()==year) {
                x+=books.get(libro);
            }
        }
        return new SimpleIntegerProperty(x);
    }
    public String toString() {
        return this.name+"\n"+this.books+"\n";
    }

    @Override
    public String getBooksAsString() {
        return this.getBooksAsStringProperty().get();
    }

    @Override
    public StringProperty getBooksAsStringProperty() {
        String finale=new String("");
        for(Entry<StandardBook,Integer> entry:this.books.entrySet()){
            finale=finale.concat(entry.getKey().toString()+">"+entry.getValue()+",");
        }
        return new SimpleStringProperty(finale);
    }
}
