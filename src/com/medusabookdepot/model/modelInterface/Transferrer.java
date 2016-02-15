/**
 * 
 */
package com.medusabookdepot.model.modelInterface;

import javafx.beans.property.StringProperty;

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

    public StringProperty NameProperty();
    /**
     * 
     * @param name
     *            is the name I want to give to the Transferrer Object
     */
    public void setName(String name);
    public boolean isADepot();

}
