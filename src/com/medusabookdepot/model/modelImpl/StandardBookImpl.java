/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.medusabookdepot.model.modelInterface.StandardBook;
import com.sun.org.apache.xalan.internal.xsltc.dom.BitArray;

/**
 * @author Marcello_Feroce
 *
 */
public class StandardBookImpl implements StandardBook{

    private String name;
    private String isbn;
    private int year;
    private int pages;
    private Optional<String> serie;
    private Optional<String> genre;//e.g. "Avventura", or "Thriller, Giallo, Azione"
    private  Optional<String> author;
    private int price;
    
    public StandardBookImpl(String isbn,String name, int year, int pages, String serie, String genre, String author, int price) {
        this.isbn=isbn;
        this.name=name;
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
        this.name=name;
        this.author=Optional.ofNullable(author);
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
    public static StandardBook getStandardBookFromString(String stringa){
        char c=stringa.charAt(0);
        int nfields=5;
        StandardBook book=new StandardBookImpl(null, null, 0, 0,null, null,null, 0);
        List<String>optionals=new ArrayList<>(3);
        if(stringa.contains("@")){
            optionals.set(0, "1");
            nfields++;
        }
        else optionals.set(0, "0");
        if(stringa.contains("#")){
            optionals.set(1, "1");
            nfields++;
        }
        else optionals.set(1, "0");
        if(stringa.contains("§")){
            optionals.set(2, "1");
            nfields++;
        }
        else optionals.set(2, "0");
        int x=0;
        int fieldInt=0;
        boolean nowSym;
        String stringValue;
        if(!optionals.contains("1")){
            for(int y=0;y<nfields;y++) {
                stringValue="";
                nowSym=false;
                while(c!=','){
                    if(Character.valueOf(c).equals(',')) nowSym=true;
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
                fieldInt++;
                x++;
            }
        }
        if(optionals.get(0).equals("1")&&optionals.get(1).equals("1")&&optionals.get(2).equals("1")) {
            for(int y=0;y<nfields;y++) {
                stringValue="";
                nowSym=false;
                while(c!='@'||c!='§'||c!='#'||c!=','){
                    if(Character.valueOf(c).equals(',')||Character.valueOf(c).equals('@')||Character.valueOf(c).equals('#')||Character.valueOf(c).equals('§')) nowSym=true;
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
                if(fieldInt==5) {
                    book.setAuthor(stringValue);
                }
                if(fieldInt==6) {
                    book.setGenre(stringValue);
                }
                if(fieldInt==7) {
                    book.setSerie(stringValue);
                }
                fieldInt++;
                x++;
            }
        }
        if(optionals.get(0).equals("1")&&optionals.get(1).equals("1")&&optionals.get(2).equals("0")) {
            for(int y=0;y<nfields;y++) {
                stringValue="";
                nowSym=false;
                while(c!='@'||c!='#'||c!=','){
                    if(Character.valueOf(c).equals(',')||Character.valueOf(c).equals('@')||Character.valueOf(c).equals('#')) nowSym=true;
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
                if(fieldInt==5) {
                    book.setAuthor(stringValue);
                }
                if(fieldInt==6) {
                    book.setGenre(stringValue);
                }
                fieldInt++;
                x++;
            }
        }
        if(optionals.get(0).equals("1")&&optionals.get(1).equals("0")&&optionals.get(2).equals("1")) {
            for(int y=0;y<nfields;y++) {
                stringValue="";
                nowSym=false;
                while(c!='@'||c!='§'||c!=','){
                    if(Character.valueOf(c).equals(',')||Character.valueOf(c).equals('@')||Character.valueOf(c).equals('§')) nowSym=true;
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
                if(fieldInt==5) {
                    book.setAuthor(stringValue);
                }
                if(fieldInt==6) {
                    book.setSerie(stringValue);
                }
                fieldInt++;
                x++;
            }
        }
        if(optionals.get(0).equals("0")&&optionals.get(1).equals("1")&&optionals.get(2).equals("1")) {
            for(int y=0;y<nfields;y++) {
                stringValue="";
                nowSym=false;
                while(c!='#'||c!='§'||c!=','){
                    if(Character.valueOf(c).equals(',')||Character.valueOf(c).equals('#')||Character.valueOf(c).equals('§')) nowSym=true;
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
                if(fieldInt==5) {
                    book.setGenre(stringValue);
                }
                if(fieldInt==6) {
                    book.setSerie(stringValue);
                }
                fieldInt++;
                x++;
            }
        }
        if(optionals.get(0).equals("1")&&optionals.get(1).equals("0")&&optionals.get(2).equals("0")) {
            for(int y=0;y<nfields;y++) {
                stringValue="";
                nowSym=false;
                while(c!='@'||c!=','){
                    if(Character.valueOf(c).equals(',')||Character.valueOf(c).equals('@')) nowSym=true;
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
                if(fieldInt==5) {
                    book.setAuthor(stringValue);
                }
                fieldInt++;
                x++;
            }
        }
        if(optionals.get(0).equals("0")&&optionals.get(1).equals("1")&&optionals.get(2).equals("0")) {
            for(int y=0;y<nfields;y++) {
                stringValue="";
                nowSym=false;
                while(c!='#'||c!=','){
                    if(Character.valueOf(c).equals(',')||Character.valueOf(c).equals('#')) nowSym=true;
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
                if(fieldInt==5) {
                    book.setGenre(stringValue);
                }
                fieldInt++;
                x++;
            }
        }
        if(optionals.get(0).equals("0")&&optionals.get(1).equals("0")&&optionals.get(2).equals("1")) {
            for(int y=0;y<nfields;y++) {
                stringValue="";
                nowSym=false;
                while(c!='§'||c!=','){
                    if(Character.valueOf(c).equals(',')||Character.valueOf(c).equals('§')) nowSym=true;
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
                if(fieldInt==5) {
                    book.setSerie(stringValue);
                }
                fieldInt++;
                x++;
            }
        }
        return book;
        
    }
    public String toString() {
        return this.name+","+this.isbn+","+this.pages+","+this.price+","+this.year+","+(this.author.isPresent()?author.get():"n.d") +"@"+(this.genre.isPresent()?this.genre.get():"n.d")+"#"+(this.serie.isPresent()?this.serie.get():"n.d")+"§";
    }
}
