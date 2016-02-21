package com.medusabookdepot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepotsController {

	private final ObservableList<DepotImpl> depots = FXCollections.observableArrayList();
	private final Map<StandardBookImpl, Integer> booksInDepot = new HashMap<>();
	private static DepotsController singDepots;
	
	// Fields for file load and save, and for converting to PDF
	private final static String NAME = "depots";
	private String directoryPath = System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot" + System.getProperty("file.separator");
	private String xmlPath = directoryPath + ".xml" + System.getProperty("file.separator") + NAME + ".xml";
	private String xslPath = directoryPath + ".xsl" + System.getProperty("file.separator") + NAME + ".xsl";
	private String pdfPath = directoryPath + NAME + new SimpleDateFormat("yyyyMMdd-HHmm-").format(new Date());
	private FileManager<DepotImpl> fileManager = new FileManager<>(depots, xmlPath, DepotImpl.class, NAME);
	
	
	public DepotsController() {
		
		//test scrittura provvisorio
		StandardBookImpl libro = new StandardBookImpl("9788767547823", "Harry Potter", 1980, 7, "HP Saga", "Fantasy", "Feroce Macello", 2);
		Map<StandardBookImpl, Integer> mappa = new HashMap<>();
		mappa.put(libro, 3);
		DepotImpl esempio = new DepotImpl("prova", mappa);
		depots.add(esempio);
		System.out.println("Salvataggio riuscito @" + xmlPath);
		fileManager.saveDataToFile();
		//fine test
		
	}

	public static DepotsController getInstanceOf() {

		return (DepotsController.singDepots == null ? new DepotsController() : DepotsController.singDepots);
	}

	/**
	 * Add one ore more before created depots in the list
	 * 
	 * @param depot
	 */
	// non sono convinto della sua utilità...
	public void addDepot(DepotImpl... depot) {

		for (DepotImpl n : depot) {

			depots.add(n);
		}
	}

	/**
	 * Add new depot in the list
	 * 
	 * @param name:
	 *            name of new book depot
	 * @param book:
	 *            a book list to insert in new book depot
	 * @param quantity:
	 *            the quantity of before books
	 * @return true: all right, false: error in parameters
	 * 
	 */
	/*
	 * Con questo modulo ho voluto poter far inserire all'utente più libri alla
	 * creazione del deposito anzichè inserirne uno solo per poi aggiungerne
	 * altri dopo la creazione
	 */
	/*public boolean addDepot(String name, List<StandardBookImpl> book, int... quantity) {

		for (DepotImpl d : depots) {
			if (d.getName().equals(name)) {
				throw new IllegalArgumentException("Fail: " + name + "is already exists!");
			}
		}

		booksInDepot.clear();

		if (book.size() != quantity.length && !book.isEmpty() && quantity.length == 0) {
			return false;
		}

		for (StandardBookImpl n : book) {

			booksInDepot.put(n, quantity[book.indexOf(n)]);
		}

		addDepot(new DepotImpl(name, booksInDepot));

		return true;
	}*/

	/**
	 * Search a depot in the list
	 * 
	 * @param depot
	 * @return null if it doesn't find one, else the object found
	 */
	public DepotImpl searchDepot(DepotImpl depot) {

		for (DepotImpl d : depots) {
			if (depot.equals(d)) {
				return d;
			}
		}
		return null;
	}

	/**
	 * Remove a depot from the list
	 * 
	 * @param depot
	 */
	public void removeDepot(DepotImpl depot) {

		depots.remove(depot);
	}
}
