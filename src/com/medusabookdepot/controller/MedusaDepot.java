package com.medusabookdepot.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.medusabookdepot.model.modelImpl.DepotImpl;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MedusaDepot {
	
	private final ObservableList<Depot> depots = FXCollections.observableArrayList();
	private final Map<StandardBook,Integer> booksInDepot = new HashMap<>();
	
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
	public boolean addDepot(String name, List<StandardBook> book, int... quantity){
		
		//libero la mappa dai vecchi inserimenti
		booksInDepot.clear();
		
		//controllo che il numero di libri passati sia uguale ale quantità, se no ritorno false
		if(book.size()!=quantity.length && !book.isEmpty() && quantity.length==0){
			
			return false;
		}
		
		//per ogni libro passato ...
		for(StandardBook n: book){
			
			//aggiungo alla mappa il suddetto libro e la sua quantità corrispondente
			booksInDepot.put(n, quantity[book.indexOf(n)]);
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
}
