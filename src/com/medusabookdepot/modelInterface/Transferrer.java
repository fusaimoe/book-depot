/**
 * 
 */
package com.medusabookdepot.modelInterface;

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
     * @param transfer is the transfer Transferrer object has to do
     */
    public void doTransfer(Transferrer transferrer, boolean sender,Parcel parcel);
    /**
     * 
     * @param name is the name I want to give to the Transferrer Object
     */
    public void setName(String name);
    
}
