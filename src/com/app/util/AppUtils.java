package com.app.util;

/**
 * Utilities for the project
 * 
 * @author anushka.ekanayake
 *
 */
public class AppUtils {
	// required API type (able change GET/POST/PUT/DELETE)
	public static final String APITYPE = "POST";

	// related to structure annotating
	public static final String OPTIONAL = "<!--optional-->";
	public static final String LOOP = "<!--loop-->";
	public static final String LOOPEND = "<!--end-->";

	// related to js generating
	public static final String VARIABLE = "request";
	public static final String LASTLINE = "mc.setPayloadXML(new XML(" + VARIABLE + "));";
	public static final String REQUESTOBJ = "requestObj";
	public static final String FIRSTLINE = "var " + REQUESTOBJ + " = mc.getPayloadJSON();";
	public static final String URLPARAMPREFIX = " = mc.getProperty('query.param.";
	public static final String URLPARAMSUFFIX = "');";
	public static final String GENLOOP = "loop";
	public static final String LOOPINDEX="index";
	
}
