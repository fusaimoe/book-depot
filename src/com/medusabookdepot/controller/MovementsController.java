package com.medusabookdepot.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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
import com.medusabookdepot.model.modelInterface.Transfer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovementsController {

	private final ObservableList<TransferImpl> movements = FXCollections.observableArrayList();

	// Fields for file load and save, and for converting to PDF
	private final static String NAME = "transfers";
	private String directoryPath = System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot"
			+ System.getProperty("file.separator");
	private String xmlPath = directoryPath + ".xml" + System.getProperty("file.separator") + NAME + ".xml";
	private String xslPath = directoryPath + ".xsl" + System.getProperty("file.separator") + NAME + ".xsl";
	private String pdfPath = directoryPath + NAME + new SimpleDateFormat("yyyyMMdd-HHmm-").format(new Date());
	private FileManager<TransferImpl> fileManager = new FileManager<>(movements, xmlPath, TransferImpl.class, NAME);

	private final ObservableList<DepotImpl> depots = FXCollections.observableArrayList();
	private final static String DEPOTS_NAME = "depots";
	private String depotsDirectoryPath = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "book-depot" + System.getProperty("file.separator");
	private String depotsXmlPath = depotsDirectoryPath + ".xml" + System.getProperty("file.separator") + DEPOTS_NAME
			+ ".xml";
	private FileManager<DepotImpl> depotsFileManager = new FileManager<>(depots, depotsXmlPath, DepotImpl.class,
			DEPOTS_NAME);

	public MovementsController() {
		StandardBookImpl libro = new StandardBookImpl("9788767547823", "Harry Potter", 1980, 7, "HP Saga", "Fantasy",
				"Feroce Macello", 2);
		Map<StandardBookImpl, Integer> mappa = new HashMap<>();
		mappa.put(libro, 3);
		DepotImpl esempio = new DepotImpl("prova", new HashMap<StandardBookImpl, Integer>());
		PersonImpl pino = new PersonImpl("pino", "via", "numbero");
		TransferImpl transfer = new TransferImpl(esempio, pino, new java.util.Date(), mappa);
		movements.add(transfer);
		fileManager.saveDataToFile();
		depotsFileManager.loadDataFromFile();
	}

	public void addMovements(TransferImpl transfer) {

		movements.add(transfer);
		fileManager.saveDataToFile();
		depotsFileManager.saveDataToFile();
	}

	/*
	 * Dalla GUI noto che si puo aggiungere solo un libro alla volta, per cui,
	 * questo metodo (fino a prossime istruzioni) creerà un movimento con una
	 * mappa (chiesta da Model) contenente un solo elemento preso in input.
	 */
	public void addMovements(String sender, String receiver, Date leavingDate, String book, String quantity) {

		DepotImpl senderObj = new DepotImpl();
		DepotImpl receiverObj = new DepotImpl();
		StandardBookImpl bookObj = new StandardBookImpl();
		
		
		if (!this.isMovementValid(sender, receiver, leavingDate, book, quantity)) {
			throw new IllegalArgumentException();
		}
		
		for(StandardBookImpl b:BooksController.getInstanceOf().getBooks()){
			if(b.getIsbn().equals(book)){
				bookObj = b;
			}
		}

		if (this.isADepot(sender)) {
			for (DepotImpl d : depots) {
				if (d.getName().equals(sender)) {
					
					senderObj = d;
					d.getBooks().put(bookObj, d.getQuantityFromStandardBook(bookObj) - Integer.parseInt(quantity));
				}
			}
		}else{
			senderObj.setName(sender);
		}
		
		if (this.isADepot(receiver)) {
			for (DepotImpl d : depots) {
				if (d.getName().equals(receiver)) {

					receiverObj = d;
					d.getBooks().put(bookObj, d.getQuantityFromStandardBook(bookObj) - Integer.parseInt(quantity));
				}
			}
		}else{
			receiverObj.setName(receiver);
		}

		Map<StandardBookImpl, Integer> books = new HashMap<>();
		books.put(bookObj, Integer.parseInt(quantity));
		this.addMovements(new TransferImpl(senderObj, receiverObj, leavingDate, books));
	}

	/**
	 * Return the list of movements
	 * 
	 * @return Movements list
	 */
	public ObservableList<TransferImpl> getAllMovements() {

		return movements;
	}

	/**
	 * Remove one ore more movements from list
	 * 
	 * @param One
	 *            ore more movements
	 * @throws <b>NoSuchElementException</b>
	 *             if you are trying to remove a movement that not exists
	 */
	public void removeMovement(TransferImpl t) throws NoSuchElementException {

		try {
			movements.remove(t);
			fileManager.saveDataToFile();

		} catch (Exception e) {
			throw new NoSuchElementException("No such element in list!");
		}
	}

	/**
	 * Search a transfer in list by tracking number
	 * 
	 * @param <b>Tracking
	 *            number</b>
	 * @return The transfer if it was found, else <b>null</b>
	 */
	public Transfer searchTrasferByTrackingNumber(String tracking) {

		for (Transfer t : movements) {
			if (t.getTrackingNumber().equals(tracking)) {
				return t;
			}
		}
		return null;
	}

	/**
	 * Assign a passed tracking number at a movement
	 * 
	 * @param <b>Tracking
	 *            number</b> given by courier
	 * @param <b>The
	 *            movement</b> to assign it
	 * @throws <b>IllegalArgumentException</b>
	 *             if there is a registered movement with the same tracking
	 *             number
	 */
	public void assignTrackingNumber(String tracking, Transfer movement) throws IllegalArgumentException {

		if (this.searchTrasferByTrackingNumber(tracking) != null) {
			throw new IllegalArgumentException("The tracking number is already assigned!");
		}
		movement.setTrackingNumber(tracking);
	}

	/**
	 * Search for transfer from StandardBook
	 * 
	 * @param StandardBookImpl
	 */
	public TransferImpl getTransferFromBook(StandardBookImpl book) {
		for (TransferImpl transfer : movements) {
			for (Entry<StandardBookImpl, Integer> entry : transfer.getBooks().entrySet()) {
				if (entry.getKey().equals(book))
					return transfer;
			}
		}
		return null;
	}

	/**
	 * Check if arguments passed are good
	 * @param sender
	 * @param receiver
	 * @param leavingDate
	 * @param book
	 * @param quantity
	 * @return
	 */
	public boolean isMovementValid(String sender, String receiver, Date leavingDate,
			String book, String quantity) {
		try {
			Integer.parseInt(quantity);
		} catch (Exception e) {
			throw new IllegalArgumentException("Quantity must be a integer!");
		}
		
		Calendar c = Calendar.getInstance();
		boolean bookFound = false;
		
		if (this.isADepot(sender)) {
			for (DepotImpl d : depots) {
				if (d.getName().equals(sender)) {
					BooksController bc = BooksController.getInstanceOf();
					for(StandardBookImpl b:bc.getBooks()){
						if(b.getIsbn().equals(book)){
							bookFound = true;
							if (d.getQuantityFromStandardBook(b) < Integer.parseInt(quantity)) {
								throw new IllegalArgumentException("There are not so many books in depot!");
							}
						}
					}
				}
			}
		}
		if(!bookFound){
			throw new IllegalArgumentException("Book not found!");
		}
		if (c.getTime().after(leavingDate)) {
			throw new IllegalArgumentException("Invalid leaving date!");
		}

		return true;
	}

	/**
	 * 
	 * @param name
	 * @return
	 */
	public boolean isADepot(String name) {

		for (DepotImpl d : depots) {
			if (d.getName().equals(name)) {
				return true;
			}
		}
		return false;
	}
}
