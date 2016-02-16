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
 * This interface represents a Depot type of Transferrer, so that does lots of operations on books
 * @author Marcello_Feroce
 *
 */
public interface Depot extends CanSendTransferrer {

    /**
     * 
     * @return the quantity of all books contained in this depot
     */
    public int getQuantity();
    /**
     * 
     * @return the quantity of all books contained in this depot
     */
    public IntegerProperty quantityProperty();
    /**
     * 
     * @param book is the book I want look for its quantity
     * @return the quantity of the book in the depot
     */
    public int getQuantityFromStandardBook(StandardBook book);
    /**
     * 
     * @param book is the book I want look for its quantity
     * @return the quantity of the book in the depot
     */
    public IntegerProperty quantityFromStandardBookProperty(StandardBook book);
    /**
     * 
     * @param title is the title of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that title
     */
    public int getQuantityFromTitle(String title);
    /**
     * 
     * @param title is the title of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that title
     */
    public IntegerProperty quantityFromTitleProperty(String title);
    /**
     * 
     * @param books is the map of the books I want to remove from depot
     */
    public void removeBooks(Map<StandardBook, Integer> books);
    /**
     * 
     * @param books is the map of the books I want to add from depot
     */
    public void addBooks(Map<StandardBook, Integer> books);
    
    /**
     * 
     * @param author is the author of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that author
     */
    public int getQuantityFromAuthor(String author);
    /**
     * 
     * @param author is the author of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that author
     */
    public IntegerProperty quantityFromAuthorProperty(String author);
    /**
     * 
     * @param year is the year of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that year
     */
    public int getQuantityFromYear(int year);
    /**
     * 
     * @param year is the year of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that year
     */
    public IntegerProperty quantityFromYearProperty(int year);
    /**
     * 
     * @return a String of the books contained in Depot
     */
    public String getBooksAsACoolString();
    /**
     * 
     * @return a String of the books contained in Depot
     */
    public StringProperty booksAsACoolStringProperty();
    /**
     * 
     * @param isbns is a list of isbns(as Strings)
     * @return a map of StandardBook, Integer matching with all isbns in input
     */
    public Map<StandardBook, Integer> getBooksFromStandardBookIsbn(List<String> isbns);
    /**
     * 
     * @param isbnsAndQuantities is a list of isbns and quantities(as Pair String, Integer)
     * @return a map of StandardBook, Integer matching with all isbns and quantities in input
     */
    public Map<StandardBook, Integer> getBooksFromStandardBookIsbnAndQuantity(
            List<Pair<String, Integer>> isbnsAndQuantities);
    /**
     * 
     * @return all the books of the depot, with their quantities
     */
    public Map<StandardBook, Integer> getBooks();
    /**
     * 
     * @return a list of the books contained in depot
     */
    List<StandardBook> getStandardBooksAsList();
    /**
     * 
     * @return a list of the isbns of the books contained in depot
     */
    List<String> getStandardBooksIsbns();
    /**
     * 
     * @return all the books contained in depot with their quantities as a list of Pair String, Integer
     */
    public List<Pair<String, Integer>> getBookIsbnsAsListOfPair();
    /**
     * 
     * @param pairBook is the book, with its quantity i want to add to the depot
     */
    public void addBook(Pair<StandardBook, Integer> pairBook);
}
