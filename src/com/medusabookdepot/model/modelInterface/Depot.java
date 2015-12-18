/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

/**
 * @author Marcello_Feroce
 *
 */
public interface Depot extends Transferrer{

    /**
     * 
     * @return the quantity of all books contained in this depot
     */
    public int getQuantity();
    /**
     * 
     * @param book is the book I want look for its quantity
     * @return the quantity of the book in the depot
     */
    public int getQuantityFromStandardBook(StandardBook book);
    /**
     * 
     * @param title is the title of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that title
     */
    public int getQuantityFromTitle(String title);
    /**
     * 
     * @param author is the author of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that author
     */
    public int getQuantityFromAuthor(String author);
    /**
     * 
     * @param year is the year of all the books I want look for their quantity
     * @return the quantity of the books in the depot, having that year
     */
    public int getQuantityFromYear(int year);
    
}
