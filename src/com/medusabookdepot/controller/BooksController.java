package com.medusabookdepot.controller;

import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BooksController {
	
	private final ObservableList<StandardBookImpl> books = FXCollections.observableArrayList();
	
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
	public StandardBookImpl searchBook(StandardBook book){
		
		for(StandardBookImpl b:books){
			if(book.equals(b)){
				
				return b;
			}
		}
		return null;
	}
	
	/**
	 * Multifilter for books search.
	 * It search in the books list if you don't pass a depot, or in a specific depot if you pass it
	 * @param depot
	 * @param isbn
	 * @param name
	 * @param year
	 * @param pages
	 * @param serie
	 * @param genre
	 * @param author
	 * @return Stream<StandardBook> : all books found with the passed filters
	 */
	public Stream<StandardBookImpl> searchBook(Optional<Depot> depot, Optional<String> isbn, Optional<String> name, Optional<Integer> year, Optional<Integer> pages, Optional<String> serie, Optional<String> genre, Optional<String> author){
		
		Stream<StandardBookImpl> result = this.books.stream();
		
		//General searcher
		if(isbn.isPresent()){
			result = result.filter(e -> isbn.get().toString() == e.getIsbn());
		}
		if(name.isPresent()){
			result = result.filter(e -> e.getTitle().contains(name.get().toString()));
		}
		if(year.isPresent()){
			result = result.filter(e -> year.get() == e.getYear());
		}
		if(pages.isPresent()){
			result = result.filter(e -> pages.get() == e.getPages());
		}
		if(serie.isPresent()){
			result = result.filter(e -> serie.get().toString() == e.getSerie());
		}
		if(genre.isPresent()){
			result = result.filter(e -> genre.get().toString() == e.getGenre());
		}
		if(author.isPresent()){
			result = result.filter(e -> author.get().toString() == e.getAuthor());
		}
		
		//In depot
		if(depot.isPresent()){
			
			//result = result.filter(e -> depot.filter(f -> f.getQuantityFromStandardBook(e)<1)!=null);
		}
		
		 
		/* If there are not filters, return all books in one stream */
		return result;
	}
	
	/**
	 * Remove a book from the list
	 * @param book
	 */
	public void removeBook(StandardBook book){
		
		books.remove(book);
	}
	
	/**
	 * @return The list of saved books
	 */
	public ObservableList<StandardBookImpl> getBooks(){
		
		return books;
	}
}
