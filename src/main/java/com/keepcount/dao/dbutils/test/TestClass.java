
package com.keepcount.dao.dbutils.test ;

import java.math.BigDecimal ;

public class TestClass {

	private BigDecimal valOne ;
	private String valOneStr ;

	private BigDecimal valTwo ;
	private String valTwoStr ;

	private String testName ;
	private BigDecimal bigTest ;
	private int intTest ;
	private double doubleTest ;

	public TestClass() {
		// TODO Auto-generated constructor stub
	}

	public TestClass( BigDecimal valOne , String valOneStr , BigDecimal valTwo , String valTwoStr , String testName , BigDecimal bigTest , int intTest , double doubleTest ) {
		super() ;
		this.valOne = valOne ;
		this.valOneStr = valOneStr ;
		this.valTwo = valTwo ;
		this.valTwoStr = valTwoStr ;
		this.testName = testName ;
		this.bigTest = bigTest ;
		this.intTest = intTest ;
		this.doubleTest = doubleTest ;
	}

	public BigDecimal getValOne() {
		return valOne ;
	}

	public void setValOne( BigDecimal valOne ) {
		this.valOne = valOne ;
	}

	public String getValOneStr() {
		return valOneStr ;
	}

	public void setValOneStr( String valOneStr ) {
		this.valOneStr = valOneStr ;
	}

	public BigDecimal getValTwo() {
		return valTwo ;
	}

	public void setValTwo( BigDecimal valTwo ) {
		this.valTwo = valTwo ;
	}

	public String getValTwoStr() {
		return valTwoStr ;
	}

	public void setValTwoStr( String valTwoStr ) {
		this.valTwoStr = valTwoStr ;
	}

	public String getTestName() {
		return testName ;
	}

	public void setTestName( String testName ) {
		this.testName = testName ;
	}

	public BigDecimal getBigTest() {
		return bigTest ;
	}

	public void setBigTest( BigDecimal bigTest ) {
		this.bigTest = bigTest ;
	}

	public int getIntTest() {
		return intTest ;
	}

	public void setIntTest( int intTest ) {
		this.intTest = intTest ;
	}

	public double getDoubleTest() {
		return doubleTest ;
	}

	public void setDoubleTest( double doubleTest ) {
		this.doubleTest = doubleTest ;
	}

	@Override
	public String toString() {
		return "TestClass [valOne=" + valOne + ", valOneStr=" + valOneStr + ", valTwo=" + valTwo + ", valTwoStr=" + valTwoStr + ", testName=" + testName + ", bigTest=" + bigTest
				+ ", intTest=" + intTest + ", doubleTest=" + doubleTest + "]" ;
	}

}
