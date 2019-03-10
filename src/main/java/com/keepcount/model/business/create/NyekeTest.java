
package com.keepcount.model.business.create;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class NyekeTest {

	public static void main ( String [ ] args ) {

		List< Map< Integer, String > > mapQns = listOfQuestions();

		List< Map< Integer, String > > mapAnswers = listOfAnswers();

		mapQns.contains( "First" );

	}

	private static List< Map< Integer, String > > listOfQuestions () {

		List< Map< Integer, String > > listOfMappedQuestions = new ArrayList<>();

		Map< Integer, String > mapOfFirstQuestions = new LinkedHashMap<>();
		mapOfFirstQuestions.put( 1 , "First" );
		mapOfFirstQuestions.put( 2 , "Second" );
		mapOfFirstQuestions.put( 3 , "Third" );
		listOfMappedQuestions.add( mapOfFirstQuestions );

		Map< Integer, String > mapOfSecondQuestions = new LinkedHashMap<>();
		mapOfSecondQuestions.put( 1 , "Second" );
		mapOfSecondQuestions.put( 2 , "Second" );
		mapOfSecondQuestions.put( 3 , "Third" );
		listOfMappedQuestions.add( mapOfSecondQuestions );

		return listOfMappedQuestions;
	}

	private static List< Map< Integer, String > > listOfAnswers () {

		List< Map< Integer, String > > listOfMappedQuestions = new ArrayList<>();

		Map< Integer, String > mapOfFirstQuestions = new LinkedHashMap<>();
		mapOfFirstQuestions.put( 1 , "First Answer" );
		mapOfFirstQuestions.put( 2 , "Second Answer" );
		mapOfFirstQuestions.put( 3 , "Third Answer" );
		listOfMappedQuestions.add( mapOfFirstQuestions );

		Map< Integer, String > mapOfSecondQuestions = new LinkedHashMap<>();
		mapOfSecondQuestions.put( 1 , "Second Answer" );
		mapOfSecondQuestions.put( 2 , "Second Answer" );
		mapOfSecondQuestions.put( 3 , "Third Answer" );
		listOfMappedQuestions.add( mapOfSecondQuestions );

		return listOfMappedQuestions;
	}

}
