/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import java.util.List;
import java.util.Map;

/**
 * @author Marcello_Feroce
 *
 */
public interface Transferrer {

    /**
     * 
     * @return the name of the Transferrer object
     */
    public String getName();
    /**
     * 
     * @param opposite is the transferrer i want to send or receive
     * @param sender identifies if the objects that does transfer is sender
     * @param parcel is the parcel of book to receive or send
     */
    public void doTransfer(Transferrer opposite, boolean sender, Map<StandardBook,Integer> books);
    /**
     * 
     * @param name is the name I want to give to the Transferrer Object
     */
    public void setName(String name);
    
    public String getNewTrackingNumber();
    
}
