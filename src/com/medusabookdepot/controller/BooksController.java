package com.medusabookdepot.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.controller.files.ConvertXML2PDF;
import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.StandardBookImpl;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class BooksController {

	private static final int ISBN_LENGTH = 13;
	private final ObservableList<StandardBookImpl> books = FXCollections.observableArrayList();
	private static BooksController singBook;

	// Fields for file load and save, and for converting to PDF
	private final static String NAME = "books";
	private String directoryPath = System.getProperty("user.home") + System.getProperty("file.separator") + "book-depot" + System.getProperty("file.separator");
	private String xmlPath = directoryPath + ".xml" + System.getProperty("file.separator") + NAME + ".xml";
	private String xslPath = directoryPath + ".xsl" + System.getProperty("file.separator") + NAME + ".xsl";
	private String pdfPath = directoryPath + NAME + new SimpleDateFormat("yyyyMMdd-HHmm-").format(new Date());
	private FileManager<StandardBookImpl> fileManager = new FileManager<>(books, xmlPath, StandardBookImpl.class, NAME);

	private BooksController() {

		super();
	}
	
	public static BooksController getInstanceOf() {

		return (BooksController.singBook == null ? new BooksController() : BooksController.singBook);
	}

	/**
	 * Convert price in int
	 * 
	 * @param Book
	 *            price in string format
	 * @return Price in int
	 * @throws IllegalArgumentException
	 *             and IndexOutOfBoundsException
	 */
	public int convertPrice(String price) throws IllegalArgumentException, IndexOutOfBoundsException {
		
		if(price.equals("")) throw new IllegalArgumentException("The price field mustn't be empty!");

		if (!price.contains(".") && !price.contains(",")) {
			price += ".00";
		} else if (price.charAt(price.length() - 2) == '.' || price.charAt(price.length() - 2) == ',') {
			price += "0";
		} else if (price.charAt(price.length() - 3) == '.' || price.charAt(price.length() - 3) == ',') {
			// Correct input, nothing to do
		} else throw new IllegalArgumentException("Price format not valid! (IE 12.50)");

		return Integer.parseInt(new StringBuilder(price).deleteCharAt(price.length() - 3).toString());
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
			String price) throws IllegalArgumentException, IndexOutOfBoundsException {

		if (this.isInputValid(isbn, year, serie, genre, author)) {

			books.add(new StandardBookImpl(isbn, name, year, pages, serie, genre, author, this.convertPrice(price)));
			fileManager.saveDataToFile();
		}
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
		fileManager.saveDataToFile();
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
	 * 
	 * @param isbn
	 * @param year
	 * @param serie
	 * @param genre
	 * @param author
	 * @return
	 */
	public boolean isInputValid(String isbn, int year, String serie, String genre, String author) {
		if (isbn.equals("") || Integer.toString(year).equals("") || serie.equals("") || genre.equals("") || author.equals("")) {

			throw new IllegalArgumentException("The fields mustn't be empty!");
		}
		if (this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).count() >= 1) {
			throw new IllegalArgumentException(isbn + " is already present!");
		}
		if (isbn.length() != ISBN_LENGTH) {
			throw new IllegalArgumentException(isbn + " should be " + ISBN_LENGTH + " character!");
		}
		if (Integer.toString(year).length() != 4
				|| year > java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)) {
			throw new IllegalArgumentException(
					"Wait a minute, Doc. Are you telling me that you built a time machine... out of Delorian?!");
		}

		return true;
	}

	/**
	 * @return The list of saved books
	 */
	public ObservableList<StandardBookImpl> getBooks() {

		return books;
	}
	
	/**
	 * Convert the xml file in a PDF
	 * @throws IOException
	 */
	public void convert() throws IOException {

		if (!new File(xslPath).exists())
			throw new IOException("XSL Template doesn't exist");
		if (!new File(xmlPath).exists())
			throw new IllegalArgumentException("XML File doesn't exist");

		ConvertXML2PDF converter = new ConvertXML2PDF(xmlPath, xslPath, pdfPath);

		converter.open();
	}

	public void editISBN(String oldISBN, String newISBN) {

		this.searchBook(Optional.empty(), Optional.of(oldISBN), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {

					if (this.isInputValid(newISBN, e.getYear(), e.getSerie(), e.getGenre(), e.getAuthor())) {
						e.setIsbn(newISBN);
					}
				});
		fileManager.saveDataToFile();
	}

	public void editTitle(String isbn, String newTitle) {

		this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {

					e.setTitle(newTitle);
				});
		fileManager.saveDataToFile();
	}

	public void editYear(String isbn, int newYear) {

		this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {

					if (this.isInputValid(isbn, newYear, e.getSerie(), e.getGenre(), e.getAuthor())) {
						e.setYear(newYear);
					}
				});
		fileManager.saveDataToFile();
	}

	public void editPages(String isbn, int newPages) {

		this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {

					e.setPages(newPages);
				});
		fileManager.saveDataToFile();
	}

	public void editSerie(String isbn, String newSerie) {

		this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {

					e.setSerie(newSerie);
					;
				});
		fileManager.saveDataToFile();
	}

	public void editGenre(String isbn, String newGenre) {

		this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {

					e.setGenre(newGenre);
				});
		fileManager.saveDataToFile();
	}

	public void editAuthor(String isbn, String newAuthor) {

		this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {

					e.setAuthor(newAuthor);
				});
		fileManager.saveDataToFile();
	}
	
	public void editPrice(String isbn, String newPrice) {

		this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).forEach(e -> {

					e.setPrice(this.convertPrice(newPrice));
				});
		fileManager.saveDataToFile();
	}
}
