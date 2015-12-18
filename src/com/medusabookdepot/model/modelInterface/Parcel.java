/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

/**
 * @author marcello
 *
 */
public interface Parcel {
    
    public String getTrackingNumber();
    
    public int getQuantity();
    
    public int getQuantityFromBook(StandardBook book);
    
    public int getTotalPrice();
    
    public void setTrackingNumber(String trackingnumber);
    
    public void setQuantityFromBook(StandardBook book,int quantity);
    
    public void replaceBook(StandardBook oldBook, StandardBook newBook);
}
