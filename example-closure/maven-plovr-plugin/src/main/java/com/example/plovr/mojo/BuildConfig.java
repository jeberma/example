package com.example.plovr.mojo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.logging.Log;
import org.plovr.CheckedSoySyntaxException;
import org.plovr.Compilation;
import org.plovr.CompileRequestHandler;
import org.plovr.Config;
import org.plovr.ConfigParser;
import org.plovr.JsInput;
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
	File dependencyFile;
	
	/**
	 * @parameter
	 */
	File workDirectory;
	
	/**
	 * @parameter
	 */
	String options;
	
	/**
	 * @parameter expression="${basedir}"
	 * @readonly
	 */
	File basedir;
	
	private JsonObject json;
	
	private Log log;
	
	public void compile(Log log) throws MojoExecutionException {
		this.log = log;
		this.json = parseOptions();
		
		sanitize();
		applyDefaultInputs();
		validate();
		
		Config config = parseConfig(writeConfigFile());
		Compilation compilation =  compile(config);
		
		logWarnings(compilation);
		assertNoErrors(compilation);
		writeCompiledCode(compilation);
		writeSourceMap(compilation);
		writeDependencyFile(config);
	}
	
	private JsonObject parseOptions() {
		return (JsonObject)new JsonParser().parse(options);
	}
	
	private void applyDefaultInputs() {
		if(json.has(KEY_INPUTS)) {
			return;
		}
		List<File> files = new JsFileScanner().scan(getPaths());
		JsonArray array = new JsonArray();
		for(File each : files) {
			array.add(new JsonPrimitive(each.getAbsolutePath()));
		}
		json.add(KEY_INPUTS, array);
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
		input = FilenameUtils.separatorsToSystem(StringUtils.trim(input));
		File file = new File(input);
		if(file.isAbsolute()) {
			return input;
		} else {
			return new File(basedir, input).getAbsolutePath();
		}
	}
	
	private List<File> getPaths() {
		List<File> result = new ArrayList<File>();
		
		if(json.get(KEY_PATHS) instanceof JsonArray) {
			JsonArray array = (JsonArray)json.get(KEY_PATHS);
			for(int i=0; i<array.size(); i++) {
				result.add(new File(array.get(i).getAsString()));
			}
		} else if(json.has(KEY_PATHS)){
			result.add(new File(json.get(KEY_PATHS).getAsString()));
		}
		return result;
	}
	
	private void validate() throws MojoExecutionException {
		if(!json.has(KEY_ID)) {
			throw new MojoExecutionException("options.id is required");
		}
	}
	
	private File writeConfigFile() throws MojoExecutionException {
		try {
			FileUtils.forceMkdir(workDirectory);
			File file = new File(workDirectory, json.get(KEY_ID).getAsString() + ".js");
			FileUtils.writeStringToFile(file, new Gson().toJson(json), CharEncoding.UTF_8);
			return file;
		} catch(IOException e) {
			throw new MojoExecutionException("failed to write config file", e);
		}
	}
	
	private Config parseConfig(File configFile) throws MojoExecutionException {
		try {
			return ConfigParser.parseFile(configFile);
		} catch(IOException e) {
			throw new MojoExecutionException("failed to parse config file", e);
		}
	}
	
	private void writeDependencyFile(Config config) throws MojoExecutionException {
		if(dependencyFile == null) {
			return;
		}
		if(!json.has(KEY_CLOSURE_LIBRARY)) {
			throw new MojoExecutionException("cannot write dependency file.  closure-library option is required.");
		}
		try {
			LinkedHashSet<JsDependency> dependencies = resolveDependencies(config);
			FileUtils.writeStringToFile(dependencyFile, googAddDependency(dependencies));
		} catch(IOException e) {
			throw new MojoExecutionException("could not write dependency file", e);
		}
	}
	
	private String googAddDependency(Collection<JsDependency> dependencies) {
		StringBuilder sb = new StringBuilder();
		for(JsDependency each : dependencies) {
			sb.append("goog.addDependency(");
				sb.append("'").append(getPathRelativeToClosureLibrary(each.getSourceFile())).append("'");
				if(!each.getProvides().isEmpty()) {
					sb.append(",[");
						for(String provide : each.getProvides()) {
							sb.append("'").append(provide).append("',");
						}
						sb.deleteCharAt(sb.length() - 1); // remove last ,
					sb.append("]");
				}
				if(!each.getRequires().isEmpty()) {
					sb.append(",[");
						for(String require : each.getRequires()) {
							sb.append("'").append(require).append("',");
						}
						sb.deleteCharAt(sb.length() -1); // remove last ,
					sb.append("]");
				}
			sb.append(");\n");
		}
		return sb.toString();
	}
	
	private LinkedHashSet<JsDependency> resolveDependencies(Config config) throws MojoExecutionException {
		try {
			List<JsInput> jsInputs = config.getManifest().getInputsInCompilationOrder();
			return new JsDependencyResolver(jsInputs).resolve(getPaths());
		}catch(Exception e) {
			throw new MojoExecutionException("could not resolve dependencies", e);
		}
	}
	
	private String getPathRelativeToClosureLibrary(File file) {
		String closureLibrary = new File(json.get(KEY_CLOSURE_LIBRARY).getAsString()).getAbsolutePath();
		
		String[] target = file.getAbsolutePath().split(Pattern.quote(File.separator));
		String[] base = closureLibrary.split(Pattern.quote(File.separator));
			
		int commonIndex = 0;
		for(int i=0; i< target.length && i<base.length; i++) {
			if(target[i].equals(base[i])) {
				commonIndex++;
			}
		}
		
		StringBuilder result = new StringBuilder();
		for(int i=commonIndex; i<base.length; i++) {
			result.append("..").append(File.separator);
		}
		
		for(int i=commonIndex; i<target.length; i++) {
			result.append(target[i]);
			if(i < target.length -1) {
				result.append(File.separator);
			}
		}
		return result.toString();
	}
	
	private Compilation compile(Config config) throws MojoExecutionException {
		try {
			return CompileRequestHandler.compile(config);
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
	
	private static class JsDependencyResolver extends DirectoryWalker {
		private List<JsInput> jsFiles;
		
		public JsDependencyResolver(List<JsInput> jsFiles) {
			super();
			this.jsFiles = new ArrayList<JsInput>();
			this.jsFiles.addAll(jsFiles);
		}
		
		public LinkedHashSet<JsDependency> resolve(List<File> paths) {
			LinkedHashSet<JsDependency> results = new LinkedHashSet<JsDependency>();
			try {
				for(File path : paths) {
					walk(path, results);
				}
				return results;
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		@SuppressWarnings("unchecked")
		protected void handleFile(File file, int depth, Collection results) {
			for(JsInput jsFile : jsFiles) {
				if(file.getAbsolutePath().contains(jsFile.getName())) {
					results.add(new JsDependency(file, jsFile.getProvides(), jsFile.getRequires()));
					jsFiles.remove(jsFile);
					break;
				}
			}
		}
	}
	
	private static class JsFileScanner extends DirectoryWalker {
		
		public List<File> scan(List<File> paths) {
			List<File> result = new ArrayList<File>();
			try {
				for(File path : paths) {
					walk(path, result);
				}
				return result;
			} catch(IOException e) {
				throw new RuntimeException(e);
			}
		}
		
		@Override
		@SuppressWarnings("unchecked")
		protected void handleFile(File file, int depth, Collection results) {
			String extension = FilenameUtils.getExtension(file.getAbsolutePath()).toLowerCase();
			if("soy".equals(extension) || "js".equals(extension)) {
				results.add(file);
			}
		}
	}
}
