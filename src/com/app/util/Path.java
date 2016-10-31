package com.app.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

/**
 * Object related to generate js depending on the structure
 * 
 * @author anushka.ekanayake
 *
 */
public class Path {
	// uses to generate json paths
	private static ArrayList<String> jsonPathCarrier = new ArrayList<>();
	private static ArrayList<String> actualJsonPath = new ArrayList<>();
	private static String parentTag = null;
	private static String currentTag = null;
	private static HashMap<String, String> tagMap = new HashMap<>();
	private static HashMap<String, String> tagMapReverse = new HashMap<>();
	private static ArrayList<String> allKeys = new ArrayList<>();

	public static ArrayList<String> getActualJsonPath() {

		return actualJsonPath;
	}

	public static ArrayList<String> getJsonPath() {
		return jsonPathCarrier;
	}

	public static void setJsonPath(String jsonPath) {
		Path.jsonPathCarrier.add(jsonPath);
	}

	public static String getParentTag() {
		return parentTag;
	}

	public static void setParentTag(String parentTag) {
		Path.parentTag = parentTag;
	}

	public static String getCurrentTag() {
		return currentTag;
	}

	public static void setCurrentTag(String currentTag) {
		Path.currentTag = currentTag;
	}

	public static void removeLastElement() {
		jsonPathCarrier.remove(jsonPathCarrier.size() - 1);
	}

	public static HashMap<String, String> getTagMap() {
		return tagMap;
	}

	public static void setTagMap(String path, String tag) {
		if (tagMapReverse.get(tag) != null) {
			// update the tag name, because it is already available
			tag = tag + new Random().nextInt(10);
		}
		// set tagMapReverse
		tagMapReverse.put(tag, path);
		tagMap.put(path, tag);
		// add tags to allKeys list
		allKeys.add(tag);
		// add to actualJsonPath
		actualJsonPath.add(path);

	}

	public static String getTagMap(String key) {
		return tagMap.get(key);
	}

	public static ArrayList<String> getAllKeys() {
		return allKeys;
	}

	public static String getAllKeys(int id) {
		return allKeys.get(id);

	}

}
