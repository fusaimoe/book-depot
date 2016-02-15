/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
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
public class DepotImpl extends TransferrerImpl implements Depot, CanSendTransferrer, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 47429766209693618L;
    private Map<StandardBook, Integer> books;
    public DepotImpl(String name, Map<StandardBook, Integer> books) {
        super(name);
        if (books == null) {
            this.books = new HashMap<StandardBook, Integer>();
        } else {
            this.books = new HashMap<>(books);// mi creo un altro oggeeto così
                                              // non mantengo il riferimento
                                              // alla mappa di input
        }
    }

    @Override
    public int getQuantity() {
        int x = 0;
        for (StandardBook libro : this.books.keySet()) {
            x += books.get(libro);
        }
        return x;
    }
    @Override
    public int getQuantityFromStandardBook(StandardBook book) {

        int x = 0;
        for (StandardBook libro : this.books.keySet()) {
            if (libro.getIsbn().equals(book.getIsbn())) {
                x += books.get(libro);
            }
        }
        return x;
    }

    @Override
    public int getQuantityFromTitle(String title) {

        int x = 0;
        for (StandardBook libro : this.books.keySet()) {
            if (libro.getTitle().equals(title)) {
                x += books.get(libro);
            }
        }
        return x;
    }

    @Override
    public int getQuantityFromAuthor(String author) {

        int x = 0;
        for (StandardBook libro : this.books.keySet()) {
            if (libro.getAuthor().equals(author)) {
                x += books.get(libro);
            }
        }
        return x;
    }
    @Override
    public int getQuantityFromYear(int year) {

        int x = 0;
        for (StandardBook libro : this.books.keySet()) {
            if (libro.getYear() == year) {
                x += books.get(libro);
            }
        }
        return x;
    }
    public String toString() {
        return "Deposito " + this.name.get() + this.books + "\n";
    }

    @Override
    public String getBooksAsACoolString() {
        String finale = new String("");
        for (Entry<StandardBook, Integer> entry : this.books.entrySet()) {
            finale = finale.concat("libro: "+entry.getKey().getTitle()+"\n\t" + "in quantità " + entry.getValue() + "\n");
        }
        return finale;
    }

    @Override
    public IntegerProperty QuantityProperty() {
        return new SimpleIntegerProperty(this.getQuantity());
    }

    @Override
    public IntegerProperty QuantityFromStandardBookProperty(StandardBook book) {
        return new SimpleIntegerProperty(this.getQuantityFromStandardBook(book));
    }

    @Override
    public IntegerProperty QuantityFromTitleProperty(String title) {
        return new SimpleIntegerProperty(this.getQuantityFromTitle(title));
    }

    @Override
    public IntegerProperty QuantityFromAuthorProperty(String author) {
        return new SimpleIntegerProperty(this.getQuantityFromAuthor(author));
    }

    @Override
    public IntegerProperty QuantityFromYearProperty(int year) {
        return new SimpleIntegerProperty(this.getQuantityFromYear(year));
    }

    @Override
    public StringProperty BooksAsStringProperty() {
        return new SimpleStringProperty(this.getBooksAsACoolString());
    }
    @Override
    public boolean containsBooks(Map<StandardBook, Integer> books) {
        for (Entry<StandardBook, Integer> entry : books.entrySet()) {
            if (!(this.books.containsKey(entry.getKey()) && this.books.get(entry.getKey()).equals(entry.getValue()))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void removeBooks(Map<StandardBook, Integer> books) {
        int x=0;
        for (Entry<StandardBook, Integer> entry : books.entrySet()) {
            x++;
            if(this.books.get(entry.getKey()) - entry.getValue()<=0) {
                this.books.remove(entry.getKey());
            }
            else {
                this.books.put(entry.getKey(), this.books.get(entry.getKey()) - entry.getValue());
            }
            
        }
    }

    @Override
    public void addBooks(Map<StandardBook, Integer> books) {
        for (Entry<StandardBook, Integer> entry : books.entrySet()) {
            if (this.books.containsKey(entry.getKey())) {
                this.books.put(entry.getKey(), entry.getValue() + this.books.get(entry.getKey()));
            } else {
                this.books.put(entry.getKey(), entry.getValue());
            }
        }

    }

    @Override
    public boolean isADepot() {
        return true;
    }

    @Override
    public Map<StandardBook, Integer> getBooksFromStandardBookIsbn(List<String> isbns) {
        if (isbns.isEmpty() || isbns == null) {
            return this.books;
        } else {
            Map<StandardBook, Integer> libri = new HashMap<>();
            for (Entry<StandardBook, Integer> entry : this.books.entrySet()) {
                if (isbns.contains(entry.getKey().getIsbn())) {
                    libri.put(entry.getKey(), entry.getValue());
                }
            }
            return libri;
        }
    }
    @Override
    public List<StandardBook> getStandardBooksAsList() {
        List<StandardBook> lis = new ArrayList<>();
        for (StandardBook b : this.books.keySet()) {
            // copia difensiva
            lis.add(new StandardBookImpl(b.getTitle(), b.getIsbn(), b.getYear(), b.getPages(), b.getSerie(),
                    b.getGenre(), b.getAuthor(), b.getPrice()));
        }
        return lis;
    }
    @Override
    public List<String> getStandardBooksIsbns() {
        List<String> lis = new ArrayList<>();
        for (StandardBook b : this.books.keySet()) {
            // copia difensiva
            lis.add(new String(b.getIsbn()));
        }
        return lis;
    }
    @Override
    public Map<StandardBook, Integer> getBooks() {
        Map<StandardBook, Integer> map = new HashMap<>();
        for (Entry<StandardBook, Integer> ee : this.books.entrySet()) {
            map.put(ee.getKey(), ee.getValue());
        }
        return map;// copia difensiva
    }

    @Override
    public Map<StandardBook, Integer> getBooksFromStandardBookIsbnAndQuantity(
            List<Pair<String, Integer>> isbnsAndQuantities) {
        if (isbnsAndQuantities.isEmpty() || isbnsAndQuantities == null) {
            return this.books;
        }
        List<String> ss = new ArrayList<>();
        for (Pair<String, Integer> pa : isbnsAndQuantities) {
            ss.add(pa.getFirst());
        }
        Map<StandardBook, Integer> mappa = getBooksFromStandardBookIsbn(ss);
        List<Integer> ii = new ArrayList<>();
        for (Pair<String, Integer> pa : isbnsAndQuantities) {
            ii.add(pa.getSecond());
        }
        int x = 0;
        for (Entry<StandardBook, Integer> en : mappa.entrySet()) {
            en.setValue(ii.get(x));
            x++;
        }
        return mappa;
    }

    @Override
    public List<Pair<String, Integer>> getBookIsbnsAsListOfPair() {
        List<Pair<String, Integer>> lis = new ArrayList<>();
        for (Entry<StandardBook, Integer> en : this.books.entrySet()) {
            lis.add(new Pair<String, Integer>(en.getKey().getIsbn(), en.getValue()));
        }
        return lis;
    }

    @Override
    public void addBook(Pair<StandardBook, Integer> pair) {
        if(!this.books.containsKey(pair.getFirst())) {
            this.books.put(pair.getFirst(), pair.getSecond());
        }
        
    }
}
