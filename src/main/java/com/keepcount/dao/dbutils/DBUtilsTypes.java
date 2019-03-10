package com.keepcount.dao.dbutils;

public class DBUtilsTypes {

	private String typeName;
	private String type;

	public DBUtilsTypes() {

	}

	public DBUtilsTypes(String typeName, String type) {
		this.typeName = typeName;
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "DBUtilsTypes [typeName=" + typeName + ", type=" + type + "]";
	}

}
