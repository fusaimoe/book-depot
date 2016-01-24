/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import com.medusabookdepot.model.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class TransferrerImpl implements Transferrer{

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
