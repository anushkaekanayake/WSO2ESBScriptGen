package com.app.service;

import java.util.ArrayList;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import com.app.util.AppUtils;
import com.app.util.Path;

/**
 * Generate the related js code for script mediator
 * 
 * @author anushka.ekanayake
 *
 */
public class JsonToJsParser {
	private final static Logger LOGGER = Logger.getLogger(JsonToJsParser.class.getName(), null);
	// starting line + variable defining
	private static ArrayList<String> section1 = new ArrayList<>();
	// soap body generating section
	private static ArrayList<String> section2 = new ArrayList<>();
	// variable list index
	private static int index = 0;
	private static int lineNumber = 0;

	public static void geneateBody(String apiType) {
		LOGGER.info("Generating js...");

		generatorDetector(Path.getAllKeys(), apiType);

	}

	public static void codeGenerator(String lineString, String status) {
		String lineGenerated = "";
		if (lineString.split("\\?").length == 2) {
			if (AppUtils.APITYPE.equals("GET") || AppUtils.APITYPE.equals("DELETE")) {

				// for loop generating
				if (status.equals(AppUtils.GENLOOP)) {
					System.out.println("loop=======================GET/DELETE=========");
				} else {
					lineGenerated = "if(!!" + Path.getAllKeys(index) + "){\n" + AppUtils.VARIABLE + "="
							+ AppUtils.VARIABLE + "+\"" + lineString.split("\\?")[0] + "\"+" + Path.getAllKeys(index)
							+ "+\"" + lineString.split("\\?")[1] + "\";\n}";
					index++;
				}

			} else {
				// normal line of POST or PUT request ex:<id>jsonpath<id>
				//System.out.println(Path.getActualJsonPath() + " " + index);
				String jsonPath = Path.getActualJsonPath().get(index);
				lineGenerated = "if(typeof(" + jsonPath + ")!=\"undefined\"){\n" + AppUtils.VARIABLE + "="
						+ AppUtils.VARIABLE + "+\"" + lineString.split("\\?")[0] + "\"+" + jsonPath + "+\""
						+ lineString.split("\\?")[1] + "\";\n}";

				index++;
			}

			// index++;
		} else {
			// nested tag
			if (lineNumber == 0) {
				lineGenerated = "var " + AppUtils.VARIABLE + "=\"" + lineString + "\";";
				lineNumber++;
			} else {

				// for loop generating (starting and ending tags)
				if (status.equals(AppUtils.GENLOOP)) {
					// @TODO

					if (lineString.split("/").length == 1) {
						String arrayPath = arrayPathDetector(Path.getActualJsonPath().get(index));
						// Opening tag of the loop

						lineGenerated = "if (typeof (" + arrayPath + ") != \"undefined\") {\n for ( var "
								+ AppUtils.LOOPINDEX + " = 0; " + AppUtils.LOOPINDEX + " < " + arrayPath + ".length; "
								+ AppUtils.LOOPINDEX + "++) {\n" + AppUtils.VARIABLE + "=" + AppUtils.VARIABLE + "+\""
								+ lineString + "\";";
					} else {
						// ending tag of the loop
						lineGenerated = AppUtils.VARIABLE + "= " + AppUtils.VARIABLE + " + \"" + lineString
								+ "\";\n}\n}";
					}

				} else {
					lineGenerated = AppUtils.VARIABLE + "=" + AppUtils.VARIABLE + "+\"" + lineString + "\";";
				}

			}

		}

		//System.out.println(lineGenerated);
		// add to section2
		section2.add(lineGenerated);

	}

	/**
	 * generate the json path of the array object with the json path of it's
	 * first child element
	 * 
	 * @param childTagPath
	 *            json path of the child element
	 * @return json path of the actual json array
	 */
	private static String arrayPathDetector(String childTagPath) {
		String actualPath = childTagPath.split((Pattern.quote("[")))[0];

		// remove [index] and return
		return actualPath;
	}

	private static void generatorDetector(ArrayList<String> variableList, String apiType) {
		switch (apiType.toUpperCase()) {
		case "GET":
			urlParamAPI(variableList);
			break;
		case "POST":
			jsonBodyAPI(variableList);
			break;
		case "PUT":
			jsonBodyAPI(variableList);
			break;
		case "DELETE":
			urlParamAPI(variableList);
			break;
		}

	}

	// related to GET or DELETE requests
	private static void urlParamAPI(ArrayList<String> variableList) {
		for (String varibleName : variableList) {
			String lineString = "var " + varibleName.trim() + AppUtils.URLPARAMPREFIX + varibleName.trim()
					+ AppUtils.URLPARAMSUFFIX;

			// add to section1 list
			section1.add(lineString);
		}
		System.out.println("generated code: \n");
		for (String string : section1) {
			System.out.println(string);
		}
		System.out.println("\n");
		for (String string : section2) {
			System.out.println(string);
		}
	}

	// related to POST or PUT request
	private static void jsonBodyAPI(ArrayList<String> variableList) {
		System.out.println("\n......generated code-post or put.....\n\n");
		System.out.println(AppUtils.FIRSTLINE);
		for (String string : section2) {
			// @TODO
			// write to js file with IOService
			System.out.println(string);
		}
		// last line
		System.out.println(AppUtils.LASTLINE);
		System.out.println("\n\n.....end of the generated code-post or put.....");
	}

}
