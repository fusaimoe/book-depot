package com.medusabookdepot.model;

public interface Parcel {
	
	public String getTrackingNumber();
	
	public int getQuantity();
	
	public int getQuantityFromBook(StandardBook book);
	
	public int getTotalPrice();
	
	public void setTrackingNumber();
	
	public void setQuantityFromBook(StandardBook book);
	
	public void replaceBook(StandardBook oldBook, StandardBook newBook);
}