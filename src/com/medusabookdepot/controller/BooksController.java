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
	private static BooksController singBook;

	private BooksController() {

		super();
	}

	public static BooksController getInstanceOf() {

		return (BooksController.singBook == null ? new BooksController() : BooksController.singBook);
	}

	/**
	 * Add a new book in the list
	 * 
	 * @param isbn
	 * @param name
	 * @param year
	 * @param pages
	 * @param serie
	 * @param genre
	 * @param author
	 * @param price
	 * @throws IllegalArgumentException
	 *             if isbn already exists
	 */
	public void addBook(String isbn, String name, int year, int pages, String serie, String genre, String author,
			int price) throws IllegalArgumentException {

		System.out.println(isbn + " " + this.bookIsPresent(isbn));
		if (this.bookIsPresent(isbn)) {

			throw new IllegalArgumentException("FAIL: " + isbn + " is already present!");
		}
		books.add(new StandardBookImpl(isbn, name, year, pages, serie, genre, author, price));
	}

	/**
	 * Search a book in the list
	 * 
	 * @param book
	 * @return StandardBook if it found the book, else null
	 */
	public StandardBookImpl searchBook(StandardBook book) {

		for (StandardBookImpl b : books) {
			if (book.equals(b)) {

				return b;
			}
		}
		return null;
	}

	/**
	 * Multifilter for books search. It search in the books list if you don't
	 * pass a depot, or in a specific depot if you pass it
	 * 
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
	public Stream<StandardBookImpl> searchBook(Optional<Depot> depot, Optional<String> isbn, Optional<String> name,
			Optional<Integer> year, Optional<Integer> pages, Optional<String> serie, Optional<String> genre,
			Optional<String> author) {

		Stream<StandardBookImpl> result = this.books.stream();

		// General searcher
		if (isbn.isPresent()) {
			result = result.filter(e -> isbn.get().toString().equals(e.getIsbn()));
		}
		if (name.isPresent()) {
			result = result.filter(e -> e.getTitle().contains(name.get().toString()));
		}
		if (year.isPresent()) {
			result = result.filter(e -> year.get() == e.getYear());
		}
		if (pages.isPresent()) {
			result = result.filter(e -> pages.get() == e.getPages());
		}
		if (serie.isPresent()) {
			result = result.filter(e -> serie.get().toString().equals(e.getSerie()));
		}
		if (genre.isPresent()) {
			result = result.filter(e -> genre.get().toString().equals(e.getGenre()));
		}
		if (author.isPresent()) {
			result = result.filter(e -> author.get().toString().equals(e.getAuthor()));
		}

		// In depot
		if (depot.isPresent()) {

			result = result.filter(e -> depot.filter(f -> f.getQuantityFromStandardBook(e) < 1) != null);
		}

		/* If there are not filters, return all books in one stream */
		return result;
	}

	/**
	 * Remove a book from the list
	 * 
	 * @param book
	 */
	public void removeBook(StandardBook book) {

		books.remove(book);
	}

	/**
	 * Search isbn and remove relative book
	 * 
	 * @param isbn
	 *            of book to remove
	 */
	public void removeBook(String isbn) {

		this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {
					this.removeBook(e);
				});
	}

	/**
	 * Search if a book is present
	 * 
	 * @param isbn
	 *            to search
	 * @return true if is present, else false
	 */
	public boolean bookIsPresent(String isbn) {

		return (this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty()).count() == 1);
	}

	/**
	 * @return The list of saved books
	 */
	public ObservableList<StandardBookImpl> getBooks() {

		return books;
	}
}
