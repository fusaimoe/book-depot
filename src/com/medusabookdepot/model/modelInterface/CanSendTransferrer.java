package com.medusabookdepot.model.modelInterface;

import java.util.Map;
/**
 * This interface represents a Transferrer that can send
 * @author Marcello_Feroce
 * 
 *
 */
public interface CanSendTransferrer extends Transferrer {

    /**
     * 
     * @param books is the map of StandardBook, Integer i want to verify if it is contained in CanSendTransferrer
     * @return true if books is contained, else false
     */
    public boolean containsBooks(Map<StandardBook, Integer> books);
}
