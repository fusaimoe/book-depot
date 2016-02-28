package com.medusabookdepot.controller;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.CustomerImpl;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelImpl.TransferImpl;
import com.medusabookdepot.model.modelInterface.Transfer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MovementsController {

	private final ObservableList<TransferImpl> movements = FXCollections.observableArrayList();
	private final ObservableList<TransferImpl> tempData = FXCollections.observableArrayList();
	private final ObservableList<DepotImpl> depots = FXCollections.observableArrayList();
	private static MovementsController singMovements;

	// Fields for file load and save, and for converting to PDF for Transfers
	private final static String NAME = "movements";
	private FileManager<TransferImpl> fileManager = new FileManager<>(movements, TransferImpl.class, NAME);

	// Fields for file load and save, and for converting to PDF for Depots
	private final static String DEPOTS_NAME = "depots";
	private FileManager<DepotImpl> depotsFileManager = new FileManager<>(depots, DepotImpl.class, DEPOTS_NAME);

	private MovementsController() {
		super();
	}

	/**
	 * Load the MovementsController object or create a new if it doesn't exists
	 */
	public static MovementsController getInstanceOf() {

		return (MovementsController.singMovements == null ? new MovementsController()
				: MovementsController.singMovements);
	}

	public ObservableList<TransferImpl> getTempData() {

		return tempData;
	}

	/**
	 * Add a movement from Transfer object and save it in file
	 * 
	 * @param Movement
	 */
	public void addMovement(TransferImpl transfer) {

		// Aggiungo alla lista il movimento di libri e scrivo su file gli
		// aggiornamenti sia per i movimenti sia per i depots dato il possibile
		// cambiamento di quantità di libri se almeno uno dei soggetti coinvolti
		// è un depot

		tempData.add(transfer);
		movements.add(transfer);
		fileManager.saveDataToFile();
	}

	/*
	 * Dalla GUI noto che si puo aggiungere solo un libro alla volta, per cui,
	 * questo metodo (fino a prossime istruzioni) creerà un movimento con una
	 * mappa (chiesta da Model) contenente un solo elemento preso in input.
	 */

	/**
	 * Add a new transfer starting by passed strings
	 * 
	 * @param Sender
	 * @param Receiver
	 * @param Leaving
	 *            date
	 * @param Book
	 * @param Quantity
	 * @param Tracking
	 *            number
	 */
	public void addMovement(String sender, String receiver, Date leavingDate, String book, String quantity,
			String trackingNumber) {

		DepotImpl senderObj = new DepotImpl();
		DepotImpl receiverObj = new DepotImpl();
		StandardBookImpl bookObj = new StandardBookImpl();

		if (!this.isMovementValid(sender, receiver, leavingDate, book, quantity)) {
			throw new IllegalArgumentException();
		}

		if (!this.isADepot(sender) && !this.isADepot(receiver)) {
			throw new IllegalArgumentException("Receiver or sender must be a depot!");
		}

		for (StandardBookImpl b : BooksController.getInstanceOf().getBooks()) {
			if (b.getIsbn().equals(book)) {
				bookObj = b;
			}
		}

		if (this.isADepot(sender)) {
			for (DepotImpl d : depots) {
				if (d.getName().equals(sender)) {

					senderObj = d;
					d.setQuantityFromBook(bookObj, d.getQuantityFromStandardBook(bookObj) - Integer.parseInt(quantity));
					if (d.getQuantityFromStandardBook(bookObj) == 0) {
						DepotsController.getInstanceOf().editBookQuantity(d, bookObj, "0");
					}
				}
			}
		} else {
			senderObj.setName(sender);
		}

		if (this.isADepot(receiver)) {
			for (DepotImpl d : depots) {
				if (d.getName().equals(receiver)) {

					receiverObj = d;
					d.setQuantityFromBook(bookObj, d.getQuantityFromStandardBook(bookObj) + Integer.parseInt(quantity));
				}
			}
		} else {
			receiverObj.setName(receiver);
		}

		depotsFileManager.saveDataToFile();

		this.addMovement(new TransferImpl(senderObj, receiverObj, leavingDate, bookObj, trackingNumber,
				Integer.parseInt(quantity), this.getMovementType(sender, receiver)));
	}

	/**
	 * Remove one movement from list and adjust the books in depots
	 * 
	 * @param One
	 *            ore more movements
	 * @throws <b>NoSuchElementException</b>
	 *             if you are trying to remove a movement that not exists
	 */
	public void removeMovement(TransferImpl t) throws NoSuchElementException {

		try {
			if (this.isADepot(t.getSender().getName())) {
				for (DepotImpl d : depots) {
					if (d.getName().equals(t.getSender().getName())) {
						d.setQuantityFromBook(t.getBook(),
								d.getQuantityFromStandardBook(t.getBook()) + t.getQuantity());
					}
				}
			}

			if (this.isADepot(t.getReceiver().getName())) {
				for (DepotImpl d : depots) {
					if (d.getName().equals(t.getReceiver().getName())) {
						d.setQuantityFromBook(t.getBook(),
								d.getQuantityFromStandardBook(t.getBook()) - t.getQuantity());
					}
				}
			}

			movements.remove(t);
			fileManager.saveDataToFile();
			depotsFileManager.saveDataToFile();

		} catch (Exception e) {
			throw new NoSuchElementException("No such element in list!");
		}
	}

	public String getMovementType(String sender, String receiver) {
		if (this.isADepot(sender) && this.isADepot(receiver)) {
			return "Resupply";
		}
		for (CustomerImpl c : CustomersController.getInstanceOf().getCustomers()) {
			if (c.getName().equals(sender)) {
				if (c.isAPrinter() && this.isADepot(receiver)) {
					return "Resupply";
				}
				if (c.isALibrary() && this.isADepot(receiver)) {
					return "Return";
				}
				if (this.isADepot(sender) && !this.isADepot(sender)) {
					return "Sold";
				}
			}
		}
		return null;
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
	 * Search a string in ALL fields of movement object and add it to results if
	 * it is contained in field
	 * 
	 * @param Value
	 * @return A list of results
	 */
	public List<TransferImpl> searchMovements(String value) {
		List<TransferImpl> result = new ArrayList<>();

		this.movements.stream().forEach(e -> {

			if (e.getBook().getTitle().toLowerCase().contains(value.toLowerCase())
					|| e.getLeavingDate().toString().toLowerCase().contains(value.toLowerCase())
					|| Integer.toString(e.getQuantity()).contains(value)
					|| e.getReceiver().toString().toLowerCase().contains(value.toLowerCase())
					|| e.getSender().toString().toLowerCase().contains(value.toLowerCase())
					|| Integer.toString(e.getTotalPrice()).contains(value)
					|| e.getTrackingNumber().toLowerCase().contains(value)) {
				result.add(e);
			}
		});
		return result;
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
	 * Check if arguments passed are valid
	 * 
	 * @param Sender
	 * @param Receiver
	 * @param Leaving
	 *            date
	 * @param Book
	 * @param Quantity
	 * @return <b>True</b> if the arguments passed are valid
	 */
	public boolean isMovementValid(String sender, String receiver, Date leavingDate, String book, String quantity) {

		if (sender.equals("") || receiver.equals("") || book.equals("") || quantity.equals("")) {
			throw new IllegalArgumentException("The arguments must be not empty!");
		}

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
	 * @param Name
	 * @return <b>True</b> if really it is, else <b>False</b>
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
	 * @return A ObservableList of all books ISBNs
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
		CustomersController.getInstanceOf().getCustomers().stream().forEach(e -> {
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
		CustomersController.getInstanceOf().getCustomers().stream().forEach(e -> {
			customersAndDepotsString.add(e.getName());
		});
		DepotsController.getInstanceOf().getDepots().stream().forEach(e -> {
			customersAndDepotsString.add(e.getName());
		});
		return customersAndDepotsString;
	}

	/**
	 * Return a ObservableList with all ISBNs relative a title (A title may to
	 * more than one ISBN, like "Introduction in Java")
	 * 
	 * @param Title
	 */
	public ObservableList<String> getAllIsbnFromTitle(String title) {

		ObservableList<String> titles = FXCollections.observableArrayList();
		BooksController
				.getInstanceOf().searchBook(Optional.empty(), Optional.empty(), Optional.ofNullable(title),
						Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty())
				.forEach(e -> {
					titles.add(e.getIsbn());
				});
		return titles;
	}

	/**
	 * @return An OservableList contains all years that have movements
	 */
	public ObservableList<String> getYearsWithMovements() {
		ObservableList<String> years = FXCollections.observableArrayList();

		movements.stream().forEach(e -> {

			LocalDate date = e.getLeavingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			// Adding year to the list avoiding copies of the same year (an
			// observableHashSet doesn't exist in FXCollections)

			if (!years.stream().anyMatch(f -> f.equals(Integer.toString(date.getYear())))) {
				years.add(Integer.toString(date.getYear()));
			}
		});
		return years;
	}

	/**
	 * In base of sender return a list of possible receivers
	 * 
	 * @param Sender
	 *            name
	 * @return ObservableList with all possible receivers
	 */
	public ObservableList<String> getReceiversFromSender(String sender) {
		ObservableList<String> receivers = FXCollections.observableArrayList();

		DepotsController.getInstanceOf().getDepots().stream().forEach(e -> {
			if (e.getName().equals(sender)) {
				CustomersController.getInstanceOf().getCustomers().stream().forEach(f -> {
					if (!f.getName().equals(sender) && !f.isAPrinter()) {
						receivers.add(f.getName());
					}
				});
				DepotsController.getInstanceOf().getDepots().stream().forEach(g -> {
					if (!g.getName().equals(sender)) {
						receivers.add(g.getName());
					}
				});
			}
		});
		CustomersController.getInstanceOf().getCustomers().stream().forEach(e -> {

			if (e.getName().equals(sender)) {
				if (e.isADepot()) {
					CustomersController.getInstanceOf().getCustomers().stream().forEach(f -> {
						if (!f.getName().equals(sender) && !f.isAPrinter()) {
							receivers.add(f.getName());
						}
					});
				}

				if (e.isALibrary() || e.isAPrinter()) {
					CustomersController.getInstanceOf().getCustomers().stream().forEach(f -> {
						if (!f.getName().equals(sender) && f.isADepot()) {
							receivers.add(f.getName());
						}
					});
					DepotsController.getInstanceOf().getDepots().stream().forEach(g -> {
						if (!receivers.contains(sender)) {
							receivers.add(g.getName());
						}
					});
				}
			}
		});

		return receivers;
	}

	/**
	 * Takes a transferrer name and if it's a depot put in list all books inside
	 * its, if it's a library put in list all book titles
	 * 
	 * @param Transferrer
	 *            name
	 * @return A ObservableList of titles
	 */
	public ObservableList<String> getTitleFromTransferrer(String transferrer) {
		ObservableList<String> titles = FXCollections.observableArrayList();

		if (this.isADepot(transferrer)) {
			DepotsController.getInstanceOf().getDepots().stream().forEach(e -> {
				if (e.getName().equals(transferrer)) {
					e.getBooks().entrySet().stream().forEach(f -> {
						titles.add(f.getKey().getTitle());
					});
				}
			});
		} else {
			CustomersController.getInstanceOf().getCustomers().stream().forEach(e -> {
				if (e.getName().equals(transferrer) && e.isALibrary()) {
					BooksController.getInstanceOf().getBooks().stream().forEach(f -> {
						titles.add(f.getTitle());
					});
				}
			});
		}

		return titles;
	}

}
