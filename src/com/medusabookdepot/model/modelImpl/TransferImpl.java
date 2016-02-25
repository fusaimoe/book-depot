/**
 * 
 */
package com.medusabookdepot.model.modelImpl;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

import com.medusabookdepot.model.modelInterface.CanSendTransferrer;
import com.medusabookdepot.model.modelInterface.Depot;
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
    private Date leavingDate;
    private StringProperty trackingNumber;
    private StandardBookImpl book;
    private IntegerProperty quantity;

    public TransferImpl() {
        this(null,null,null,null,null,0);
    }
    
    /*public TransferImpl(CanSendTransferrer sender, Transferrer receiver, Date leavingDate,
            StandardBookImpl book, int quantity) {
        this.sender = sender;
        this.receiver = receiver;
        this.leavingDate = leavingDate;
        this.trackingNumber = new SimpleStringProperty(this.getNewTrackingNumber());
        this.book=book;
        this.quantity=new SimpleIntegerProperty(quantity);

    }*/
    public TransferImpl(CanSendTransferrer sender, Transferrer receiver, Date leavingDate,
            StandardBookImpl book, String trackingNumber, int quantity) {
        this.sender = sender;
        this.receiver = receiver;
        this.leavingDate = leavingDate;
        this.book = book;
        this.trackingNumber = new SimpleStringProperty(trackingNumber);
        this.quantity=new SimpleIntegerProperty(quantity);
    }
    @Override
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
    public StringProperty trackingNumberProperty() {
        return trackingNumber;
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
    public Date getLeavingDate() {
        return this.leavingDate;
    }
    @Override
    public String getTrackingNumber() {
        return new String(trackingNumber.get());//copia difensiva
    }
    @Override
    public int getQuantity() {
    	if(this.quantity==null) {
    		return 0;
    	}
        Integer q=new Integer(this.quantity.get());//copia difensiva
        return q.intValue();
    }

    @Override
    public int getTotalPrice() {
        int x = 0;
        x=this.quantity.get()*this.book.getPrice();
        return x;
    }
    @Override
    public StandardBookImpl getBook() {//copia difensiva
        if(this.book==null) {
        	return null;
        }
    	return new StandardBookImpl(this.book.getIsbn(), this.book.getTitle(), this.book.getYear(), this.book.getPages(), this.book.getSerie(), this.book.getGenre(), this.book.getAuthor(), this.book.getPrice());
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
    public void setLeavingDate(Date leavingDate) {
        this.leavingDate = leavingDate;
    }
  
    @Override
    public void setBook(StandardBookImpl book) {
        this.book=book;
        
    }
    
    @Override
    public void setTrackingNumber(String trackingnumber) {
        this.trackingNumber.set(trackingnumber);
    }
    
    @Override
    public void setQuantity(int quantity) {
        this.quantity.set(quantity);
    }
    
    public String toString() {
        return this.leavingDate + "\n" + this.sender + "\n" + this.receiver + "\n" + this.trackingNumber + "\n"
                + this.getQuantity() + "\n";
    }
    @Override
    public IntegerProperty quantityProperty() {
        return quantity;
    }
    @Override
    public IntegerProperty totalPriceProperty() {
        return new SimpleIntegerProperty(this.getTotalPrice());
    }
}
