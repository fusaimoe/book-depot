/**
 * 
 */
package com.medusabookdepot.model;

import java.util.Date;

import com.medusabookdepot.modelInterface.Parcel;
import com.medusabookdepot.modelInterface.Transfer;
import com.medusabookdepot.modelInterface.Transferrer;

/**
 * @author Marcello_Feroce
 *
 */
public class TransferImpl implements Transfer {

    private Transferrer sender;
    private Transferrer receiver;
    private Parcel parcel;
    private Date leavingDate;
    
    public  TransferImpl(Transferrer sender,Transferrer receiver,Parcel parcel,Date leavingDate) {
        this.sender=sender;
        this.receiver=receiver;
        this.parcel=parcel;
        this.leavingDate=leavingDate;
    }
    @Override
    public Transferrer getSender() {
        return this.sender;
    }

    @Override
    public Transferrer getReceiver() {
        return this.receiver;
    }

    @Override
    public Parcel getParcel() {
        return this.parcel;
    }

    @Override
    public Date getLeavingDate() {
        return this.leavingDate;
    }

    @Override
    public void setSender(Transferrer sender) {
        this.sender=sender;
    }

    @Override
    public void setReceiver(Transferrer receiver) {
        this.receiver=receiver;
    }

    @Override
    public void setParcel(Parcel parcel) {
        this.parcel=parcel;
    }

    @Override
    public void setLeavingDate(Date leavingDate) {
        this.leavingDate=leavingDate;
    }

}
