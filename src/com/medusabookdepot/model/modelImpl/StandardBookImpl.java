/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.Optional;

import com.medusabookdepot.model.modelInterface.StandardBook;

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
    public static StandardBook getStandardBookFromString(String stringa){
        char c=stringa.charAt(0);
        int nfields=5;
        StandardBook book=new StandardBookImpl(null, null, 0, 0,null, null,null, 0);
        if(stringa.contains("@")){
            nfields++;
        }
        if(stringa.contains("#")){
            nfields++;
        }
        if(stringa.contains("§")){
            nfields++;
        }
        int x=0;
        int fieldInt=0;
        boolean nowSym;
        String stringValue;
        boolean aut=false;
        boolean gen=false;
        boolean ser=false;
            for(int y=0;y<nfields;y++) {
                stringValue="";
                nowSym=false;
                c=Character.valueOf(stringValue.charAt(x));
                while(c!='@'&&c!='§'&&c!='#'&&c!=','){
                    if(Character.valueOf(c).equals(',')||Character.valueOf(c).equals('@')||Character.valueOf(c).equals('#')||Character.valueOf(c).equals('§')) nowSym=true;
                    if(Character.valueOf(c).equals('@')) {
                        aut=true;
                    }
                    if(Character.valueOf(c).equals('#')) {
                        gen=true;
                    }
                    if(Character.valueOf(c).equals('§')) {
                        ser=true;
                    }
                    if(!nowSym) {
                        stringValue=stringValue.concat(String.valueOf(c));
                    }
                    x++;
                    c=Character.valueOf(stringValue.charAt(x));
                }
                if(fieldInt==0) {
                    book.setName(stringValue);
                }
                if(fieldInt==1) {
                    book.setIsbn(stringValue);
                }
                if(fieldInt==2) {
                    book.setPages(Integer.parseInt(stringValue));
                }
                if(fieldInt==3) {
                    book.setPrice(Integer.parseInt(stringValue));
                }
                if(fieldInt==4) {
                    book.setYear(Integer.parseInt(stringValue));
                }
                if(fieldInt==5&&aut) {
                    book.setAuthor(stringValue);
                }
                if(fieldInt==5&&gen) {
                    book.setGenre(stringValue);
                }
                if(fieldInt==5&&ser) {
                    book.setSerie(stringValue);
                }
                if(fieldInt==6&&aut&&gen) {
                    book.setGenre(stringValue);
                }
                if(fieldInt==6&&aut&&ser) {
                    book.setSerie(stringValue);
                }
                if(fieldInt==7) {
                    book.setSerie(stringValue);
                }
                fieldInt++;
                x++;
            }
        return book;  
    }
    public String toString() {
        return this.name+","+this.isbn+","+this.pages+","+this.price+","+this.year+","+this.author+","+this.genre+","+this.serie;
    }
}
