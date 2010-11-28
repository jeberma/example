package com.example.plovr.mojo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public class Config {
	
	/**
	 * @parameter
	 */
	protected String id;
	
	/**
	 * @parameter
	 */
	protected String paths;
	
	/**
	 * @parameter
	 */
	protected String inputs;
	
	/**
	 * @parameter
	 */
	protected String mode;
	
	/**
	 * @parameter
	 */
	protected String level;
	
	/**
	 * @parameter
	 */
	protected Boolean prettyPrint;
	
	/**
	 * @parameter
	 */
	protected File closureLibrary;
	
	/**
	 * @parameter
	 */
	protected File workDirectory;
	
	public String toJson() {
		JsonObject json = new JsonObject();
		if(id != null) {
			json.addProperty("id", id);
		}
		if(inputs != null) {
			json.add("inputs", filenamesToArray(inputs));
		}
		if(paths != null) {
			json.add("paths", filenamesToArray(paths));
		}
		if(mode != null) {
			json.addProperty("mode", mode);
		}
		if(level != null) {
			json.addProperty("level", level);
		}
		if(prettyPrint != null) {
			json.addProperty("pretty-print", prettyPrint);
		}
		if(closureLibrary != null) {
			json.addProperty("closure-library", closureLibrary.getAbsolutePath());
		}
		return new Gson().toJson(json);
	}
	
	public File writeToFile() throws IOException {
		FileUtils.forceMkdir(workDirectory);
		File output = new File(workDirectory, id + "-config.js");
		FileUtils.writeStringToFile(output, toJson(), "utf-8");
		return output;
	}
	
	public List<String> validate() {
		List<String> errors = new ArrayList<String>();
		if(id == null) {
			errors.add("id is required");
		}
		return errors;
	}
	
	private JsonArray filenamesToArray(String paths) {
		JsonArray array = new JsonArray();
		for(String each: StringUtils.split(paths, ",")) {
			each = FilenameUtils.separatorsToSystem(StringUtils.trim(each));
			array.add(new JsonPrimitive((each)));
		}
		return array;
	}

}
