package com.medusabookdepot.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;

import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.model.modelInterface.Transfer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovementsController {

	private final ObservableList<TransferImpl> movements = FXCollections.observableArrayList();
	private final ObservableList<DepotImpl> depots = FXCollections.observableArrayList();
	private static MovementsController singMovements;

	// Fields for file load and save, and for converting to PDF for Transfers
	private final static String NAME = "transfers";
	private String directoryPath = System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot"
			+ System.getProperty("file.separator");
	private String xmlPath = directoryPath + ".xml" + System.getProperty("file.separator") + NAME + ".xml";
	private String xslPath = directoryPath + ".xsl" + System.getProperty("file.separator") + NAME + ".xsl";
	private String pdfPath = directoryPath + NAME + new SimpleDateFormat("yyyyMMdd-HHmm-").format(new Date());
	private FileManager<TransferImpl> fileManager = new FileManager<>(movements, xmlPath, TransferImpl.class, NAME);

	// Fields for file load and save, and for converting to PDF for Depots
	private final static String DEPOTS_NAME = "depots";
	private String depotsDirectoryPath = System.getProperty("user.home") + System.getProperty("file.separator")
			+ "book-depot" + System.getProperty("file.separator");
	private String depotsXmlPath = depotsDirectoryPath + ".xml" + System.getProperty("file.separator") + DEPOTS_NAME
			+ ".xml";
	private FileManager<DepotImpl> depotsFileManager = new FileManager<>(depots, depotsXmlPath, DepotImpl.class,
			DEPOTS_NAME);

	private MovementsController() {
		super();
	}

	public static MovementsController getInstanceOf() {

		return (MovementsController.singMovements == null ? new MovementsController()
				: MovementsController.singMovements);
	}

	public void addMovements(TransferImpl transfer) {

		// Aggiungo alla lista il movimento di libri e scrivo su file gli
		// aggiornamenti sia per i movimenti sia per i depots dato il possibile
		// cambiamento di quantità di libri se almeno uno dei soggetti coinvolti
		// è un depot

		movements.add(transfer);
		fileManager.saveDataToFile();
	}

	/*
	 * Dalla GUI noto che si puo aggiungere solo un libro alla volta, per cui,
	 * questo metodo (fino a prossime istruzioni) creerà un movimento con una
	 * mappa (chiesta da Model) contenente un solo elemento preso in input.
	 */
	public void addMovements(String sender, String receiver, Date leavingDate, String book, String quantity,
			String trackingNumber) {

		DepotImpl senderObj = new DepotImpl();
		DepotImpl receiverObj = new DepotImpl();
		StandardBookImpl bookObj = new StandardBookImpl();

		// Controllo se il movimento ha tutti i presupposti per essere eseguito
		if (!this.isMovementValid(sender, receiver, leavingDate, book, quantity)) {
			throw new IllegalArgumentException();
		}
		// Una volta arrivati qui si tenga presente che si da per scontato il
		// fatto che tutto può essere fatto senza ulteriori controlli!

		// Cerco il libro corrispondente alla stringa passatami
		for (StandardBookImpl b : BooksController.getInstanceOf().getBooks()) {
			if (b.getIsbn().equals(book)) {
				bookObj = b;
			}
		}

		// Controllo se è un depot oppure no, per sapere se togliere i libri da
		// un eventuale magazzino di partenza oppure solo settare il nome del
		// sender
		if (this.isADepot(sender)) {
			for (DepotImpl d : depots) {
				if (d.getName().equals(sender)) {

					// Ora che so che è un depot e l'ho trovato rimpiazzo il
					// libro nella lista in modo da aggiornare la sua quantità
					senderObj = d;
					d.setQuantityFromBook(bookObj, d.getQuantityFromStandardBook(bookObj) - Integer.parseInt(quantity));
					if (d.getQuantityFromStandardBook(bookObj) == 0) {
						// Rimuovo il libro se è arrivato a quantità zero
						BooksController.getInstanceOf().removeBook(bookObj);
					}
				}
			}
		} else {
			// Se non è un depot setto solamente il nome del mittente (sender)
			senderObj.setName(sender);
		}

		// Faccio la stessa cosa con il ricevente ma aggiungo
		if (this.isADepot(receiver)) {
			for (DepotImpl d : depots) {
				if (d.getName().equals(receiver)) {

					receiverObj = d;
					// d.getBooks().put(bookObj,
					// d.getQuantityFromStandardBook(bookObj) +
					// Integer.parseInt(quantity));
					d.setQuantityFromBook(bookObj, d.getQuantityFromStandardBook(bookObj) + Integer.parseInt(quantity));
				}
			}
		} else {
			receiverObj.setName(receiver);
		}

		depotsFileManager.saveDataToFile();

		// A questo punto non ci resta che creare l'oggetto Transfer e con
		// addMovements scrivere su file i vari cambiamenti
		this.addMovements(new TransferImpl(senderObj, receiverObj, leavingDate, bookObj, trackingNumber,
				Integer.parseInt(quantity)));
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
			if (t.getTrackingNumber().toLowerCase().equals(tracking.toLowerCase())) {
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
			if (transfer.getBook().getIsbn().equals(book)) {
				return transfer;
			}
		}
		return null;
	}

	/**
	 * Check if arguments passed are good
	 * 
	 * @param sender
	 * @param receiver
	 * @param leavingDate
	 * @param book
	 * @param quantity
	 * @return
	 */
	public boolean isMovementValid(String sender, String receiver, Date leavingDate, String book, String quantity) {
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
					for (StandardBookImpl b : bc.getBooks()) {
						if (b.getIsbn().equals(book)) {
							bookFound = true;
							if (d.getQuantityFromStandardBook(b) < Integer.parseInt(quantity)) {
								throw new IllegalArgumentException("There are not so many books in depot!");
							}
						}
					}
				}
			}
		} else {
			// Se non è un magazzino non si può controllare se ha realmente il
			// libro
			bookFound = true;
		}
		if (!bookFound) {
			throw new IllegalArgumentException("Book not found!");
		}
		if (leavingDate.after(c.getTime())) {
			throw new IllegalArgumentException("Invalid leaving date!");
		}

		return true;
	}

	/**
	 * Check if the string passed is a depot name
	 * 
	 * @param name
	 * @return
	 */
	public boolean isADepot(String name) {
		name = name.toLowerCase();
		for (DepotImpl d : depots) {
			if (d.getName().toLowerCase().equals(name)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Return the list of movements
	 * 
	 * @return Movements list
	 */
	public ObservableList<TransferImpl> getMovements() {

		return movements;
	}

	/**
	 * @return A ObservableList of all books titles
	 */
	public ObservableList<String> getTitlesString() {

		ObservableList<String> booksString = FXCollections.observableArrayList();
		BooksController.getInstanceOf().getBooks().stream().forEach(e -> {
			booksString.add(e.getTitle());
		});
		return booksString;
	}

	/**
	 * @return A ObservableList of all books isbns
	 */
	public ObservableList<String> getIsbnsString() {

		ObservableList<String> booksString = FXCollections.observableArrayList();
		BooksController.getInstanceOf().getBooks().stream().forEach(e -> {
			booksString.add(e.getIsbn());
		});
		return booksString;
	}

	/**
	 * @return A ObservableList of only CanSendTransferrer(s) name
	 */
	public ObservableList<String> getCanSendTransferrersString() {

		ObservableList<String> canSendTransferrersString = FXCollections.observableArrayList();
		CustomerController.getInstanceOf().getCustomers().stream().forEach(e -> {
			if (e.isALibrary() || e.isAPrinter()) {
				canSendTransferrersString.add(e.getName());
			}
		});
		DepotsController.getInstanceOf().getDepots().stream().forEach(e -> {
			canSendTransferrersString.add(e.getName());
		});
		return canSendTransferrersString;
	}

	/**
	 * @return A ObservableList of all customers name
	 */
	public ObservableList<String> getCustomersAndDepotsString() {

		ObservableList<String> customersAndDepotsString = FXCollections.observableArrayList();
		CustomerController.getInstanceOf().getCustomers().stream().forEach(e -> {
			customersAndDepotsString.add(e.getName());
		});
		DepotsController.getInstanceOf().getDepots().stream().forEach(e -> {
			customersAndDepotsString.add(e.getName());
		});
		return customersAndDepotsString;
	}

}
