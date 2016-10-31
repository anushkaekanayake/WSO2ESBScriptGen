package com.app.controller;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import com.app.service.IOService;
import com.app.service.JsonToJsParser;
import com.app.service.StructureIdentifire;
import com.app.util.AppUtils;
import com.app.util.Path;

/**
 * starting point
 * 
 * @author anushka.ekanayake
 *
 */
public class FlowController {
	public static void main(String[] args) throws FileNotFoundException {
		
		String filePath = "<path>\\TestAPI.xml";
		ArrayList<String> requestList = IOService.getConnection().fileReader(filePath);
		StructureIdentifire.getConnection().identifire(requestList, false);

		// generate code
		StructureIdentifire.getConnection().identifire(requestList, true);
		jsGenerator();

	}

	public static void jsGenerator() {
		JsonToJsParser.geneateBody(AppUtils.APITYPE);
	}

}
