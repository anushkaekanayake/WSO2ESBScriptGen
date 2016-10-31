package com.app.service;

import java.util.ArrayList;
import java.util.regex.Pattern;
import com.app.util.AppUtils;
import com.app.util.Path;
import java.util.logging.Logger;

/**
 * This class identify the related json paths of each property mentioned in the
 * xml document <br/>
 * Determine property type depending on the annotations
 * 
 * @author anushka.ekanayake
 *
 */
public class StructureIdentifire {
	private final static Logger LOGGER = Logger.getLogger(StructureIdentifire.class.getName(), null);
	private static StructureIdentifire connection;
	private ArrayList<String> requestList = new ArrayList<>();
	private boolean isCodeGenerating = false;

	public static StructureIdentifire getConnection() {
		if (connection == null) {
			connection = new StructureIdentifire();
		}
		return connection;
	}

	private StructureIdentifire() {
	}

	/**
	 * find annotation tags and understand property types
	 * 
	 * @param requestList
	 */
	public void identifire(ArrayList<String> requestList, boolean isCodeGenerating) {
		this.requestList = requestList;
		// set the property - isCodeGenerating true or false
		this.isCodeGenerating = isCodeGenerating;
		// read line by line and understand operation
		int index = 0;
		int cursor;
		int cursorStart = -1;
		int cursorEnd;
		int cursorIgnore = -1;
		for (String lineString : requestList) {
			switch (lineString) {
			case AppUtils.OPTIONAL:
				// next line is an optional parameter
				cursor = index + 1;
				partDetector(cursor);
				// ignore the next line repeat processing
				cursorIgnore = cursor;
				break;
			case AppUtils.LOOP:
				// Next line starts loop
				cursorStart = index + 1;
				break;
			case AppUtils.LOOPEND:
				// This is the end of the loop
				cursorEnd = index - 1;
				partDetector(cursorStart, cursorEnd);
				// reset cursorstart and cursor end
				cursorStart = -1;
				cursorEnd = -1;
				break;
			// Error making location******************************************
			default:
				// this is a mandatory line
				if (index != cursorIgnore) {
					if (cursorStart == -1) {
						partDetector(index);
					}

				}
				break;
			}
			index++;

		}

	}

	/**
	 * Identify normal optional and Mandatory properties
	 * 
	 * @param cursor
	 */
	private void partDetector(int cursor) {
		if (!this.isCodeGenerating) {
			// identifying tags first time
			generateTag(this.requestList.get(cursor), cursor, "");
		} else {
			// generate js code
			JsonToJsParser.codeGenerator(this.requestList.get(cursor), "empty");
		}

		LOGGER.info(this.requestList.get(cursor));
	}

	/**
	 * Identify the loop type properties
	 * 
	 * @param cursorStart
	 * @param cursorEnd
	 */
	private void partDetector(int cursorStart, int cursorEnd) {
		if (cursorStart != -1) {

			for (int lineNum = cursorStart; lineNum <= cursorEnd; lineNum++) {
				LOGGER.info("array: " + this.requestList.get(lineNum));
				// generate js code
				if (this.isCodeGenerating) {
					JsonToJsParser.codeGenerator(this.requestList.get(lineNum), AppUtils.GENLOOP);
				} else {
					// generate tags (json paths)
					generateTag(this.requestList.get(lineNum), lineNum, AppUtils.GENLOOP);
				}

			}
		} else {
			LOGGER.severe("System Error");
		}
	}

	/**
	 * Generate the tag values and generate json path of each property tag
	 * 
	 * @param requestString
	 * @param cursor=
	 *            index of the string located in the List
	 * @param status
	 *            =loop situation or not
	 */
	private void generateTag(String requestString, int cursor, String status) {
		char[] request = null;
		StringBuffer actualTag = new StringBuffer();
		String jsonPath = AppUtils.REQUESTOBJ;

		// check whether it is a normal property or nested property
		if (Pattern.matches(".*\\?.*", this.requestList.get(cursor))) {
			LOGGER.info("Normal Property");

			// find if it has name space
			if (Pattern.matches(".*\\:.*", requestString)) {
				// @TODO complete the code

			} else {

				request = requestString.trim().split("\\?")[0].toCharArray();
				// remove last character
				for (int index = 1; index < request.length - 1; index++) {
					actualTag.append(request[index]);
				}
				for (String path : Path.getJsonPath()) {
					jsonPath = jsonPath + "." + path;
				}
				if (status.equals(AppUtils.GENLOOP)) {
					jsonPath = jsonPath + "[" + AppUtils.LOOPINDEX + "]" + "." + actualTag;
				} else {
					jsonPath = jsonPath + "." + actualTag;
				}

				LOGGER.info("tag-final: " + actualTag.toString() + " json path: " + jsonPath);

				// add to hashMap, tag and json path (all tags related to
				// variable generating)
				Path.setTagMap(jsonPath, actualTag.toString());

			}
		} else {
			// for nested objects - have to add List
			if (!Pattern.matches("^</.*", requestString)) {
				// select only the opening tag
				request = requestString.trim().toCharArray();
				for (int index = 1; index < request.length - 1; index++) {
					actualTag.append(request[index]);
				}
				// add to linkedList
				Path.setJsonPath(actualTag.toString());

				LOGGER.info("tag-nested: " + actualTag);

			} else {
				// found a closing tag - remove last element from json path
				Path.removeLastElement();
			}

		}

	}

}
