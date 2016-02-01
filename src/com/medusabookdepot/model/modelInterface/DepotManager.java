package com.medusabookdepot.model.modelInterface;

import java.util.List;
import java.util.Map;

import javafx.collections.ObservableList;

public interface DepotManager {
    public List<? extends Depot>getAllDepots();
    public ObservableList<? extends Depot> getAllDepotsProperty();
    
    public void addDepot(final Depot depot);
    
    public void addDepot(final String name,Map<StandardBook,Integer> books);
    
    void removeDepot(final Transfer transfer);
    
    void removeDepot(int index);
}
