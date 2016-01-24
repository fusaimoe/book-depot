package com.medusabookdepot.model.modelInterface;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelImpl.TransferrerImpl;

public interface FactoryManager {
    
    
    public FactoryManager getInstanceOfFactoryManger();
    public List<? extends Transfer>getAllTransfers();
    public void addTransfer(Transfer transfer);
    public void addTransfer(Transferrer sender,Transferrer receiver, Date leavingDate,Map<StandardBook,Integer> books);
    public List<Transfer> getTransfersFromFile(String filePath);
}
