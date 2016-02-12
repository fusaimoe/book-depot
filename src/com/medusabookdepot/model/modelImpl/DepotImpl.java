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

import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.beans.property.IntegerProperty;
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
    private Map<StandardBook, Integer> books;
    public DepotImpl(String name, Map<StandardBook, Integer> books) {
        super(name);
        if (books == null) {
            this.books = new HashMap<StandardBook, Integer>();
        } else {
            this.books = books;
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
        return this.name + "\n" + this.books + "\n";
    }

    @Override
    public String getBooksAsString() {
        String finale = new String("");
        for (Entry<StandardBook, Integer> entry : this.books.entrySet()) {
            finale = finale.concat(entry.getKey().toString() + ">" + entry.getValue() + ",");
        }
        return finale;
    }

    @Override
    public IntegerProperty getQuantityProperty() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IntegerProperty getQuantityFromStandardBookProperty(StandardBook book) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IntegerProperty getQuantityFromTitleProperty(String title) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IntegerProperty getQuantityFromAuthorProperty(String author) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public IntegerProperty getQuantityFromYearProperty(int year) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StringProperty getBooksAsStringProperty() {
        // TODO Auto-generated method stub
        return null;
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
        for (Entry<StandardBook, Integer> entry : books.entrySet()) {
            this.books.put(entry.getKey(), this.books.get(entry.getKey()) - entry.getValue());
        }
        List<StandardBook> lis = new ArrayList<>();
        for (Entry<StandardBook, Integer> entry : this.books.entrySet()) {
            if (entry.getValue() == 0) {
                lis.add(entry.getKey());
            }
        }
        for (StandardBook b : lis) {
            this.books.remove(b);
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
    public Map<StandardBook, Integer> getBooks() {
        Map<StandardBook, Integer> map = new HashMap<>();
        for (Entry<StandardBook, Integer> ee : this.books.entrySet()) {
            map.put(ee.getKey(), ee.getValue());
        }
        return map;
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
}
