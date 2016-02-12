/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Depot;
import com.medusabookdepot.model.modelInterface.StandardBook;
import com.medusabookdepot.model.modelInterface.Transfer;
import com.medusabookdepot.model.modelInterface.Transferrer;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Marcello_Feroce
 *
 */
public class TransferImpl implements Transfer, Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1788501318970271441L;
    private CanSendTransferrer sender;
    private Transferrer receiver;
    private java.util.Date leavingDate;
    private String trackingNumber;
    private Map<StandardBook, Integer> books;
    private boolean arrived;

    public TransferImpl(CanSendTransferrer sender, Transferrer receiver, java.util.Date leavingDate,
            Map<StandardBook, Integer> books) {
        this.sender = sender;
        this.receiver = receiver;
        this.leavingDate = leavingDate;
        this.trackingNumber = this.getNewTrackingNumber();
        this.books = books;

    }
    public TransferImpl(CanSendTransferrer sender, Transferrer receiver, java.util.Date leavingDate,
            Map<StandardBook, Integer> books, String trackingNumber) {
        this.sender = sender;
        this.receiver = receiver;
        this.leavingDate = leavingDate;
        this.books = books;
        this.trackingNumber = trackingNumber;
    }
    public String getNewTrackingNumber() {
        return String.valueOf(new Random().nextInt(1000000));
    }
    @Override
    public CanSendTransferrer getSender() {
        if (this.sender.isADepot()) {// copia difensiva!
            Depot cs = (Depot) this.sender;
            Map<StandardBook, Integer> m = new HashMap<>();
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
            Map<StandardBook, Integer> m = new HashMap<>();
            m = cs.getBooks();
            cs = new DepotImpl(new String(this.receiver.getName()), m);
            return cs;
        }
        return this.receiver;
    }

    @Override
    public Map<StandardBook, Integer> getBooks() {
        Map<StandardBook, Integer> map = new HashMap<>();
        for (Entry<StandardBook, Integer> ee : this.books.entrySet()) {
            map.put(ee.getKey(), ee.getValue());
        }
        return map;//copia difensiva
    }
    @Override
    public String getBooksAsString() {
        String finale = new String("");
        for (Entry<StandardBook, Integer> entry : this.books.entrySet()) {
            finale = finale.concat(entry.getKey().toString() + ">" + entry.getValue() + ",");
        }
        return finale;

    }
    @Override
    public java.util.Date getLeavingDate() {
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
    public void setBooks(Map<StandardBook, Integer> books) {
        this.books = books;
    }

    @Override
    public void setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
    }
    @Override
    public String getTrackingNumber() {
        return this.trackingNumber;
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
    public int getQuantityFromBook(StandardBook book) {
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
        this.trackingNumber = trackingnumber;
    }

    @Override
    public void setQuantityFromBook(StandardBook book, int quantity) {
        this.books.replace(book, books.get(book), Integer.valueOf(quantity));

    }

    @Override
    public void replaceBook(StandardBook oldBook, StandardBook newBook) {
        Integer x = books.get(oldBook);
        this.books.remove(oldBook);
        this.books.put(newBook, x);

    }
    public String toString() {
        return this.leavingDate + "\n" + this.sender + "\n" + this.receiver + "\n" + this.trackingNumber + "\n"
                + this.getQuantity() + "\n";
    }
    @Override
    public Map<StandardBook, IntegerProperty> getBooksProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public IntegerProperty getQuantityProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public IntegerProperty getQuantityFromBookProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public IntegerProperty getTotalPriceProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public StringProperty getBooksAsStringProperty() {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public boolean isArrived() {
        return this.arrived;
    }
    @Override
    public void setArrived(boolean arrived) {
        this.arrived = arrived;

    }
}
