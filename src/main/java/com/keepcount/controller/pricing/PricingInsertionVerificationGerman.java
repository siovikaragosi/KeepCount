package com.keepcount.controller.pricing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PricingInsertionVerificationGerman {
	public static void main(String[] args) {
		// System.out.println("sp: " + spaces());
		String n = "1.200.000,54";
		System.out.println("length: " + n.length());
		// System.out.println(n.length());

		// Set<String> message = ifGerman(n).keySet();
		// System.out.println("mes: " + message);
		// String number = ifGerman(n).get(message);
		// System.out.println("num: " + number);

		ifGerman(n);

	}

	public static Map<String, String> ifGerman(String germanNumber) {
		Map<String, String> map = new HashMap<>();

		if (ifGermanNumberOfDecimals(germanNumber).equals(notOK())) {
			System.out.println("0");
			map.put(notOK(), germanNumber);
		} else {
			// If the number CAN have a thousand separator
			if (germanNumber.length() >= 4) {
				/*
				 * If the number has a SEPARATOR and a DECIMAL and not subsequent COMMA SPACE or
				 * SPACE COMMA and no double DECIMAL
				 */
				if ((germanNumber.contains(".") && germanNumber.contains(",")) && (!germanNumber.contains(" "))
														&& (!germanNumber.contains(", ") && !germanNumber.contains(" ,") && !germanNumber.contains(",,"))) {
					System.out.println("1");
					map.put(OK(), germanNumber);
				}
				/*
				 * If the number must does not have a SEPARATOR but has a DECIMAL and not
				 * subsequent COMMA SPACE or SPACE COMMA and no double DECIMAL
				 */
				else if ((!germanNumber.contains(".") && germanNumber.contains(",")) && (!germanNumber.contains(" "))
														&& (!germanNumber.contains(", ") && !germanNumber.contains(" ,") && !germanNumber.contains(",,"))) {
					System.out.println("2");
					map.put(OK(), germanNumber);
				}
				/*
				 * If the number does not have a SEPARATOR , no DECIMAL and not subsequent COMMA
				 * SPACE or SPACE COMMA and no double DECIMAL
				 */
				else if ((!germanNumber.contains(".") && !germanNumber.contains(",")) && (!germanNumber.contains(" "))
														&& (!germanNumber.contains(", ") && !germanNumber.contains(" ,") && !germanNumber.contains(",,"))) {
					System.out.println("3");
					map.put(OK(), germanNumber);
				}
				/*
				 * If the number does HAS a SEPARATOR , no DECIMAL and not subsequent COMMA
				 * SPACE or SPACE COMMA and no double DECIMAL
				 */
				else if ((germanNumber.contains(".") && !germanNumber.contains(",")) && (!germanNumber.contains(" "))
														&& (!germanNumber.contains(", ") && !germanNumber.contains(" ,") && !germanNumber.contains(",,"))) {
					System.out.println("4");
					map.put(OK(), germanNumber);
				} else {
					map.put(notOK(), germanNumber);
				}
			}
			// if the number DOES not REQUIRE a SEPARATOR
			else if (germanNumber.length() < 4) {
				/*
				 * If the number DECIMAL and not subsequent COMMA SPACE or SPACE COMMA and no
				 * double DECIMAL
				 */
				if (germanNumber.contains(",") && !germanNumber.contains(" ") && !germanNumber.contains(",,")
														&& (!germanNumber.contains(", ") && !germanNumber.contains(" ,") && !germanNumber.contains(",,"))) {
					System.out.println("5");
					map.put(OK(), germanNumber);
				}
				/*
				 * If the number has no DECIMAL and not subsequent COMMA SPACE or SPACE COMMA
				 * and no double DECIMAL
				 */
				else if (!germanNumber.contains(",") && !germanNumber.contains(" ") && !germanNumber.contains(",,")
														&& (!germanNumber.contains(", ") && !germanNumber.contains(" ,") && !germanNumber.contains(",,"))) {
					System.out.println("6");
					map.put(OK(), germanNumber);
				} else {
					System.out.println("7");
					map.put(notOK(), germanNumber);
				}
			}
		}
		return map;
	}

	/* Check the number of decimal places in German number */
	private static String ifGermanNumberOfDecimals(String germanNumber) {
		List<String> allCharacters = new ArrayList<>();

		for (int i = 0; i < germanNumber.length(); i++) {
			char eachCharacter = germanNumber.charAt(i);
			String eachChar = String.valueOf(eachCharacter);
			allCharacters.add(eachChar);
		}

		String aComma = ",";
		List<String> allCommas = new ArrayList<>();
		if (allCharacters != null) {
			for (int i = 0; i < allCharacters.size(); i++) {
				String charObtained = allCharacters.get(i);
				if (charObtained.equals(aComma)) {
					allCommas.add(charObtained);
				}
			}
		}

		if (allCommas.size() > 1) {
			return notOK();
		} else {
			return OK();
		}
	}

	public static String OK() {
		// System.out.println("OK");
		return "OK";
	}

	public static String notOK() {
		// System.out.println("not OK");
		return "Not OK";
	}

}
