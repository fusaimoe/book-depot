package com.medusabookdepot.model.modelInterface;

import java.util.List;
import java.util.Map;

public interface DepotManager {
    public List<? extends Depot>getAllDepots();
    public void addDepot(Depot depot);
    public void addDepot(String name,Map<StandardBook,Integer> books);
    void removeDepot(Transfer transfer);
    void removeDepot(int index);
}
