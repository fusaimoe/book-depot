/**
 * 
 */
package com.medusabookdepot.controller;

import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.model.modelInterface.*;
import com.medusabookdepot.view.viewImpl.*;

public class Medusa {

    private final Menu/*in futuro sarà qualcosa come MenuInterface*/ firstframe;

    public Medusa() {
            this.firstframe = new Menu();
            this.firstframe.mainGui(new String[]{""});
    }
    
    public static void main(String[] args) {
        
    	new Medusa();
    	MedusaStandardBook medusiniBook = new MedusaStandardBook();
    	MedusaDepot medusiniDepot = new MedusaDepot();
    	
        //Creo 4 titoli con valore dei campi diversi
        medusiniBook.addBook("9788767547823", "La fabbrica dei bambocci", 1980, 7, "Serie pico", "Avventure", "Feroce Macello", 2);
        medusiniBook.addBook("9788712309897", "Cicci posticci", 2002, 290, "", "Romantici", "Croccolino Lorenzo", 15);
        medusiniBook.addBook("9788712378922", "The poveracci", 2017, 322, "Serie pocanzi", "Horror", "Colombo Andrea", 22);
        medusiniBook.addBook("9788712378922", "Censurare: una passione", 2017, 322, "Serie rompini", "Fantascienza", "Cecchetti Giulia", 22);

        //Creo un depot con i libri sopra creati assegnando ad ognuno una quantità nel depot
        medusiniDepot.addDepot("Medusini", medusiniBook.getAllBooks(), new int[]{10,2,33,22});
        
        //Faccio stampare il toString di tutti i depot esistenti
        /*for(int n=0;n<medusiniDepot.depots.size();n++){
        	System.out.println(medusiniDepot.depots.get(n));
        }*/
        
        //Cerco se il libro che ha come parte del nome "Cicci" esiste nella nostra lista, se tutto va bene dovrebbe trovarmelo 
        //Stream<StandardBook> bb =medusiniDepot.searchBook(Optional.empty(), Optional.empty(), Optional.of("Cicci"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        // <-- DECOMMENTARE I TEST CHE SI DESIDERA ESEGUIRE --> \\
        
        /*Filtri: isbn*/
        //Stream<StandardBook> bb =medusiniDepot.searchBook(Optional.empty(), Optional.of("9788712378922"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*Filtri: name*/
        Stream<StandardBook> bb =medusiniBook.searchBook(Optional.empty(), Optional.empty(), Optional.of("Cicci"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*Filtri: name*/
        //Stream<StandardBook> bb =medusiniDepot.searchBook(Optional.empty(), Optional.empty(), Optional.of("cci"), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*Filtri: year*/
        //Stream<StandardBook> bb =medusiniDepot.searchBook(Optional.empty(), Optional.empty(), Optional.empty(), Optional.of(1980), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*Filtri: year + name*/
        //Stream<StandardBook> bb =medusiniDepot.searchBook(Optional.empty(), Optional.empty(), Optional.of("cci"), Optional.of(1980), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        /*Filtri: depot + name + year*/
        //Stream<StandardBook> bb =medusiniDepot.searchBook(Optional.of(medusiniDepot.depots.get(0)), Optional.empty(), Optional.empty(), Optional.of(1980), Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        
        System.out.println("All books saved:");
        for(StandardBook e: medusiniBook.getAllBooks()){
        	System.out.println("# " + e.getTitle());
        }
        
        //Stampa risultati trovati
        System.out.println("\nAll books found:");
        bb.forEach(e->{
        	System.out.println("F: " + e.getTitle());
        });
    }

}
