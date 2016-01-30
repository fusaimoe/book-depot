package com.medusabookdepot.model.modelInterface;

import java.util.List;
import java.util.Map;

public interface TransferManager {
    
    
    public List<? extends Transfer>getAllTransfers();
    public void addTransfer(Transfer transfer);
    public void addTransfer(Transferrer sender,Transferrer receiver, java.util.Date leavingDate,Map<StandardBook,Integer> books);
    void removeTransfer(Transfer transfer);
    void removeTransfer(int index);
}
