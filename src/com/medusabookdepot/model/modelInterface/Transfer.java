/**
 * 
 */
package com.medusabookdepot.model.modelInterface;
/**
 * @author Marcello_Feroce
 *
 */
public interface Transfer {
        
        public Transferrer getSender();
        
        public Transferrer getReceiver();
        
        public Parcel getParcel();
        
        public java.sql.Date getLeavingDate();
        
        // ========================================== \\
        
        public void setSender(Transferrer sender);
        
        public void setReceiver(Transferrer receiver);
        
        public void setParcel(Parcel parcel);
        
        public void setLeavingDate(java.sql.Date leavingDate);

}
