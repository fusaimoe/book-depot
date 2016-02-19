package com.medusabookdepot.controller;

import java.util.Optional;
import java.util.stream.Stream;

import com.medusabookdepot.model.modelImpl.LibraryImpl;
import com.medusabookdepot.model.modelImpl.PersonImpl;
import com.medusabookdepot.model.modelInterface.Customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CustomerController {

	/**
	 * Search a customer in his list
	 * @param Name of customer
	 * @param Address of customer
	 * @param Telephone number of customer
	 * @return None elements if it doesn't find the element(s)
	 */
	public Stream<Customer> searchCustomer(Optional<String> name, Optional<String> address,
			Optional<String> telephoneNumber) {

		Stream<Customer> result = this.customers.stream();

		if (name.isPresent()) {
			result = result.filter(e -> name.get().equals(e.getName()));
		}
		if (address.isPresent()) {
			result = result.filter(e -> address.get().equals(e.getAddress()));
		}
		if (telephoneNumber.isPresent()) {
			result = result.filter(e -> telephoneNumber.get().equals(e.getTelephoneNumber()));
		}

		/* If there are not filters, return all books in one stream */
		return result;
	}

	public boolean customerIsPresent(String address, String telephoneNumber) {

		return (this.searchCustomer(Optional.empty(), Optional.of(address), Optional.of(telephoneNumber)).count() == 1);
	}

	/**
	 * The list that contains all saved customers
	 */
	private final ObservableList<Customer> customers = FXCollections.observableArrayList();

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
	public void addCustomer(String name, String address, String telephoneNumber, int type) {

		if (this.customerIsPresent(address, telephoneNumber)) {
			throw new IllegalArgumentException(
					"FAIL: " + address + " and/or " + telephoneNumber + "are/is already present/s");
		}

		switch (type) {
		case 1:
			customers.add(new LibraryImpl(name, address, telephoneNumber));
			break;
		case 2:
			customers.add(new PersonImpl(name, address, telephoneNumber));
			break;
		}
	}

	/**
	 * Remove a customer from the list
	 * 
	 * @param Customer
	 */
	public void deleteCusomer(Customer customer) {

		customers.remove(customer);
	}
}
