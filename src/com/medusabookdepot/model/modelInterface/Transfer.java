/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import java.util.Map;

/**
 * @author Marcello_Feroce
 *
 */
public interface Transfer {
        
        public Transferrer getSender();
        
        public Transferrer getReceiver();
        
        public Map<StandardBook,Integer> getBooks();
        
        public java.sql.Date getLeavingDate();
        
        // ========================================== \\
        
        public void setSender(Transferrer sender);
        
        public void setReceiver(Transferrer receiver);
        
        public void setBooks(Map<StandardBook,Integer> books);
        
        public void setLeavingDate(java.sql.Date leavingDate);
        /*
         * 
         */
        public String getTrackingNumber();
        
        public int getQuantity();
        
        public int getQuantityFromBook(StandardBook book);
        
        public int getTotalPrice();
        
        public void setTrackingNumber(String trackingnumber);
        
        public void setQuantityFromBook(StandardBook book,int quantity);
        
        public void replaceBook(StandardBook oldBook, StandardBook newBook);

}
