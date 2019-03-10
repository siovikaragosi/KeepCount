
package com.keepcount.dao.langauge ;

import java.io.FileInputStream ;
import java.io.IOException ;
import java.io.InputStream ;
import java.util.LinkedHashMap ;
import java.util.Map ;
import java.util.Properties ;

import com.keepcount.model.langauge.LanguageModel ;

public class LanguageSignUpDAO {

	/*
	 * The language specified by the user determines the resource bundle properties file to called.
	 * 
	 * The resource bundle then lists all its words and their respective IDs.
	 * 
	 * The IDs in the Map must match the IDs of the components.
	 */
	public static Map < String , LanguageModel > findAllLanguageWords( String language , String country , String classAreaOfConcern ) {

		Map < String , LanguageModel > languageModelMap = new LinkedHashMap <>() ;
		Map < String , String > languageMapFromPropertiesFile = new LinkedHashMap <>() ;

		Properties prop = new Properties() ;
		InputStream input = null ;

		String msgBundle = "MessageBundle".concat( "_" ) ;
		String areaOfConcern = classAreaOfConcern ;
		String languageCode = language.concat( "_" ).concat( country ).concat( "_" ).concat( areaOfConcern ).concat( ".properties" ) ;

		String finalMessageBundle = msgBundle.concat( languageCode ) ;

		try {

			input = new FileInputStream( "src/main/java/".concat( finalMessageBundle ) ) ;

			// load a properties file
			prop.load( input ) ;

			for ( Object object : prop.keySet() ) {
				String key = object.toString() ;
				String value = prop.getProperty( key ) ;
				languageMapFromPropertiesFile.put( key , value ) ;
				LanguageModel model = new LanguageModel( key , value ) ;
				languageModelMap.put( key , model ) ;
				System.out.println( languageModelMap ) ;
			}
		} catch ( Exception e ) {
			e.printStackTrace() ;
		} finally {
			if ( input != null ) {
				try {
					input.close() ;
				} catch ( IOException e ) {
					e.printStackTrace() ;
				}
			}
		}

		return languageModelMap ;
	}

	public static void main( String [ ] args ) throws Exception {
		String langCode = "ganda" ;
		String country = "UG" ;
		String areaOfConcern = "signup" ;

		findAllLanguageWords( langCode , country , areaOfConcern ) ;

	}

}
