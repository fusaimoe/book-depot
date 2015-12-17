/**
 * 
 */
package com.medusabookdepot.modelInterface;

/**
 * @author marcello
 *
 */
public interface Customer extends Transferrer{
    /**
     * 
     * @return the address of the Customer
     */
    public String getAddress();
    /**
     * 
     * @return the telephone number of the Customer
     */
    public String getTelephoneNumber();
    /**
     * @param address is the new address of the Customer
     * this method set the new address as current address
     */
    public void setAddress(String address);
    /**
     * @param address is the new telephone number of the Customer
     * this method set the new telephone number as current address
     */
    public void setTelephoneNumber(String telephonenumber);
    
}
