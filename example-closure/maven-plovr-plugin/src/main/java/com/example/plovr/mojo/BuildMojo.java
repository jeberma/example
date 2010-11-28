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
			compile(each);
		}
	}
	
	private void compile(BuildConfig config) throws MojoExecutionException {
		if(config.outputFile == null) {
			throw new MojoExecutionException("no output file specified");
		}
		try {
			File configFile = config.writeToFile();
			org.plovr.Config plovrConfig = ConfigParser.parseFile(configFile);
			Compilation compilation =  CompileRequestHandler.compile(plovrConfig);
			
			logWarnings(compilation.getResult());
			assertNoErrors(compilation.getResult());
			FileUtils.writeStringToFile(config.outputFile, compilation.getCompiledCode());
		} catch(IOException e) {
			throw new MojoExecutionException("failed to write file", e);
		} catch(CheckedSoySyntaxException e) {
			throw new MojoExecutionException("compilation failed", e);
		} catch(MissingProvideException e) {
			throw new MojoExecutionException("compilation failed", e);
		}
	}
}
