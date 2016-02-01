/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import com.medusabookdepot.model.modelInterface.StandardBook;

import javafx.beans.property.IntegerProperty;
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
    private String name;
    private String isbn;
    private int year;
    private int pages;
    private String serie;
    private String genre;//e.g. "Avventura", or "Thriller, Giallo, Azione"
    private String author;
    private int price;
    
    public StandardBookImpl(String isbn,String name, int year, int pages, String serie, String genre, String author, int price) {
        this.isbn=isbn;
        this.name=name;
        this.year=year;
        this.pages=pages;
        this.serie=serie;
        this.genre=genre;
        this.author=author;
        this.price=price;
    }
    public StandardBookImpl(String isbn) {
        this.isbn=isbn;
    }
    public StandardBookImpl(String isbn,String name,String author) {
        this.isbn=isbn;
        this.name=name;
        this.author=author;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getIsbn() {
        return this.isbn;
    }

    @Override
    public int getYear() {
        return this.year;
    }

    @Override
    public int getPages() {
        return this.pages;
    }

    @Override
    public String getSerie() {
        return this.serie;
    }

    @Override
    public String getGenre() {
        return this.genre;
    }

    @Override
    public String getAuthor() {
        return this.author;
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public void setIsbn(String isbn) {
        this.isbn=isbn;
    }

    @Override
    public void setYear(int year) {
        this.year=year;
    }

    @Override
    public void setPages(int pages) {
        this.pages=pages;
    }

    @Override
    public void setSerie(String serie) {
        this.serie=serie;
    }

    @Override
    public void setGenre(String genre) {
        this.genre=genre;
    }

    @Override
    public void setAuthor(String author) {
        this.author=author;
    }

    @Override
    public void setPrice(int price) {
        this.price=price;
    }
    public String toString() {
        return this.name+","+this.isbn+","+this.pages+","+this.price+","+this.year+","+this.author+","+this.genre+","+this.serie;
    }
    @Override
    public StringProperty getNameProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public StringProperty getIsbnProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public IntegerProperty getYearProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public IntegerProperty getPagesProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public StringProperty getSerieProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public StringProperty getGenreProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public StringProperty getAuthorProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public IntegerProperty getPriceProperty() {
        // TODO Auto-generated method stub
        return null;
    }
}
