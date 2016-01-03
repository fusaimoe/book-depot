/**
 * 
 */
package com.medusabookdepot.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelImpl.LibraryImpl;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.Library;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.view.viewImpl.FirstFrameImpl;
import com.medusabookdepot.view.viewImpl.Menu;
import com.medusabookdepot.view.viewinterface.FirstFrameInterface;

public class Medusa {

    private final Menu/*in futuro sarà qualcosa come MenuInterface*/ firstframe;
    private final List<Depot> depots = new ArrayList<Depot>();
    private Map<StandardBook,Integer> booksInDepot = new HashMap<StandardBook,Integer>();
    private final List<StandardBook> books = new ArrayList<StandardBook>();
    private final List<Library> libraries = new ArrayList<Library>();

    public Medusa() {
            this.firstframe.begin();
    }
    
    /*
     * =============== DEPOT ===============
     */
    
    /**
     * Add one ore more before created depots in the list
     * @param depot
     */
    //non sono convinto della sua utilità...
	public void addDepot(DepotImpl... depot){
		
		for(DepotImpl n : depot){
			
			depots.add(n);
		}
	}
	/**
	 * Add new depot in the list
	 * @param name: name of new book depot
	 * @param book: a book list to insert in new book depot
	 * @param quantity: the quantity of before books
	 * @return true: all right, false: error in parameters
	 * 
	 */
	/*
	 * Con questo modulo ho voluto poter far inserire all'utente più libri alla creazione del deposito
	 * anzichè inserirne uno solo per poi aggiungerne altri dopo la creazione
	 */
	public boolean addDepot(String name, ArrayList<StandardBook> book, ArrayList<Integer> quantity){
		
		//libero la mappa dai vecchi inserimenti
		booksInDepot.clear();
		
		//controllo che il numero di libri passati sia uguale ale quantità, se no ritorno false
		if(book.size()!=quantity.size() && !book.isEmpty() && !quantity.isEmpty()){
			
			return false;
		}
		
		//per ogni libro passato ...
		for(StandardBook n: book){
			
			//aggiungo alla mappa il suddetto libro e la sua quantità corrispondente
			booksInDepot.put(n, quantity.get(book.indexOf(n)));
		}
		
		//dopodiche aggiungo alla lista dei depot un nuovo depot con le caratteristiche appena modellate
		addDepot(new DepotImpl(name, booksInDepot));
		
		//ritorno true come conferma di inserimento
		return true;
	}
	
	/**
	 * Search a depot in the list
	 * @param depot
	 * @return null if it doesn't find one, else the object found
	 */
	public Depot searchDepot(Depot depot){
		
		for(Depot d:depots){
			if(depot.equals(d)){
				return d;
			}
		}
		return null;
	}
	
	/**
	 * Remove a depot from the list
	 * @param depot
	 */
	public void removeDepot(Depot depot){
		
		depots.remove(depot);
	}
	
	/*
	 * =============== BOOK ===============
	 */
	
	/**
	 * Add a new book in the list
	 * @param isbn
	 * @param name
	 * @param year
	 * @param pages
	 * @param serie
	 * @param genre
	 * @param author
	 * @param price
	 */
	public void addBook(String isbn,String name, int year, int pages, String serie, String genre, String author, int price){
		
		books.add(new StandardBookImpl(isbn,name, year, pages, serie, genre, author, price));
	}
	
	/**
	 * Search a book in the list
	 * @param book
	 * @return StandardBook if it found the book, else null
	 */
	public StandardBook searchBook(StandardBook book){
		
		for(StandardBook b:books){
			if(book.equals(b)){
				
				return b;
			}
		}
		return null;
	}
	
	/**
	 * Remove a book from the list
	 * @param book
	 */
	public void removeBook(StandardBook book){
		
		books.remove(book);
	}
	
	/*
	 * =============== LIBRARY ===============
	 */
	
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
	public void deleteLibrary(Library lib){
		
		libraries.remove(lib);
	}
	
    public static void main(String[] args) {
        new Medusa();

    }

}
