/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.application.backend.utility;

import java.io.IOException;
import java.util.Properties;

/**
 *
 * @author Tanzil
 */
public class PropertyReader {

	public String loadPropertiesValues(String key) throws IOException {
		Properties prop = new Properties();
		String propertiesValue = "";
		try {
			// load a properties file from class path, inside static method
			prop.load(PropertyReader.class.getClassLoader().getResourceAsStream("application.properties"));

			propertiesValue = prop.getProperty(key);

		} catch (IOException ex) {
			ex.printStackTrace();
		}catch (Exception ex) {
			ex.printStackTrace();
		}
		return propertiesValue;
	}

}
