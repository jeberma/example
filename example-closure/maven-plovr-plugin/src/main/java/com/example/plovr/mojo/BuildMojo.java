package com.example.plovr.mojo;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.MojoExecutionException;
import org.plovr.CheckedSoySyntaxException;
import org.plovr.Compilation;
import org.plovr.CompileRequestHandler;
import org.plovr.ConfigParser;
import org.plovr.MissingProvideException;

import com.google.javascript.jscomp.Result;

/**
 * Goal that executes the plovr build command.
 * 
 * @goal build
 * @phase package
 */
public class BuildMojo extends AbstractPlovrMojo {
	
	/**
	 * @parameter
	 */
	private List<BuildConfig> buildConfigs;
	
	public void execute() throws MojoExecutionException {
		if(buildConfigs == null) {
			return;
		}
		for(BuildConfig each : buildConfigs) {
			setDefaults(each);
			validate(each);
			Compilation compilation = compile(each);
			logWarnings(compilation.getResult());
			assertNoErrors(compilation.getResult());
			writeCompiledCode(each.outputFile, compilation);
			writeSourceMap(compilation.getResult(), each);
		}
	}
	
	private Compilation compile(BuildConfig config) throws MojoExecutionException {
		if(config.outputFile == null) {
			throw new MojoExecutionException("no output file specified");
		}
		try {
			File configFile = config.writeToFile();
			return CompileRequestHandler.compile(ConfigParser.parseFile(configFile));
		} catch(IOException e) {
			throw new MojoExecutionException("failed to write config file", e);
		} catch(CheckedSoySyntaxException e) {
			throw new MojoExecutionException("compilation failed", e);
		} catch(MissingProvideException e) {
			throw new MojoExecutionException("compilation failed", e);
		}
	}
	
	private void writeCompiledCode(File outputFile, Compilation compilation) throws MojoExecutionException {
		try {
			FileUtils.writeStringToFile(outputFile, compilation.getCompiledCode());
		} catch(IOException e) {
			throw new MojoExecutionException("failed to write output file", e);
		}
	}
	
	private void writeSourceMap(Result result, BuildConfig config) throws MojoExecutionException {
		if(config.sourceMapFile == null) {
			return;
		}
		try {
			StringBuilder sb = new StringBuilder();
			result.sourceMap.appendTo(sb, config.id);
			FileUtils.writeStringToFile(config.sourceMapFile, sb.toString());
		} 
		catch(IOException e) {
			throw new MojoExecutionException("failed to write source map", e);
		}
	}
}
