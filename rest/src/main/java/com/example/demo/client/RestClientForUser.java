package com.example.demo.client;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.example.demo.utils.Person;

public class RestClientForUser {

	private static final int UUID_SIZE = 10;
	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String HOST = "http://localhost:8080/user/";

	public Response userLogin(String login, String password) {
		Client client = ClientBuilder.newClient();
		Person person = new Person(login, password);
		Response response = client.target(HOST + "login").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(person, MediaType.APPLICATION_JSON), Response.class);

		return response;

	}
	
	public Response userLogout(String uuid){
		Client client = ClientBuilder.newClient();
		Response response = client.target(HOST + "logout?uuid="+uuid).request(MediaType.APPLICATION_JSON)
				.delete();
		return response;
	}

	public Response home() {
		Client client = ClientBuilder.newClient();
		Response response = client.target(HOST).request(MediaType.APPLICATION_JSON).get(Response.class);
		return response;

	}
	public Response content(String uuid) {
		Client client = ClientBuilder.newClient();
		Response response = client.target(HOST+"content?uuid="+uuid).request(MediaType.APPLICATION_JSON).get(Response.class);
		return response;

	}

}