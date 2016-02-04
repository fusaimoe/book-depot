package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.Map;
import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.StandardBook;

public abstract class CanSendTransferrerImpl extends TransferrerImpl implements CanSendTransferrer,Serializable {

    public CanSendTransferrerImpl(String name) {
        super(name);
    }
    /**
     * 
     */
    private static final long serialVersionUID = -1774043652685434484L;
    protected Map<StandardBook,Integer> books;
    public abstract boolean contains(Map<StandardBook, Integer> books);

}
