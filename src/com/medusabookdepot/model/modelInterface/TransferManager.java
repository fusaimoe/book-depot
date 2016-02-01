package com.medusabookdepot.model.modelInterface;

import java.io.File;
import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;

public interface TransferManager {
    
    
    public List<? extends Transfer>getAllTransfers();
    public ObservableList<? extends Transfer> getAllTransfersProperty();
    
    public void addTransfer(final Transfer transfer);
    public void addTransfer(final Transferrer sender,final Transferrer receiver,final java.util.Date leavingDate,final Map<StandardBook,Integer> books);
    void removeTransfer(final Transfer transfer);
    void removeTransfer(final int index);
    public void registerTransfersFromFile(final File f);
}
