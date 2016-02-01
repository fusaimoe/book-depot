package com.medusabookdepot.controller;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MedusaMovements {
	
	private final ObservableList<Transfer> movements = FXCollections.observableArrayList();
	
	public void addMovements(Transfer transfer){
		
		movements.add(transfer);
	}
	
	/*
	 * Dalla GUI noto che si puo aggiungere solo un libro alla volta, per cui, questo metodo (fino a prossime istruzioni)
	 * creerà un movimento con una mappa (chiesta da Model) contenente un solo elempento preso in input.
	 */
	public void addMovements(Transferrer sender,Transferrer receiver,Date leavingDate, StandardBook book, int quantity){
		
		Map<StandardBook,Integer> books = new HashMap<>();
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
				new NoSuchElementException();
			}
		}
	}
}
