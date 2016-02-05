package com.medusabookdepot.controller;

import com.medusabookdepot.model.modelImpl.LibraryImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MedusaLibrary {
	
	/**
	 * The list that contain all saved libraries
	 */
	private final ObservableList<LibraryImpl> libraries = FXCollections.observableArrayList();
	
	/**
	 * Add a new library to the list of all libraries
	 * @param name
	 * @param address
	 * @param telephoneNumber
	 */
	public void addLibrary(String name, String address, String telephoneNumber){
		
		libraries.add(new LibraryImpl(name, address, telephoneNumber));
	}
	/**
	 * Remove a library from the list
	 * @param lib
	 */
	public void deleteLibrary(LibraryImpl lib){
		
		libraries.remove(lib);
	}
}
