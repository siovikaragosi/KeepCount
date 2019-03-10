package com.keepcount.controller.pricing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricingInsertionVerificationEnglsih {
	public static void main(String[] args) {
		// System.out.println("sp: " + spaces());
		String n = "1 200";
		System.out.println(n.length());
		// System.out.println(n.length());

		ifEnglish(n);

		Map<String, String> map = ifEnglish(n);

		System.out.println(map);

		System.out.println("%%%%%%");
		System.out.println(map.get("Not OK"));

	}

	public static Map<String, String> ifEnglish(String englishNumber) {
		Map<String, String> map = new HashMap<>();

		if (ifEnglishNumberOfDecimals(englishNumber).equals(notOK())) {
			System.out.println("0");
			map.put(notOK(), englishNumber);
		} else {
			System.out.println("0-0");
			// If the number CAN have a thousand separator
			if (englishNumber.length() >= 4) {
				/*
				 * If the number has a SEPARATOR and a DECIMAL and not subsequent COMMA SPACE or
				 * SPACE COMMA and no double DECIMAL
				 */
				if ((englishNumber.contains(",") && englishNumber.contains(".")) && (!englishNumber.contains(" "))
														&& (!englishNumber.contains(". ") && !englishNumber.contains(" .") && !englishNumber.contains(".."))) {
					System.out.println("1");
					System.out.println("OK");
					map.put(OK(), englishNumber);
				}
				/*
				 * If the number must does not have a SEPARATOR but has a DECIMAL and not
				 * subsequent COMMA SPACE or SPACE COMMA and no double DECIMAL
				 */
				else if ((!englishNumber.contains(",") && englishNumber.contains(".")) && (!englishNumber.contains(" "))
														&& (!englishNumber.contains(". ") && !englishNumber.contains(" .") && !englishNumber.contains(".."))) {
					System.out.println("2");
					System.out.println("OK");
					map.put(OK(), englishNumber);
				}
				/*
				 * If the number does not have a SEPARATOR , no DECIMAL and not subsequent COMMA
				 * SPACE or SPACE COMMA and no double DECIMAL
				 */
				else if ((!englishNumber.contains(",") && !englishNumber.contains(".")) && (!englishNumber.contains(" "))
														&& (!englishNumber.contains(". ") && !englishNumber.contains(" .") && !englishNumber.contains(".."))) {
					System.out.println("3");
					System.out.println("OK");
					map.put(OK(), englishNumber);
				}
				/*
				 * If the number does HAS a SEPARATOR , no DECIMAL and not subsequent COMMA
				 * SPACE or SPACE COMMA and no double DECIMAL
				 */
				else if ((englishNumber.contains(",") && !englishNumber.contains(".")) && (!englishNumber.contains(" "))
														&& (!englishNumber.contains(". ") && !englishNumber.contains(" .") && !englishNumber.contains(".."))) {
					System.out.println("4");
					System.out.println("OK");
					map.put(OK(), englishNumber);
				} else {
					map.put(notOK(), englishNumber);
				}
			}
			// if the number DOES not REQUIRE a SEPARATOR
			else if (englishNumber.length() < 4) {
				/*
				 * If the number DECIMAL and not subsequent COMMA SPACE or SPACE COMMA and no
				 * double DECIMAL
				 */
				if (englishNumber.contains(".") && !englishNumber.contains(" ") && !englishNumber.contains("..")
														&& (!englishNumber.contains(". ") && !englishNumber.contains(" .") && !englishNumber.contains(".."))) {
					System.out.println("5");
					System.out.println("OK");
					map.put(OK(), englishNumber);
				}
				/*
				 * If the number has no DECIMAL and not subsequent COMMA SPACE or SPACE COMMA
				 * and no double DECIMAL
				 */
				else if (!englishNumber.contains(".") && !englishNumber.contains(" ") && !englishNumber.contains("..")
														&& (!englishNumber.contains(". ") && !englishNumber.contains(" .") && !englishNumber.contains(".."))) {
					System.out.println("6");
					System.out.println("OK");
					map.put(OK(), englishNumber);
				} else {
					System.out.println("7");
					System.out.println("Not OK");
					map.put(notOK(), englishNumber);
				}
			}
		}
		return map;
	}

	/* Check the number of decimal places in English number */
	private static String ifEnglishNumberOfDecimals(String englishNumber) {
		List<String> allCharacters = new ArrayList<>();

		for (int i = 0; i < englishNumber.length(); i++) {
			char eachCharacter = englishNumber.charAt(i);
			String eachChar = String.valueOf(eachCharacter);
			allCharacters.add(eachChar);
		}

		String aComma = ".";
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
