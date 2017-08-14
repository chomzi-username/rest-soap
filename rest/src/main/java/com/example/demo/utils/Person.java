package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person {
	public String firstName;
	public String lastName;
	private  String login;
	private String password;
	private boolean admin;

	private static List<Person> persons = new ArrayList<>();

	public Person() {
	}

	public Person(String login, String password) {
		this.login = login;
		this.password = password;
		this.admin = false;
	}

	public Person(String login, String password, boolean admin) {
		this.login = login;
		this.password = password;
		this.admin = admin;
	}

	public String getLogin() {
		return login;
	}

	public String getPassword() {
		return password;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public static List<Person> getUsers() {
		return persons;
	}

	public static void addUser(Person person) {
		persons.add(person);
	}

	public static void removeUser(Person person) {
		persons.remove(person);
	}

	public static void replaceUser(Person person) {
		removeUser(person);
		addUser(person);
	}

	@Override
	public String toString() {
		return "User [login=" + login + ", password=" + password + ", admin=" + admin + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

	public boolean equalsLogin(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Person other = (Person) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}
}
