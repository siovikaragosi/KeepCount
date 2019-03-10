package com.keepcount.controller.pricing;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PricingInsertionVerificationFrench {

	public static void main(String[] args) {
		// System.out.println("sp: " + spaces());
		String n = "1 200";
		System.out.println(n.length());
		// System.out.println(n.length());

		ifFrench(n);

	}

	public static Map<String, String> ifFrench(String frenchNumber) {
		Map<String, String> map = new HashMap<>();

		if (ifFrenchNumberOfDecimals(frenchNumber).equals(notOK())) {
			System.out.println("0");
			map.put(notOK(), frenchNumber);
		} else {
			System.out.println("0-0");
			// If the number CAN have a thousand separator
			if (frenchNumber.length() >= 4) {
				/*
				 * If the number has a SEPARATOR and a DECIMAL and not subsequent COMMA SPACE or
				 * SPACE COMMA and no double DECIMAL
				 */
				if ((frenchNumber.contains(" ") && frenchNumber.contains(",")) && (!frenchNumber.contains("."))
														&& (!frenchNumber.contains(", ") && !frenchNumber.contains(" ,") && !frenchNumber.contains(",,"))) {
					System.out.println("1");
					map.put(OK(), frenchNumber);
				}
				/*
				 * If the number must does not have a SEPARATOR but has a DECIMAL and not
				 * subsequent COMMA SPACE or SPACE COMMA and no double DECIMAL
				 */
				else if ((!frenchNumber.contains(" ") && frenchNumber.contains(",")) && (!frenchNumber.contains("."))
														&& (!frenchNumber.contains(", ") && !frenchNumber.contains(" ,") && !frenchNumber.contains(",,"))) {
					System.out.println("2");
					map.put(OK(), frenchNumber);
				}
				/*
				 * If the number does not have a SEPARATOR , no DECIMAL and not subsequent COMMA
				 * SPACE or SPACE COMMA and no double DECIMAL
				 */
				else if ((!frenchNumber.contains(" ") && !frenchNumber.contains(",")) && (!frenchNumber.contains("."))
														&& (!frenchNumber.contains(", ") && !frenchNumber.contains(" ,") && !frenchNumber.contains(",,"))) {
					System.out.println("3");
					map.put(OK(), frenchNumber);
				}
				/*
				 * If the number does HAS a SEPARATOR , no DECIMAL and not subsequent COMMA
				 * SPACE or SPACE COMMA and no double DECIMAL
				 */
				else if ((frenchNumber.contains(" ") && !frenchNumber.contains(",")) && (!frenchNumber.contains("."))
														&& (!frenchNumber.contains(", ") && !frenchNumber.contains(" ,") && !frenchNumber.contains(",,"))) {
					System.out.println("4");
					map.put(OK(), frenchNumber);
				} else {
					map.put(notOK(), frenchNumber);
				}
			}
			// if the number DOES not REQUIRE a SEPARATOR
			else if (frenchNumber.length() < 4) {
				/*
				 * If the number DECIMAL and not subsequent COMMA SPACE or SPACE COMMA and no
				 * double DECIMAL
				 */
				if (frenchNumber.contains(",") && !frenchNumber.contains(".") && !frenchNumber.contains(",,")
														&& (!frenchNumber.contains(", ") && !frenchNumber.contains(" ,") && !frenchNumber.contains(",,"))) {
					System.out.println("5");
					map.put(OK(), frenchNumber);
				}
				/*
				 * If the number has no DECIMAL and not subsequent COMMA SPACE or SPACE COMMA
				 * and no double DECIMAL
				 */
				else if (!frenchNumber.contains(",") && !frenchNumber.contains(".") && !frenchNumber.contains(",,")
														&& (!frenchNumber.contains(", ") && !frenchNumber.contains(" ,") && !frenchNumber.contains(",,"))) {
					System.out.println("6");
					map.put(OK(), frenchNumber);
				} else {
					System.out.println("7");
					map.put(notOK(), frenchNumber);
				}
			}
		}
		return map;
	}

	/* Check the number of decimal places in French number */
	private static String ifFrenchNumberOfDecimals(String frenchNumber) {
		List<String> allCharacters = new ArrayList<>();

		for (int i = 0; i < frenchNumber.length(); i++) {
			char eachCharacter = frenchNumber.charAt(i);
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
