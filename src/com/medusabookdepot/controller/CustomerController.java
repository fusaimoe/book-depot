package com.medusabookdepot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.model.modelImpl.LibraryImpl;
import com.medusabookdepot.model.modelImpl.PersonImpl;
import com.medusabookdepot.model.modelImpl.PrinterImpl;
import com.medusabookdepot.controller.files.FileManager;
import com.medusabookdepot.model.modelImpl.CustomerImpl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerController {

	/**
	 * The list that contains all saved customers
	 */
	private final ObservableList<CustomerImpl> customers = FXCollections.observableArrayList();

	private static CustomerController singCustomers;
	
	// Fields for file load and save, and for converting to PDF
	private final static String NAME = "customers";
	private FileManager<CustomerImpl> fileManager = new FileManager<>(customers, CustomerImpl.class, NAME);

	private CustomerController(){
		super();
	}
	
	public static CustomerController getInstanceOf() {

		return (CustomerController.singCustomers == null ? new CustomerController() : CustomerController.singCustomers);
	}

	/**
	 * Add a new customer to the list of all customers
	 * 
	 * @param Name
	 *            of new customer
	 * @param Address
	 *            of new customer
	 * @param Telephone
	 *            number of new customer
	 * @param Type
	 *            of customer: 1) Library 2) Person
	 */
	public void addCustomer(String name, String address, String telephoneNumber, String type) {

		if (this.isCustomerValid(name, address, telephoneNumber, type)) {
			throw new IllegalArgumentException(
					"FAIL: " + address + " and/or " + telephoneNumber + "are/is already present/s");
		}

		//To be sure
		type = type.toLowerCase();
		switch (type) {
		case "library":
			customers.add(new LibraryImpl(name, address, telephoneNumber));
			break;
		case "person":
			customers.add(new PersonImpl(name, address, telephoneNumber));
			break;
		case "printer":
			customers.add(new PrinterImpl(name, address, telephoneNumber));
			break;
		}

		fileManager.saveDataToFile();
	}
	
	/**
	 * Search a string in all field of customer properties
	 * @param Value to search
	 * @return List of customers that contains the value
	 */
	public List<CustomerImpl> searchCustomer(String value){
		List<CustomerImpl> result = new ArrayList<>();
		value.toLowerCase();
		this.customers.stream().forEach(e->{
			if(e.getAddress().toLowerCase().contains(value) || e.getTelephoneNumber().toLowerCase().contains(value) || e.getName().toLowerCase().contains(value)){
				result.add(e);
			}
		});
		
		return result;
	}
	
	/**
	 * Search a customer in his list
	 * 
	 * @param Name
	 *            of customer
	 * @param Address
	 *            of customer
	 * @param Telephone
	 *            number of customer
	 * @return None elements if it doesn't find the element(s)
	 */
	public Stream<CustomerImpl> searchCustomer(Optional<String> name, Optional<String> address,
			Optional<String> telephoneNumber) {

		Stream<CustomerImpl> result = this.customers.stream();

		if (name.isPresent()) {
			result = result.filter(e -> e.getName().toLowerCase().contains(name.get().toLowerCase()));
		}
		if (address.isPresent()) {
			result = result.filter(e -> e.getAddress().toLowerCase().contains(address.get().toLowerCase()));
		}
		if (telephoneNumber.isPresent()) {
			result = result.filter(e -> e.getTelephoneNumber().toLowerCase().contains(telephoneNumber.get().toLowerCase()));
		}

		return result;
	}

	/**
	 * Check if a customer is already present in list
	 * @param Address
	 * @param Telephone number
	 * @return <b>True</b> if he's already present, else <b>False</b>
	 */
	public boolean customerIsPresent(String address, String telephoneNumber) {
		
		return (this.searchCustomer(Optional.empty(), Optional.of(address), Optional.of(telephoneNumber)).count() >= 1);
	}
	
	/**
	 * Verify the correct input for new customers
	 * @param Name
	 * @param Address
	 * @param Telephone number
	 * @param Type
	 * @return True if arguments are correct
	 */
	public boolean isCustomerValid(String name, String address, String telephoneNumber, String type){
		
		if(name.equals("") || address.equals("") || telephoneNumber.equals("") || type.equals("")){
			throw new IllegalArgumentException("The arguments must be not empty!");
		}
		if(this.customerIsPresent(address, telephoneNumber)){
			throw new IllegalArgumentException("The customer is already present!");
		}
		
		return true;
	}

	/**
	 * Remove a customer from the list
	 * 
	 * @param Customer
	 * @throws NoSuchElementException
	 *             if it doesn't find the customer
	 */
	public void removeCustomer(CustomerImpl customer) throws NoSuchElementException {

		try {
			customers.remove(customer);
		} catch (Exception e) {

			throw new NoSuchElementException("No such element in list!");
		}
		fileManager.saveDataToFile();
	}
	
	/**
	 * Edit a customer name
	 * 
	 * @param Customer
	 * @param Name
	 */
	public void editName(CustomerImpl customer, String name) {

		if(!isCustomerValid(name, customer.getAddress(), customer.getTelephoneNumber(), customer.getType().toString())){
			throw new IllegalArgumentException("Arguments are not right!");
		}
		customers.get(customers.indexOf(customer)).setName(name);
		fileManager.saveDataToFile();
	}

	/**
	 * Edit a customer address
	 * 
	 * @param Customer
	 * @param Address
	 */
	public void editAddress(CustomerImpl customer, String address) {
		
		if(!isCustomerValid(customer.getName(), address, customer.getTelephoneNumber(), customer.getType().toString())){
			throw new IllegalArgumentException("Arguments are not right!");
		}
		customers.get(customers.indexOf(customer)).setAddress(address);
		fileManager.saveDataToFile();
	}

	/**
	 * Edit a customer telephone number
	 * 
	 * @param Customer
	 * @param Telephone
	 */
	public void editPhone(CustomerImpl customer, String phone) {

		if(!isCustomerValid(customer.getName(), customer.getAddress(), phone, customer.getType().toString())){
			throw new IllegalArgumentException("Arguments are not right!");
		}
		customers.get(customers.indexOf(customer)).setTelephoneNumber(phone);
		fileManager.saveDataToFile();
	}
	
	/**
	 * @return The list of saved books
	 */
	public ObservableList<CustomerImpl> getCustomers() {

		return customers;
	}
}
