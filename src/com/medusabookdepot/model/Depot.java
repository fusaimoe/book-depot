package com.medusabookdepot.model;

public interface Depot extends Transferrer{

	
	public int getQuantity();
	
	public int getQuantityFromStandardBook(StandardBook book);
	
	public int getQuantityFromTitle(String title);
	
	public int getQuantityFromAuthor(String author);
	
	public int getQuantityFromYear(int year);
	
}
