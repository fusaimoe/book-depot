/**
 * 
 */
package com.medusabookdepot.modelImpl;

import java.util.Optional;

import com.medusabookdepot.modelInterface.StandardBook;

/**
 * @author Marcello_Feroce
 *
 */
public class StandardBookImpl implements StandardBook{

    private Optional<String> name;
    private String isbn;
    private int year;
    private int pages;
    private Optional<String> serie;
    private Optional<String> genre;//e.g. "Avventura", or "Thriller, Giallo, Azione"
    private  Optional<String> author;
    private int price;
    
    public StandardBookImpl(String isbn,String name, int year, int pages, String serie, String genre, String author, int price) {
        this.isbn=isbn;
        this.name=Optional.ofNullable(name);
        this.year=year;
        this.pages=pages;
        this.serie=Optional.ofNullable(serie);
        this.genre=Optional.ofNullable(genre);
        this.author=Optional.ofNullable(author);
        this.price=price;
    }
    public StandardBookImpl(String isbn) {
        this.isbn=isbn;
    }
    public StandardBookImpl(String isbn,String name,String author) {
        this.isbn=isbn;
        this.name=Optional.ofNullable(name);
        this.author=Optional.ofNullable(author);
    }
    @Override
    public String getName() {
        return this.name.get();
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
        return this.serie.get();
    }

    @Override
    public String getGenre() {
        return this.genre.get();
    }

    @Override
    public String getAuthor() {
        return this.author.get();
    }

    @Override
    public int getPrice() {
        return this.price;
    }

    @Override
    public void setName(String name) {
        this.name=Optional.ofNullable(name);
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
        this.serie=Optional.ofNullable(serie);
    }

    @Override
    public void setGenre(String genre) {
        this.genre=Optional.ofNullable(genre);
    }

    @Override
    public void setAuthor(String author) {
        this.author=Optional.ofNullable(author);
    }

    @Override
    public void setPrice(int price) {
        this.price=price;
    }

}
