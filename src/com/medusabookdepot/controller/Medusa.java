/**
 * 
 */
package com.medusabookdepot.controller;

import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.LibraryImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.view.viewImpl.FirstFrameImpl;
import com.medusabookdepot.view.viewinterface.FirstFrameInterface;

public class Medusa {

    private final FirstFrameInterface firstframe;
    private final List<DepotImpl> depots;

    public Medusa() {
            this.firstframe=new FirstFrameImpl();
            firstframe.welcome();
            this.depots=firstframe.setInizilizationFrame();
    }
    
	public void addDepot(DepotImpl depot){
		
		depots.add(depot);
	}
	
	/** Add a new book to the list of all books */
	public StandardBookImpl standardBookImpl(String isbn,String name, int year, int pages, String serie, String genre, String author, int price){
		
		
		return new StandardBookImpl(isbn,name, year, pages, serie, genre, author, price);
	}
	
	/** Add a new library to the list of all libraries */
	public LibraryImpl libraryImpl(String name, String address, String telephoneNumber){
		
		return new LibraryImpl(name, address, telephoneNumber);
	}
    public static void main(String[] args) {
        new Medusa();

    }

}
