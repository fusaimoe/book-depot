/**
 * 
 */
package com.medusabookdepot.modelInterface;

/**
 * @author marcello
 *
 */
public interface StandardBook {

    /**
     * @return The book's name
     */
    public String getName();
    
    public String getISBN();
    
    public int getYear();
    
    public int getPages();
    
    public String getSerie();
    
    public String getGenre();
    
    public String getAuthor();
    
    public int getPrice();
    
    // ====================================== \\
    
    public void setName(String name);
    
    public void setISBN(String isbn);
    
    public void setYear(int year);
    
    public void setPages(int pages);
    
    public void setSerie(String serie);
    
    public void setGenre(String genre);
    
    public void setAuthor(String author);
    
    public void setPrice(int price);
}
