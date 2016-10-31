package com.app.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Read json and write js file (all the IO operations)
 * 
 * @author anushka.ekanayake
 *
 */
public class IOService {

	private static IOService connection = null;

	private IOService() {
	}

	public static IOService getConnection() {
		if (connection == null) {
			connection = new IOService();
		}
		return connection;
	}

	public ArrayList<String> fileReader(String xmlFilePath) {
		ArrayList<String> xmlRequest = new ArrayList<>();

		try (BufferedReader bufferedReader = new BufferedReader(new FileReader(xmlFilePath))) {

			String currentLine;

			while ((currentLine = bufferedReader.readLine()) != null) {
				xmlRequest.add(currentLine.trim());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

		return xmlRequest;

	}

	// write the js file
	public void jsWritter() {
	}

}
