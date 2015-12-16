package com.medusabookdepot.model;

import java.util.Calendar;

public interface Transfer {
	
	public Transferrer getSender();
	
	public Transferrer getReceiver();
	
	public Parcel getParcel();
	
	public Calendar getLeavingDate();
	
	// ========================================== \\
	
	public void setSender();
	
	public void setReceiver();
	
	public void setParcel();
	
	public void setLeavingDate();

}
