package com.medusabookdepot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepotsController {

	private final ObservableList<Depot> depots = FXCollections.observableArrayList();
	private final Map<StandardBookImpl, Integer> booksInDepot = new HashMap<>();
	private static DepotsController singDepots;

	private DepotsController() {
		// lettura da file
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
	public boolean addDepot(String name, List<StandardBookImpl> book, int... quantity) {

		for (Depot d : depots) {
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
	}

	/**
	 * Search a depot in the list
	 * 
	 * @param depot
	 * @return null if it doesn't find one, else the object found
	 */
	public Depot searchDepot(Depot depot) {

		for (Depot d : depots) {
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
	public void removeDepot(Depot depot) {

		depots.remove(depot);
	}
}
