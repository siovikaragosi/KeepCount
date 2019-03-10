package com.keepcount.dao.select;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.keepcount.model.select.Country;

@Repository
public class CountryDAO {

	private static final List<Country> COUNTRIES = new ArrayList<Country>();

	static {
		initData();
	}

	private static void initData() {
		Country vn = new Country(1L, "VN", "Vietnam");
		Country en = new Country(2L, "EN", "England");
		Country ru = new Country(3L, "VN", "Russia");

		COUNTRIES.add(en);
		COUNTRIES.add(vn);
		COUNTRIES.add(ru);
	}

	public List<Country> getCountries() {
		return COUNTRIES;
	}

	public Map<Long, String> getMapCountries() {
		Map<Long, String> map = new HashMap<Long, String>();
		for (Country c : COUNTRIES) {
			map.put(c.getCountryId(), c.getCountryName());
		}
		return map;
	}
}