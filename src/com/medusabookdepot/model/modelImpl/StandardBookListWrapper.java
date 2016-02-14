package com.medusabookdepot.model.modelImpl;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Helper class to wrap a list of books. This is used for saving the list of books to an XML file. 
 **/
@XmlRootElement(name = "books")
public class StandardBookListWrapper {

    private List<StandardBookImpl> books;

    @XmlElement(name = "book")
    public List<StandardBookImpl> getBooks() {
        return books;
    }

    public void setBooks(List<StandardBookImpl> books) {
        this.books = books;
    }
}
