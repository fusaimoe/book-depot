package com.medusabookdepot.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Stream;

import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class DepotsController {

	private final ObservableList<DepotImpl> depots = FXCollections.observableArrayList();
	private static DepotsController singDepots;
	
	// Fields for file load and save, and for converting to PDF
	private final static String NAME = "depots";
	private String directoryPath = System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot" + System.getProperty("file.separator");
	private String xmlPath = directoryPath + ".xml" + System.getProperty("file.separator") + NAME + ".xml";
	private String xslPath = directoryPath + ".xsl" + System.getProperty("file.separator") + NAME + ".xsl";
	private String pdfPath = directoryPath + NAME + new SimpleDateFormat("yyyyMMdd-HHmm-").format(new Date());
	private FileManager<DepotImpl> fileManager = new FileManager<>(depots, xmlPath, DepotImpl.class, NAME);
	
	
	public DepotsController() {
		
	}

	/**
	 * Singleton for DepotsController
	 */
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
	 * Add a empty depot from name
	 * @param <b>Name of new depot</b>
	 * @throws IllegalArgumentException if another depot is registered with the same name
	 */
	public void addDepot(String name) throws IllegalArgumentException{
		
		if(this.searchDepot(name).count()>=1){
			throw new IllegalArgumentException("Depot " + name +" is already present!");
		}
		depots.add(new DepotImpl(name, new HashMap<>()));
		fileManager.saveDataToFile();
	}

	/**
	 * Search a depot in the list
	 * 
	 * @param Depot name
	 * @return Empty stream if none depot was found, else the depot
	 */
	public Stream<DepotImpl> searchDepot(String name) {

		Stream<DepotImpl> result = this.depots.stream();
		result = result.filter(e -> e.getName().contains(name));
		return result;
	}
	
	

	/**
	 * Remove a depot from the list
	 * @param Depot
	 * @throws <b>NoSuchElementException</b> if you are trying to remove a depot that not exists
	 */
	public void removeDepot(DepotImpl depot) throws NoSuchElementException{

		try {
			depots.remove(depot);
		} catch (Exception e) {
			throw new NoSuchElementException("No such element in list!");
		}
		fileManager.saveDataToFile();
	}
	
	/**
	 * Get the observable list of depots
	 */
	public ObservableList<DepotImpl> getDepots() {

		return depots;
	}
	
	public void editName(DepotImpl depot, String name) {

		depots.get(depots.indexOf(depot)).setName(name);
		fileManager.saveDataToFile();
	}
}
