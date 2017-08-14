package com.example.demo.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.example.demo.utils.Person;

public class RestClientForAdmin {
	private static final Logger LOGGER = Logger.getRootLogger();
	private static final String HOST = "http://localhost:8080/admin/";

	Client client = ClientBuilder.newClient();
	
	public Response adminLogin(String login, String password) {	
		Person person = new Person(login, password);
		Response response = client.target(HOST + "login").request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(person, MediaType.APPLICATION_JSON), Response.class);
		return response;
	}
	
	public Response adminLogout(String uuid){
		Response response = client.target(HOST + "logout?uuid="+uuid).request(MediaType.APPLICATION_JSON)
				.delete();
		return response;
	}

	public Response home() {
		Response response = client.target(HOST).request(MediaType.APPLICATION_JSON).get(Response.class);
		return response;

	}
	public Response content(String uuid) {
		Response response = client.target(HOST+"content?uuid="+uuid).request(MediaType.APPLICATION_JSON).get(Response.class);
		return response;

	}
	public Response addUser(String uuid,String login, String password)
	{
		Person person = new Person(login,password);
		Response response = client.target(HOST + "add?uuid="+uuid).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(person, MediaType.APPLICATION_JSON), Response.class);
		return response;
	}
	public Response addAdmin(String uuid,String login, String password)
	{
		Person person = new Person(login,password,true);
		Response response = client.target(HOST + "add?uuid="+uuid).request(MediaType.APPLICATION_JSON)
				.post(Entity.entity(person, MediaType.APPLICATION_JSON), Response.class);
		return response;
	}
	public Response getLoggedInUsers(String uuid){
		Response response = client.target(HOST+"getUsers?uuid="+uuid).request(MediaType.APPLICATION_JSON).get(Response.class);
		return response;
	}
}
