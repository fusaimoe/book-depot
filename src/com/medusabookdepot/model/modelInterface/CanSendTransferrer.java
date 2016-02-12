package com.medusabookdepot.model.modelInterface;

import java.util.Map;

public interface CanSendTransferrer extends Transferrer {

    public boolean containsBooks(Map<StandardBook, Integer> books);
}
