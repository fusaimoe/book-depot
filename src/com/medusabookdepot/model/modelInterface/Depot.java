/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelImpl.Pair;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public interface Depot extends CanSendTransferrer {

    /**
     * 
     * @return the quantity of all books contained in this depot
     */
    public int getQuantity();

    public IntegerProperty QuantityProperty();
    /**
     * 
     * @param book
     *            is the book I want look for its quantity
     * @return the quantity of the book in the depot
     */
    public int getQuantityFromStandardBook(StandardBook book);

    public IntegerProperty QuantityFromStandardBookProperty(StandardBook book);
    /**
     * 
     * @param title
     *            is the title of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that title
     */
    public int getQuantityFromTitle(String title);
    public IntegerProperty QuantityFromTitleProperty(String title);
    
    public void removeBooks(Map<StandardBook, Integer> books);
    public void addBooks(Map<StandardBook, Integer> books);
    
    /**
     * 
     * @param author
     *            is the author of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that author
     */
    public int getQuantityFromAuthor(String author);

    public IntegerProperty QuantityFromAuthorProperty(String author);
    /**
     * 
     * @param year
     *            is the year of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that year
     */
    public int getQuantityFromYear(int year);

    public IntegerProperty QuantityFromYearProperty(int year);

    public String getBooksAsString();

    public StringProperty BooksAsStringProperty();

    public Map<StandardBook, Integer> getBooksFromStandardBookIsbn(List<String> isbns);
    public Map<StandardBook, Integer> getBooksFromStandardBookIsbnAndQuantity(
            List<Pair<String, Integer>> isbnsAndQuantities);

    public Map<StandardBook, Integer> getBooks();

    List<StandardBook> getStandardBooksAsList();

    List<String> getStandardBooksIsbns();

    public List<Pair<String, Integer>> getBookIsbnsAsListOfPair();
}
