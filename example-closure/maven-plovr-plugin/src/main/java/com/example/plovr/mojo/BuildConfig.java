package com.example.plovr.mojo;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.plovr.CheckedSoySyntaxException;
import org.plovr.Compilation;
import org.plovr.CompileRequestHandler;
import org.plovr.ConfigParser;
import org.plovr.MissingProvideException;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.javascript.jscomp.JSError;

public class BuildConfig {
	
	private static final String KEY_PATHS = "paths";
	
	private static final String KEY_INPUTS ="inputs";
	
	private static final String KEY_CLOSURE_LIBRARY = "closure-library";
	
	private static final String KEY_ID = "id";
	
	/**
	 * @parameter
	 */
	File outputFile;
	
	/**
	 * @parameter
	 */
	File sourceMapFile;
	
	/**
	 * @parameter
	 */
	File workDirectory;
	
	/**
	 * @parameter
	 */
	String options;
	
	private JsonObject json;
	
	private Log log;
	
	public void compile(Log log) throws MojoExecutionException {
		this.log = log;
		this.json = parseOptions();
		sanitize();
		validate();
		File configFile = writeConfigFile();
		Compilation compilation =  compile(configFile);
		
		logWarnings(compilation);
		assertNoErrors(compilation);
		writeCompiledCode(compilation);
		writeSourceMap(compilation);
	}
	
	private JsonObject parseOptions() {
		return (JsonObject)new JsonParser().parse(options);
	}
	
	private void sanitize() {
		fixPathnames(KEY_PATHS);
		fixPathnames(KEY_INPUTS);
		fixPathnames(KEY_CLOSURE_LIBRARY);
	}
	
	private void fixPathnames(String key) {
		if(json.has(key)) {
			JsonElement paths = json.get(key);
			json.remove(key);
			if(paths.isJsonArray()) {
				json.add(key, fixPathnames((JsonArray)paths));
			} else {
				json.addProperty(key, fixPathname(paths.getAsString()));
			}
		}
	}
	
	private JsonArray fixPathnames(JsonArray input) {
		JsonArray result = new JsonArray();
		for(int i=0; i<input.size(); i++) {
			result.add(
					new JsonPrimitive(
							fixPathname(input.get(i).getAsString())
					)
			);
		}
		return result;
	}
	
	private String fixPathname(String input) {
		return FilenameUtils.separatorsToSystem(StringUtils.trim(input));
	}
	
	private void validate() throws MojoExecutionException {
		if(!json.has(KEY_ID)) {
			throw new MojoExecutionException("options.id is required");
		}
	}
	
	private File writeConfigFile() throws MojoExecutionException {
		try {
			FileUtils.forceMkdir(workDirectory);
			File file = new File(workDirectory, json.get(KEY_ID).getAsString());
			FileUtils.writeStringToFile(file, new Gson().toJson(json), CharEncoding.UTF_8);
			return file;
		} catch(IOException e) {
			throw new MojoExecutionException("failed to write config file", e);
		}
	}
	
	private Compilation compile(File configFile) throws MojoExecutionException {
		try {
			return CompileRequestHandler.compile(ConfigParser.parseFile(configFile));
		} catch(IOException e) {
			throw new MojoExecutionException("failed to write config file", e);
		} catch(CheckedSoySyntaxException e) {
			throw new MojoExecutionException("compilation failed", e);
		} catch(MissingProvideException e) {
			throw new MojoExecutionException("compilation failed", e);
		}
	}
	
	private void logWarnings(Compilation compilation) {
		for(JSError each : compilation.getResult().warnings) {
			log.warn(each.toString());
		}
	}
	
	private void assertNoErrors(Compilation compilation) throws MojoExecutionException {
		if(compilation.getResult().errors.length > 0) {
			for(JSError each : compilation.getResult().errors) {
				log.error(each.toString());
			}
			throw new MojoExecutionException(
					"BUILD FAILED: " + compilation.getResult().errors.length + 
					" Errors, " + compilation.getResult().warnings.length + " Warnings"
			);
		}
	}

	private void writeSourceMap(Compilation compilation) throws MojoExecutionException {
		if(sourceMapFile == null) {
			return;
		}
		try {
			StringBuilder sb = new StringBuilder();
			compilation.getResult().sourceMap.appendTo(sb, json.get(KEY_ID).getAsString());
			FileUtils.writeStringToFile(sourceMapFile, sb.toString());
		} catch(IOException e) {
			throw new MojoExecutionException("failed to write source map", e);
		}
	}
	
	private void writeCompiledCode(Compilation compilation) throws MojoExecutionException {
		if(outputFile == null) {
			return;
		}
		try {
			FileUtils.writeStringToFile(outputFile, compilation.getCompiledCode());
		} catch(IOException e) {
			throw new MojoExecutionException("failed to write output file", e);
		}
	}
}
