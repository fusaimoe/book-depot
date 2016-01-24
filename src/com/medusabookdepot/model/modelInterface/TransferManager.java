package com.medusabookdepot.model.modelInterface;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface TransferManager {
    
    
    public List<? extends Transfer>getAllTransfers();
    public void addTransfer(Transfer transfer);
    public void addTransfer(Transferrer sender,Transferrer receiver, Date leavingDate,Map<StandardBook,Integer> books);
    public List<Transfer> getTransfersFromFile(String filePath);
}
