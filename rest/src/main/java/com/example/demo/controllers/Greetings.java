package com.example.demo.controllers;



import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.ForbiddenException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.utils.Person;

@Controller
@RestController
public class Greetings {
	
	static final String expectedToken = "e550d817-534c-4140-bb76-4395f83cac08";
/*
- All services should take parameter: token. If token is equal to 'e550d817-534c-4140-bb76-4395f83cac08' than service can return results
*/
	public static boolean verifyToken(String userToken){
		
		if(userToken.equals(expectedToken)){
			return true;
		}
		else{
			return false;
		}
	}
	
	@RequestMapping(value="svc/v1/public/greetings/getPersonById/{id}/token/{token}")
	public String getPersonById(@PathVariable final int id, @PathVariable final String token) throws Exception{
		
		if(!verifyToken(token)){
			throw new ForbiddenException();
		}
		String result = token + " " + expectedToken;
		return token;//"First name: Artur /n Last name: Goralczyk";
	}
	
	
	//test: header Content-Type  application/json
	@RequestMapping(value="svc/v1/public/greetings/addPerson",method = RequestMethod.POST)
	public int addPerson(@RequestBody Person person){
		int id = 1;
		return id;
	}
	
	@RequestMapping(value="svc/v1/public/greetings/deletePersonById/{id}")
	public String deletePersonById(@PathVariable final int id){
		return "Artur Goralczyk has been deleted";
	}
	
	@RequestMapping(value="svc/v1/public/greetings/getPersons")
	public List<String> getPersons(){
		List<String> persons = new ArrayList<String>();
		persons.add("Artur Goralczyk");
		persons.add("asda asd as");
		persons.add("asd a zxczxcz");
		return persons;
	}
	
	@RequestMapping(value="svc/v1/private/admin/accounts/{accountNumber}")
	public String getExtraPrivateAccountDataLinkedTo(@PathVariable final int accountNumber){
		return "Private extra account lined to: "+accountNumber;
	}

}
