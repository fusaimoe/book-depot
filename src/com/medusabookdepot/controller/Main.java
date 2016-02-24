package com.medusabookdepot.controller;

//NON ELIMINARE GLI INPORT NON USATI, SONO USATI NEI TEST COMMENTATI

import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.test.Test;
import com.medusabookdepot.view.*;

/*
 * Suppressed unused warnings because they are used in commented tests
 */
@SuppressWarnings("unused")

public class Main {

    private final Menu firstFrame;

    public Main() {
            this.firstFrame = new Menu();
            this.firstFrame.mainGui(new String[]{""});
    }
    
    public static void main(String[] args) throws Exception {
        
        Test prova=new Test();
        prova.test();
    	if(prova.getResult())System.out.println("test riuscito");
    	else {
    	    throw new Exception("failed test");
    	}
        new Main();
    	//BooksController testBooksCtrl = BooksController.getInstanceOf();
    	//DepotsController testDepotCtrl = DepotsController.getInstanceOf();
    	
        //Creo 4 titoli con valore dei campi diversi
        //testBooksCtrl.addBook("9788767547899", "Harry Potter", "1980", "7", "HP Saga", "Fantasy", "Feroce Macello", "2");
        //testBooksCtrl.addBook("9788712309888", "Il Signore degli Anelli", "2002", "290", "LOTR Saga", "Romanzo", "Croccolino Lorenzo", "15");
        //testBooksCtrl.addBook("9788712378955", "Il Codice da Vinci", "2012","322", "Libri", "Horror", "Colombo Andrea", "22");
        //testBooksCtrl.addBook("9788712378966", "Hunger Games", "2015", "322", "HG Saga", "Fantascienza", "Cecchetti Giulia", "22");

        //Creo un depot con i libri sopra creati assegnando ad ognuno una quantità nel depot
        //medusiniDepot.addDepot("Medusini", medusiniBook.getBooks(), new int[]{10,2,33,22});
        
        //Faccio stampare il toString di tutti i depot esistenti
        /*for(int n=0;n<medusiniDepot.depots.size();n++){
        	System.out.println(medusiniDepot.depots.get(n));
        }*/
        
        //Cerco se il libro che ha come parte del nome "Cicci" esiste nella nostra lista, se tutto va bene dovrebbe trovarmelo 
        //Stream<StandardBook> bb =medusiniDepot.searchBook(Optional.empty(), Optional.empty(), Optional.of("Cicci"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        // <-- DECOMMENTARE I TEST CHE SI DESIDERA ESEGUIRE --> \\
        
        /*Filtri: isbn*/
    	/*for(StandardBookImpl b:testBooksCtrl.getBooks()){
    		System.out.println(b.getTitle());
    	}
        Stream<StandardBookImpl> bb =testBooksCtrl.searchBook(Optional.empty(), Optional.of("9"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        */
        /*Filtri: name*/
        //Stream<StandardBookImpl> bb =testBooksCtrl.searchBook(Optional.empty(), Optional.empty(), Optional.of("S"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*Filtri: name*/
        //Stream<StandardBook> bb =testBooksCtrl.searchBook(Optional.empty(), Optional.empty(), Optional.of("cci"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*Filtri: year*/
        //Stream<StandardBook> bb =testBooksCtrl.searchBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(1980), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*Filtri: year + name*/
        //Stream<StandardBook> bb =testBooksCtrl.searchBook(Optional.empty(), Optional.empty(), Optional.of("cci"), Optional.of(1980), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*Filtri: depot + name + year*/
        //Stream<StandardBook> bb =testBooksCtrl.searchBook(Optional.of(medusiniDepot.depots.get(0)), Optional.empty(), Optional.empty(), Optional.of(1980), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*System.out.println("All books saved:");
        for(StandardBook e: medusiniBook.getBooks()){
        	System.out.println("# " + e.getTitle());
        }*/
        
        /*Stampa risultati trovati*/
        /*System.out.println("\nAll books found:");
        bb.forEach(e->{
        	System.out.println("F: " + e.getTitle());
        });*/
    }

}
