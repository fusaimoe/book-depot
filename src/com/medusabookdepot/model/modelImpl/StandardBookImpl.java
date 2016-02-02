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
public class StandardBookImpl implements StandardBook, Serializable{

    /**
     * 
     */
    private static final long serialVersionUID = 7759011458602273856L;
    private StringProperty title;
    private StringProperty isbn;
    private IntegerProperty year;
    private IntegerProperty pages;
    private StringProperty serie;
    private StringProperty genre;//e.g. "Avventura", or "Thriller, Giallo, Azione"
    private StringProperty author;
    private IntegerProperty price;
    
    public StandardBookImpl(String isbn,String title, int year, int pages, String serie, String genre, String author, int price) {
        this.isbn=new SimpleStringProperty(isbn);
        this.title=new SimpleStringProperty(title);
        this.year=new SimpleIntegerProperty(year);
        this.pages=new SimpleIntegerProperty(pages);
        this.serie=new SimpleStringProperty(serie);
        this.genre=new SimpleStringProperty(genre);
        this.author=new SimpleStringProperty(author);
        this.price=new SimpleIntegerProperty(price);
    }
    public StandardBookImpl(String isbn) {
        this.isbn=new SimpleStringProperty(isbn);
    }
    public StandardBookImpl(String isbn,String title,String author) {
        this.isbn=new SimpleStringProperty(isbn);
        this.title=new SimpleStringProperty(title);
        this.author=new SimpleStringProperty(author);
    }
    @Override
    public String getTitle() {
        return this.getTitleProperty().get();
    }
    @Override
    public StringProperty getTitleProperty() {
        return this.title;
    }
    @Override
    public String getIsbn() {
        return this.getIsbnProperty().get();
    }
    @Override
    public StringProperty getIsbnProperty() {
        return this.isbn;
    }
    @Override
    public int getYear() {
        return this.getYearProperty().get();
    }
    @Override
    public IntegerProperty getYearProperty() {
        return this.year;
    }
    @Override
    public int getPages() {
        return this.getPagesProperty().get();
    }
    @Override
    public IntegerProperty getPagesProperty() {
        return this.pages;
    }
    @Override
    public String getSerie() {
        return this.getSerieProperty().get();
    }
    @Override
    public StringProperty getSerieProperty() {
        return this.serie;
    }
    @Override
    public String getGenre() {
        return this.getGenreProperty().get();
    }
    @Override
    public StringProperty getGenreProperty() {
        return this.genre;
    }
    @Override
    public String getAuthor() {
        return this.getAuthorProperty().get();
    }
    @Override
    public StringProperty getAuthorProperty() {
        return this.author;
    }
    @Override
    public int getPrice() {
        return this.getPriceProperty().get();
    }
    @Override
    public IntegerProperty getPriceProperty() {
        return this.price;
    }
    @Override
    public void setTitle(String title) {
        this.title.set(title);
    }

    @Override
    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    @Override
    public void setYear(int year) {
        this.year.set(year);
    }

    @Override
    public void setPages(int pages) {
        this.pages.set(pages);
    }

    @Override
    public void setSerie(String serie) {
        this.serie.set(serie);
    }

    @Override
    public void setGenre(String genre) {
        this.genre.set(genre);
    }

    @Override
    public void setAuthor(String author) {
        this.author.set(author);
    }

    @Override
    public void setPrice(int price) {
        this.price.set(price);
    }
    public String toString() {
        return this.title.get()+","+this.isbn.get()+","+this.pages.get()+","+this.price.get()+","+this.year.get()+","+this.author.get()+","+this.genre.get()+","+this.serie.get();
    }
    
   
    
    
    
    
    
}
