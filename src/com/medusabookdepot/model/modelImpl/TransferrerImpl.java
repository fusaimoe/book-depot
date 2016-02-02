/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.Transferrer;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public abstract class TransferrerImpl implements Transferrer,Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -7387131023077698466L;
    protected StringProperty name;
    protected boolean isDepot;
    public TransferrerImpl(String name) {
        this.name=new SimpleStringProperty(name);
    }
    @Override
    public String getName() {
        return this.getNameProperty().get();
    }
    @Override
    public StringProperty getNameProperty() {
        return this.name;
        
    }
    @Override
    public void setName(String name) {
        this.name.set(name);
    }
    
    abstract public String toString();
}
