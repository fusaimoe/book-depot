package com.medusabookdepot.model.modelInterface;

import java.util.Map;

public interface CanSendTransferrer extends Transferrer{
    
    public boolean contains(Map<StandardBook,Integer> books);
}
