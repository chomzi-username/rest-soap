package com.example.demo.utils;

import java.util.ArrayList;
import java.util.List;

public final class PersonDatabase {

	private static List<Person> persons = new ArrayList<>();
	private static List<Person> admins = new ArrayList<>();
	private static PersonDatabase instance = null;

	private PersonDatabase() {
		persons.add(new Person("user", "user"));
		persons.add(new Person("user1", "user1"));
		persons.add(new Person("user2", "user2"));
		persons.add(new Person("user3", "user3"));
		admins.add(new Person("admin", "admin", true));
	}

	public static PersonDatabase connect() {
		if (instance == null) {
			instance = new PersonDatabase();
		}
		return instance;
	}

	public static List<Person> getUsersFromDatabase() {
		return persons;
	}

	public static List<Person> getAdminsFromDatabase() {
		return admins;
	}

	public static void addUser(Person person) {
		if (person.isAdmin()) {
			admins.add(person);
		}
		persons.add(person);
	}

}
