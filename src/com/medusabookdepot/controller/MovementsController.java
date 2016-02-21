package com.medusabookdepot.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovementsController {
	
	private final ObservableList<Transfer> movements = FXCollections.observableArrayList();
	
	public void addMovements(Transfer transfer){
		
		movements.add(transfer);
	}
	
	/*
	 * Dalla GUI noto che si puo aggiungere solo un libro alla volta, per cui, questo metodo (fino a prossime istruzioni)
	 * creerà un movimento con una mappa (chiesta da Model) contenente un solo elempento preso in input.
	 */
	public void addMovements(CanSendTransferrer sender,Transferrer receiver,Date leavingDate, StandardBookImpl book, int quantity){
		
		Map<StandardBookImpl,Integer> books = new HashMap<>();
		books.put(book, quantity);
		this.addMovements(new TransferImpl(sender, receiver, leavingDate, books));
	}
	
	/**
	 * Return the list of movements
	 * @return Movements list
	 */
	public List<Transfer> getAllMovements(){
		
		return movements;
	}
	
	/**
	 * Remove one ore more movements from list
	 * @param One ore more movements
	 */
	public void removeMovements(Transfer... mov){
		
		for(Transfer t:mov){
			try {
				movements.remove(t);
			} catch (Exception e) {
				throw new NoSuchElementException("No such element in list");
			}
		}
	}
	
	/**
	 * Search a transfer in list by tracking number
	 * @param <b>Tracking number</b>
	 * @return The transfer if it was found, else <b>null</b>
	 */
	public Transfer searchTrasferByTrackingNumber(String tracking){
		
		for(Transfer t:movements){
			if(t.getTrackingNumber().equals(tracking)){
				return t;
			}
		}
		return null;
	}
}
