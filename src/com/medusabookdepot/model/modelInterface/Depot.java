/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public interface Depot extends CanSendTransferrer{

    /**
     * 
     * @return the quantity of all books contained in this depot
     */
    public int getQuantity();
    
    public IntegerProperty getQuantityProperty();
    /**
     * 
     * @param book is the book I want look for its quantity
     * @return the quantity of the book in the depot
     */
    public int getQuantityFromStandardBook(StandardBook book);
    
    public IntegerProperty getQuantityFromStandardBookProperty(StandardBook book);
    /**
     * 
     * @param title is the title of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that title
     */
    public int getQuantityFromTitle(String title);
    
    public IntegerProperty getQuantityFromTitleProperty(String title);
    /**
     * 
     * @param author is the author of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that author
     */
    public int getQuantityFromAuthor(String author);
    
    public IntegerProperty getQuantityFromAuthorProperty(String author);
    /**
     * 
     * @param year is the year of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that year
     */
    public int getQuantityFromYear(int year);
    
    public IntegerProperty getQuantityFromYearProperty(int year);
    
    public String getBooksAsString();
    
    public StringProperty getBooksAsStringProperty();
}
