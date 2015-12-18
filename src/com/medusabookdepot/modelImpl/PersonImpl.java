/**
 * 
 */
package com.medusabookdepot.modelImpl;

import com.medusabookdepot.modelInterface.Person;

/**
 * @author Marcello_Feroce
 *
 */
public class PersonImpl extends CustomerImpl implements Person {

    public PersonImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }
    
}
