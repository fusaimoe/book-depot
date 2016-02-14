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
    public StringProperty titleProperty();

    public String getIsbn();
    public StringProperty isbnProperty();

    public int getYear();
    public IntegerProperty yearProperty();

    public int getPages();
    public IntegerProperty pagesProperty();

    public String getSerie();
    public StringProperty serieProperty();

    public String getGenre();
    public StringProperty genreProperty();

    public String getAuthor();
    public StringProperty authorProperty();

    public int getPrice();
    public IntegerProperty priceProperty();

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
