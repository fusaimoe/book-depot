/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;

import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public class StandardBookImpl implements StandardBook,Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7759011458602273856L;
	private final StringProperty isbn;
	private final StringProperty title;
	private final IntegerProperty year;
	private final IntegerProperty pages;
	private final StringProperty serie;
	private final StringProperty genre;
	private final StringProperty author;
	private final IntegerProperty price;
    
	/**
     * Default constructor.
     */
	public StandardBookImpl(){
		 this(null, null, 0, 0, null, null, null, 0);
	}
	/**
     * Constructor
	 *
	 *@param isbn
     *@param title
	 *@param year
	 *@param pages
	 *@param serie
	 *@param genre
	 *@param author
	 *@param price
     */
    public StandardBookImpl(String isbn, String title, int year, int pages, String serie, String genre, String author, int price) {
        this.isbn = new SimpleStringProperty(isbn);
		this.title = new SimpleStringProperty(title);
		this.year = new SimpleIntegerProperty(year);
		this.pages = new SimpleIntegerProperty(pages);
		this.serie = new SimpleStringProperty(serie);
		this.genre = new SimpleStringProperty(genre);
		this.author = new SimpleStringProperty(author);
		this.price = new SimpleIntegerProperty(price);
    }

    public String getIsbn() {
        return isbn.get();
    }
	
    public StringProperty isbnProperty() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public String getTitle() {
        return title.get();
    }
	
    public StringProperty titleProperty() {
        return title;
    }
	
    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getYear() {
        return year.get();
    }
	
    public IntegerProperty yearProperty() {
        return year;
    }

    public void setYear(int year) {
        this.year.set(year);
    }

    public int getPages() {
        return pages.get();
    }
	
    public IntegerProperty pagesProperty() {
        return pages;
    }
	
    public void setPages(int pages) {
        this.pages.set(pages);
    }

    public String getSerie() {
        return serie.get();
    }

    public StringProperty serieProperty() {
        return serie;
    }
	
    public void setSerie(String serie) {
        this.serie.set(serie);
    }

    public String getGenre() {
        return genre.get();
    }

    public StringProperty genreProperty() {
        return genre;
    }
	
    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    public String getAuthor() {
        return author.get();
    }

    public StringProperty authorProperty() {
        return author;
    }
	
    public void setAuthor(String author) {
        this.author.set(author);
    }

    public int getPrice() {
        return price.get();
    }

    public IntegerProperty priceProperty() {
        return price;
    }

    public void setPrice(int price) {
        this.price.set(price);
    }

    public String toString() {
        return this.title+"\n"+this.isbn+"\n"+this.pages+"\n"+this.price+"\n"+this.year+"\n"+this.author+"\n"+this.genre+"\n"+this.serie+"\n";
    }
	
}
