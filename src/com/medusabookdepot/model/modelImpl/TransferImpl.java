/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
@XmlRootElement(name = "transfer")
@XmlSeeAlso({DepotImpl.class,CustomerImpl.class, TransferrerImpl.class})
public class TransferImpl implements Transfer, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1788501318970271441L;
    private CanSendTransferrer sender;
    private Transferrer receiver;
    
    @XmlElement
    private Date leavingDate;
    private StringProperty trackingNumber;
    private Map<StandardBookImpl, Integer> books;
    private boolean arrived;
    private boolean left;

    public TransferImpl() {
        this(null,null,null,null, null);
    }
    
    public TransferImpl(CanSendTransferrer sender, Transferrer receiver, Date leavingDate,
            Map<StandardBookImpl, Integer> books) {
        this.sender = sender;
        this.receiver = receiver;
        this.leavingDate = leavingDate;
        this.trackingNumber = new SimpleStringProperty(this.getNewTrackingNumber());
        this.books = books;

    }
    public TransferImpl(CanSendTransferrer sender, Transferrer receiver, Date leavingDate,
            Map<StandardBookImpl, Integer> books, String trackingNumber) {
        this.sender = sender;
        this.receiver = receiver;
        this.leavingDate = leavingDate;
        this.books = books;
        this.trackingNumber = new SimpleStringProperty(trackingNumber);
    }
    public String getNewTrackingNumber() {
        return String.valueOf(new Random().nextInt(1000000));
    }
    @Override
    public CanSendTransferrer getSender() {
        if (this.sender.isADepot()) {// copia difensiva!
            Depot cs = (Depot) this.sender;
            Map<StandardBookImpl, Integer> m = new HashMap<>();
            m = cs.getBooks();
            cs = new DepotImpl(new String(this.sender.getName()), m);
            return cs;
        }
        return this.sender;
    }

    @Override
    public Transferrer getReceiver() {
        if (this.receiver.isADepot()) {// copia difensiva!
            Depot cs = (Depot) this.receiver;
            Map<StandardBookImpl, Integer> m = new HashMap<>();
            m = cs.getBooks();
            cs = new DepotImpl(new String(this.receiver.getName()), m);
            return cs;
        }
        return this.receiver;
    }

    @Override
    public Map<StandardBookImpl, Integer> getBooks() {
    	
      /*  Map<StandardBookImpl, Integer> map = new HashMap<>();
        for (Entry<StandardBookImpl, Integer> ee : this.books.entrySet()) {
            map.put(ee.getKey(), ee.getValue());
        }*/ //copia difensiva
        return books;
    }
    @Override
    public String getBooksAsACoolString() {
        String finale = new String("");
        for (Entry<StandardBookImpl, Integer> entry : this.books.entrySet()) {
            finale = finale.concat("libro: "+entry.getKey().getTitle()+"\n\t" + "in quantità " + entry.getValue() + "\n");
        }
        return finale;

    }
    @Override
    public Date getLeavingDate() {
        return this.leavingDate;
    }

    @Override
    public void setSender(CanSendTransferrer sender) {
        this.sender = sender;
    }

    @Override
    public void setReceiver(Transferrer receiver) {
        this.receiver = receiver;
    }

    @Override
    public void setBooks(Map<StandardBookImpl, Integer> books) {
        this.books = books;
    }

    @Override
    public void setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
    }
    @Override
    public String getTrackingNumber() {
        return new String(trackingNumber.get());//copia difensiva
    }
    @Override
    public int getQuantity() {
        int x = 0;
        for (StandardBook libro : this.books.keySet()) {
            x = x + books.get(libro);
        }
        return x;
    }

    @Override
    public int getQuantityFromBook(StandardBookImpl book) {
        int x = 0;
        for (StandardBook libro : this.books.keySet()) {
            if (libro.getIsbn().equals(book.getIsbn())) {
                x += books.get(libro).intValue();
            }
        }
        return x;
    }

    @Override
    public int getTotalPrice() {
        int x = 0;
        for (StandardBook libro : this.books.keySet()) {
            x = x + libro.getPrice() * this.books.get(libro);
        }
        return x;
    }

    @Override
    public void setTrackingNumber(String trackingnumber) {
        this.trackingNumber.set(trackingnumber);
    }

    @Override
    public void setQuantityFromBook(StandardBookImpl book, int quantity) {
        this.books.replace(book, books.get(book), Integer.valueOf(quantity));

    }

    @Override
    public void replaceBook(StandardBookImpl oldBook, StandardBookImpl newBook) {
        Integer x = books.get(oldBook);
        this.books.remove(oldBook);
        this.books.put(newBook, x);

    }
    public String toString() {
        return this.leavingDate + "\n" + this.sender + "\n" + this.receiver + "\n" + this.trackingNumber + "\n"
                + this.getQuantity() + "\n";
    }
    @Override
    public Map<StandardBookImpl, IntegerProperty> booksProperty() {
        return null;
    }
    @Override
    public IntegerProperty quantityProperty() {
        Integer i=new Integer(getQuantity());//copia difensiva
        return new SimpleIntegerProperty(i);
    }
    @Override
    public IntegerProperty quantityFromBookProperty(StandardBookImpl book) {
        Integer i=new Integer(this.getQuantityFromBook(book));
        return new SimpleIntegerProperty(i);
    }
    @Override
    public IntegerProperty totalPriceProperty() {
        return new SimpleIntegerProperty(this.getTotalPrice());
    }
    @Override
    public StringProperty booksAsACoolStringProperty() {
        return new SimpleStringProperty(this.getBooksAsACoolString());
    }
    @Override
    public boolean isArrived() {
        return this.arrived;
    }
    @Override
    public void setArrived(boolean arrived) {
        this.arrived = arrived;

    }
    @Override
    public boolean equals(Transfer transfer) {
        if(this.getTrackingNumber().equals(transfer.getTrackingNumber())) {
            return true;
        }
        return false;

    }
    @Override
    public boolean isLeft() {
        return this.left;
    }
    @Override
    public void setLeft(boolean left) {
        this.left = left;
        
    }
}
