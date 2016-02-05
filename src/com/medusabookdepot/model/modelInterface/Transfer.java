/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import java.util.Map;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public interface Transfer {
        
        public CanSendTransferrer getSender();
        
        public Transferrer getReceiver();
        
        public Map<StandardBook,Integer> getBooks();
        public Map<StandardBook,IntegerProperty> getBooksProperty();
        
        public java.util.Date getLeavingDate();
        
        // ========================================== \\
        
        public void setSender(CanSendTransferrer sender);
        
        public void setReceiver(Transferrer receiver);
        
        public void setBooks(Map<StandardBook,Integer> books);
        
        public void setLeavingDate(java.sql.Date leavingDate);
        /*
         * 
         */
        public String getTrackingNumber();
        
        public int getQuantity();
        public IntegerProperty getQuantityProperty();
        
        public int getQuantityFromBook(StandardBook book);
        public IntegerProperty getQuantityFromBookProperty();
        
        public int getTotalPrice();
        public IntegerProperty getTotalPriceProperty();
        
        public void setTrackingNumber(String trackingnumber);
        
        public void setQuantityFromBook(StandardBook book,int quantity);
        
        public void replaceBook(StandardBook oldBook, StandardBook newBook);

        public String getBooksAsString();
        public StringProperty getBooksAsStringProperty();
        
        public boolean isArrived();
        public void setArrived(boolean arrived);

}
