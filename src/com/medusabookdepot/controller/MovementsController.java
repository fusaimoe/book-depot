package com.medusabookdepot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.PersonImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovementsController {
	
	private final ObservableList<TransferImpl> movements = FXCollections.observableArrayList();
	
	// Fields for file load and save, and for converting to PDF
	private final static String NAME = "transfers";
	private String directoryPath = System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot" + System.getProperty("file.separator");
	private String xmlPath = directoryPath + ".xml" + System.getProperty("file.separator") + NAME + ".xml";
	private String xslPath = directoryPath + ".xsl" + System.getProperty("file.separator") + NAME + ".xsl";
	private String pdfPath = directoryPath + NAME + new SimpleDateFormat("yyyyMMdd-HHmm-").format(new Date());
	private FileManager<TransferImpl> fileManager = new FileManager<>(movements, xmlPath, TransferImpl.class, NAME);
	
	public MovementsController(){
		StandardBookImpl libro = new StandardBookImpl("9788767547823", "Harry Potter", 1980, 7, "HP Saga", "Fantasy", "Feroce Macello", 2);
		Map<StandardBookImpl, Integer> mappa = new HashMap<>();
		mappa.put(libro, 3);
		DepotImpl esempio = new DepotImpl("prova", new HashMap<StandardBookImpl,Integer>());
		PersonImpl pino = new PersonImpl("pino", "via","numbero");
		TransferImpl transfer = new TransferImpl(esempio, pino, new java.util.Date(), mappa);
		movements.add(transfer);
		fileManager.saveDataToFile();
	}
		
	public void addMovements(TransferImpl transfer){
		
		movements.add(transfer);
	}
	
	/*
	 * Dalla GUI noto che si puo aggiungere solo un libro alla volta, per cui, questo metodo (fino a prossime istruzioni)
	 * creerà un movimento con una mappa (chiesta da Model) contenente un solo elemento preso in input.
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
	public ObservableList<TransferImpl> getAllMovements(){
		
		return movements;
	}
	
	/**
	 * Remove one ore more movements from list
	 * @param One ore more movements
	 * @throws <b>NoSuchElementException</b> if you are trying to remove a movement that not exists
	 */
	public void removeMovement(TransferImpl t) throws NoSuchElementException{
		
		try {
			movements.remove(t);
			fileManager.saveDataToFile();
			
		} catch (Exception e) {
			throw new NoSuchElementException("No such element in list!");
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
	
	/**
	 * Assign a passed tracking number at a movement
	 * @param <b>Tracking number</b> given by courier
	 * @param <b>The movement</b> to assign it
	 * @throws <b>IllegalArgumentException</b> if there is a registered movement with the same tracking number
	 */
	public void assignTrackingNumber(String tracking, Transfer movement) throws IllegalArgumentException{
		
		if(this.searchTrasferByTrackingNumber(tracking) != null){
			throw new IllegalArgumentException("The tracking number is already assigned!");
		}
		movement.setTrackingNumber(tracking);
	}
	
	/**
	 * Search for transfer from StandardBook
	 * @param StandardBookImpl
	 */
	public TransferImpl getTransferFromBook(StandardBookImpl book){
		for(TransferImpl transfer : movements){
			for(Entry<StandardBookImpl,Integer> entry : transfer.getBooks().entrySet()){
				if(entry.getKey().equals(book)) return transfer;
			}
		}
		return null;
	}
}
