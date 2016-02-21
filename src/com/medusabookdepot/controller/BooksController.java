package com.medusabookdepot.controller;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.NoSuchElementException;
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
	 * @param ISBN 
	 * @param Name
	 * @param Year
	 * @param Pages
	 * @param Serie
	 * @param Genre
	 * @param Author
	 * @param <b>Price</b> in string format
	 * @throws IllegalArgumentException
	 *             if isbn already exists
	 */
	public void addBook(String isbn, String name, String year, String pages, String serie, String genre, String author,
			String price) throws IllegalArgumentException, IndexOutOfBoundsException {
		
		if (this.isInputValid(isbn, year, pages, serie, genre, author)) {

			books.add(new StandardBookImpl(isbn, name, Integer.parseInt(year), Integer.parseInt(pages), serie, genre, author, this.convertPrice(price)));
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
	 * @param Depot
	 * @param ISBN
	 * @param Name
	 * @param Year
	 * @param Pages
	 * @param Serie
	 * @param Genre
	 * @param Author
	 * @return Stream<StandardBook> : all books found with the passed filters
	 */
	public Stream<StandardBookImpl> searchBook(Optional<Depot> depot, Optional<String> isbn, Optional<String> name,
			Optional<String> year, Optional<String> pages, Optional<String> serie, Optional<String> genre,
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
			result = result.filter(e -> year.get().equals(e.getYear()));
		}
		if (pages.isPresent()) {
			result = result.filter(e -> pages.get().equals(e.getPages()));
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
	 * @param Book
	 * @throws NoSuchElementException if element is not present in books list
	 */
	public void removeBook(StandardBook book) throws NoSuchElementException{

		try {
			books.remove(book);
		} catch (Exception e) {
			throw new NoSuchElementException("No such element in list!");
		}
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
	 * @param ISBN
	 * @param Year
	 * @param Serie
	 * @param Genre
	 * @param Author
	 * @return <b>True</b> if input is valid, else a exception
	 * @throws IllegalArgumentException if the arguments are not valid
	 */
	public boolean isInputValid(String isbn, String year, String pages, String serie, String genre, String author) throws IllegalArgumentException{
		
		try {
			Integer.parseInt(year);
			Integer.parseInt(pages);
		} catch (Exception e) {
			throw new IllegalArgumentException("Year and pages must be integers!");
		}
		if (isbn.equals("") || year.equals("") || pages.equals("") || serie.equals("") || genre.equals("") || author.equals("")) {

			throw new IllegalArgumentException("The fields mustn't be empty!");
		}
		if (this.searchBook(Optional.empty(), Optional.of(isbn), Optional.empty(), Optional.empty(), Optional.empty(),
				Optional.empty(), Optional.empty(), Optional.empty()).count() >= 1) {
			throw new IllegalArgumentException(isbn + " is already present!");
		}
		if (isbn.length() != ISBN_LENGTH) {
			throw new IllegalArgumentException(isbn + " should be " + ISBN_LENGTH + " character!");
		}
		if (year.length() != 4
				|| Integer.parseInt(year) > java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)) {
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

	public void editISBN(StandardBook book, String isbn) {

		books.get(books.indexOf(book)).setIsbn(isbn);
		fileManager.saveDataToFile();
	}

	public void editTitle(StandardBook book, String title) {

		books.get(books.indexOf(book)).setTitle(title);
		fileManager.saveDataToFile();
	}

	public void editYear(StandardBook book, int year) {

		books.get(books.indexOf(book)).setYear(year);
		fileManager.saveDataToFile();
	}

	public void editPages(StandardBook book, int pages) {

		books.get(books.indexOf(book)).setPages(pages);
		fileManager.saveDataToFile();
	}

	public void editSerie(StandardBook book, String serie) {

		books.get(books.indexOf(book)).setSerie(serie);
		fileManager.saveDataToFile();
	}

	public void editGenre(StandardBook book, String genre) {

		books.get(books.indexOf(book)).setGenre(genre);
		fileManager.saveDataToFile();
	}

	public void editAuthor(StandardBook book, String author) {

		books.get(books.indexOf(book)).setAuthor(author);
		fileManager.saveDataToFile();
	}
	
	public void editPrice(StandardBook book, int price) {

		books.get(books.indexOf(book)).setPrice(price);
		fileManager.saveDataToFile();
	}
}
