package com.tetrasoft.util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class SMTPAuthenticator extends Authenticator {

	private String username;
	private String password;

	public SMTPAuthenticator(String user, String pass) {
		this.username = user;
		this.password = pass;
	}

	public PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(username, password); // erro nesta linha
	}
}