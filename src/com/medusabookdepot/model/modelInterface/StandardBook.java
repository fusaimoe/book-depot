/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * @author marcello
 *
 */
public interface StandardBook {

    /**
     * @return The book's name
     */
    public String getTitle();
    public StringProperty getTitleProperty();
    
    public String getIsbn();
    public StringProperty getIsbnProperty();
    
    public int getYear();
    public IntegerProperty getYearProperty();
    
    public int getPages();
    public IntegerProperty getPagesProperty();
    
    public String getSerie();
    public StringProperty getSerieProperty();
    
    public String getGenre();
    public StringProperty getGenreProperty();
    
    public String getAuthor();
    public StringProperty getAuthorProperty();
    
    public int getPrice();
    public IntegerProperty getPriceProperty();
    
    // ====================================== \\
    
    public void setTitle(String title);
    
    public void setIsbn(String isbn);
    
    public void setYear(int year);
    
    public void setPages(int pages);
    
    public void setSerie(String serie);
    
    public void setGenre(String genre);
    
    public void setAuthor(String author);
    
    public void setPrice(int price);
}
