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
public abstract class TransferrerImpl implements Transferrer, Serializable {// template
                                                                            // method

    /**
     * 
     */
    private static final long serialVersionUID = -7387131023077698466L;
    protected StringProperty name;
    public TransferrerImpl(String name) {
        this.name = new SimpleStringProperty(name);
    }
    @Override
    public String getName() {
        String name=new String(this.name.get());//copia difensiva
        return name;
    }
    @Override
    public void setName(String name) {
        this.name.set(name);
    }
    @Override
    public StringProperty NameProperty() {
        StringProperty name=new SimpleStringProperty(this.name.get());//copia difensiva
        return name;
    }
    abstract public String toString();
    abstract public boolean isADepot();
}
