/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class TransferrerImpl implements Transferrer,Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = -7387131023077698466L;
    protected String name;
    protected boolean isDepot;
    public TransferrerImpl(String name) {
        this.name=name;
    }
    @Override
    public String getName() {
        return this.name;
    }
    
    
    @Override
    public void setName(String name) {
        this.name=name;
    }
}
