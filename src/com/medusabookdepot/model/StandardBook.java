package com.medusabookdepot.model;

public interface StandardBook {

	/**
	 * @return The book's name
	 */
	public String getName();
	
	public String getISBN();
	
	public int getYear();
	
	public int getPages();
	
	public String getSerie();
	
	public String getGenre();
	
	public String getAuthor();
	
	public int getPrice();
	
	// ====================================== \\
	
	public String setName();
	
	public String setISBN();
	
	public int setYear();
	
	public int setPages();
	
	public String setSerie();
	
	public String setGenre();
	
	public String setAuthor();
	
	public int setPrice();
}
