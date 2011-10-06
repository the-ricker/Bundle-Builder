package com.jeffreyricker.osgi.repository;

public interface Property {

	String VERSION = "version";
	String URL = "url";
	String URI = "uri";
	String LONG = "long";
	String DOUBLE = "double";
	String SET = "set";

	String getName();

	String getType();

	String getValue();

	Object getConvertedValue();

}
