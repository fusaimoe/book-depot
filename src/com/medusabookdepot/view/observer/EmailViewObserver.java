package com.medusabookdepot.view.observer;

import javax.mail.MessagingException;

/**
 * Send E-mail
 */
public interface EmailViewObserver {
	
	/**
	 * Send an email to <i>receiver</i>, with <i>subject</i> and message <i>body</i>, with a mandatory <i>attachment</i> 
	 * @param receiver
	 * @param subject
	 * @param body
	 * @param attachment
	 */
	public void send(String receiver, String subject, String body, String attachment) throws MessagingException, IllegalArgumentException;
	
}
