package com.example.demo.controllers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.digest.HmacUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.mobile.device.Device;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.utils.Person;
import com.example.demo.utils.PersonDatabase;

@RestController
@RequestMapping("/admin")
public class AdminController {
	private static final int UUID_DURATION_TIME = 2;
	private static final String SECRET_KEY = "JBSWY3DPEHPK3PXP";
	private static final int HEX = 16;
	private static final int MILLION = 1000000;

	Map<String, LocalDateTime> uuidTime = new HashMap<String, LocalDateTime>();
	Map<String, Person> uuidAdmin = new HashMap<String, Person>();

	@RequestMapping("/")
	public String home() {
		return "REST Service admin homepage";
	}

	@PostMapping("/login")
	public String login(@RequestBody Person user) {
		String uuid = generateUuid();
		user.setAdmin(true);
		String message = "Invalid login or password";
		if (!verifyAdmin(user)) {
			return message;
		}
		if (!uuidAdmin.containsValue(user)) {
			Person.addUser(user);
			uuidAdmin.put(uuid, user);
			message = uuid;
		} else {
			message = "You are already logged";
		}

		return message;
	}

	@RequestMapping("/getUsers")
	public List<Person> getAllUsers(@RequestParam(value = "") String uuid) {
		validateUuid(uuid);
		checkUsers();
		checkAdmins();
		return Person.getUsers();
	}

	private void checkUsers() {
		checkActivity(PersonController.uuidTime, PersonController.uuidUser);
	}

	private void checkAdmins() {
		checkActivity(uuidTime, uuidAdmin);
	}

	private void checkActivity(Map<String, LocalDateTime> uuidTime, Map<String, Person> uuidAdmin) {
		Set<String> keys = uuidTime.keySet();
		List<String> toRemove = new ArrayList<>();
		for (String key : keys) {
			if (!uuidTime.get(key).isAfter(LocalDateTime.now())) {
				toRemove.add(key);
			}
		}
		for (String uuid : toRemove) {
			uuidTime.remove(uuid);
			Person.removeUser(uuidAdmin.get(uuid));
			uuidAdmin.remove(uuidAdmin.get(uuid));
		}
	}
/*
	@RequestMapping("/content")
	public String contentPage(@RequestParam(value = "") String uuid, Device device) {

		validateUuid(uuid);
		if (isMobileDevice(device) == true) {
			return "Great, you are in secret admin page on mobile";
		}
		return "Great, you are in secret admin page on desktop";
	}
*/
	@DeleteMapping("/logout")
	public String logout(@RequestParam(value = "") String uuid) {
		if (uuidAdmin.containsKey(uuid)) {
			String username = uuidAdmin.get(uuid).getLogin();
			Person.removeUser(uuidAdmin.get(uuid));
			uuidTime.remove(uuid);
			uuidAdmin.remove(uuid);
			return String.format("Admin %s logout successfully", username);
		}
		return "Admin is not logged in";
	}

	@PostMapping("/add")
	public String registerUser(@RequestBody Person user, @RequestParam(value = "") String uuid) {
		if(checkIfUserExistInDatabase(user)){
			return "User duplicate";
		}
		PersonDatabase.addUser(user);
		return "User added";
	}

	private boolean checkIfUserExistInDatabase(Person user) {
		if (user.isAdmin()){
			for (Person tempUser : PersonDatabase.getAdminsFromDatabase()) {
				if (tempUser.equals(user)) {
					return true;
				}
			}
			return false;
		}
		for (Person tempUser : PersonDatabase.getUsersFromDatabase()) {
			if (tempUser.equals(user)) {
				return true;
			}
		}
		return false;

	}

	/*
	private void generateGoogleAuthenticateCode() {
		String formatedSecret = SECRET_KEY.toUpperCase().replaceAll(StringUtils.SPACE, StringUtils.EMPTY);
		BigInteger secretDecoded = new BigInteger(new Base32().decode(formatedSecret));
		Long unixTime = System.currentTimeMillis() / 30;
		String key = HmacUtils.hmacSha1Hex(secretDecoded.toString(),
				HmacUtils.hmacSha1Hex(secretDecoded.toString(), unixTime.toString()));
		String last4bytes = key.substring(32, 40);
		BigInteger bytesValue = new BigInteger(last4bytes, HEX);
		long result = bytesValue.longValue() % MILLION;

	}*/

	public String generateUuid() {
		StringBuffer uuid = new StringBuffer();
		Random generator = new Random();
		for (int i = 0; i < 10; i++) {
			int number = generator.nextInt(10);
			uuid.append(number);
		}
		LocalDateTime uuidValidationTime = LocalDateTime.now();
		uuidValidationTime = uuidValidationTime.plusMinutes(UUID_DURATION_TIME);
		uuidTime.put(uuid.toString(), uuidValidationTime);
		return uuid.toString();
	}

	private void validateUuid(String uuid) {
		LocalDateTime now = LocalDateTime.now();
		try {
			if (!uuidTime.get(uuid).isAfter(now)) {
				uuidTime.remove(uuid);
				Person.removeUser(uuidAdmin.get(uuid));
				uuidAdmin.remove(uuid);
				throw new RuntimeException("Uuid expired");
			}
		} catch (java.lang.NullPointerException exception) {
			throw new RuntimeException("You are not logged");
		}
	}

	private boolean verifyAdmin(Person user) {
		for (Person tempUser : PersonDatabase.getAdminsFromDatabase()) {
			if (tempUser.equals(user)) {
				return true;
			}
		}
		return false;
	}
/*
	private boolean isMobileDevice(Device device) {
		if (device.isMobile()) {
			return true;
		}
		return false;
	}
*/
}
