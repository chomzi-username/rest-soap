package com.example.demo.controllers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
@RequestMapping("/person")
public class PersonController {
	private static final int UUID_DURATION_TIME = 1;
	private static final String SECRET_KEY = "JBSWY3DPEHPK3PXP";
	private static final int HEX = 16;
	private static final int MILLION = 1000000;

	static Map<String, LocalDateTime> uuidTime = new HashMap<String, LocalDateTime>();
	static Map<String, Person> uuidUser = new HashMap<String, Person>();

	@RequestMapping("/")
	public String home() {
		return "REST Service user homepage";
	}

	@PostMapping("/login")
	public String login(@RequestBody Person person) {
		String uuid = generateUuid();
		String message = "Invalid login or password";
		if (!verifyUser(person)) {
			return message;
		}

		if (!uuidUser.containsValue(person)) {
			Person.addUser(person);
			uuidUser.put(uuid, person);
			message = uuid;
		} else {
			message = "You are already logged";
		}

		return message;
	}

	private String generateUuid() {
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
				Person.removeUser(uuidUser.get(uuid));
				uuidUser.remove(uuid);
				throw new RuntimeException("Uuid expired");
			}
		} catch (java.lang.NullPointerException exception) {
			throw new RuntimeException("You are not logged");
		}
	}

	private boolean verifyUser(Person person) {

		for (Person tempUser : PersonDatabase.getUsersFromDatabase()) {
			if (tempUser.equals(person)) {
				return true;
			}
		}
		return false;
	}

	/*
	@RequestMapping("/content")
	public String contentPage(@RequestParam(value = "") String uuid, Device device) {

		validateUuid(uuid);
		if (isMobileDevice(device) == true) {
			return "Great, you are in secret page on mobile";
		}
		return "Great, you are in secret page on desktop";
	}

	private boolean isMobileDevice(Device device) {
		if (device.isMobile()) {
			return true;
		}
		return false;
	}*/

	@DeleteMapping("/logout")
	public String logout(@RequestParam(value = "") String uuid) {

		if (uuidUser.containsKey(uuid)) {
			String username = uuidUser.get(uuid).getLogin();
			Person.removeUser(uuidUser.get(uuid));
			uuidTime.remove(uuid);
			uuidUser.remove(uuid);
			return String.format("User %s logout successfully", username);
		}
		return "User is not logged in";
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

}

