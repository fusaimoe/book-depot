/**
 * 
 */
package com.medusabookdepot.model;

import com.medusabookdepot.modelInterface.Library;

/**
 * @author Marcello_Feroce
 *
 */
public class LibraryImpl extends CustomerImpl implements Library{

    public LibraryImpl(String name, String address, String telephoneNumber) {
        super(name, address, telephoneNumber);
    }

}
