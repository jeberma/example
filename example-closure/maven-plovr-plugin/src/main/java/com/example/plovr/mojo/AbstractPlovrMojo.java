package com.example.plovr.mojo;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import com.google.javascript.jscomp.JSError;
import com.google.javascript.jscomp.Result;

public abstract class AbstractPlovrMojo extends AbstractMojo {
	
	/**
	 * A comma separated string of path names
	 * @parameter
	 */
	protected String paths;
	
	/**
	 * A comma separated string of input names
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
	 * @parameter expression="${project.build.directory}/plovr"
	 */
	protected File workDirectory;
	
	protected void setDefaults(Config config) {
		if(config.paths == null) {
			config.paths = paths;
		}
		if(config.inputs == null) {
			config.inputs = inputs;
		}
		if(config.mode == null) {
			config.mode = mode;
		}
		if(config.level == null) {
			config.level = level;
		}
		if(config.prettyPrint == null) {
			config.prettyPrint = prettyPrint;
		}
		if(config.closureLibrary == null) {
			config.closureLibrary = closureLibrary;
		}
		if(config.workDirectory == null) {
			config.workDirectory = workDirectory;
		}
	}
	
	protected void validate(Config config) throws MojoExecutionException {
		List<String> errors = config.validate();
		if(!errors.isEmpty()) {
			StringBuilder sb = new StringBuilder();
			for(String each : errors) {
				sb.append(each).append("\n");
			}
			throw new MojoExecutionException(sb.toString());
		}
	}
	
	protected void logWarnings(Result result) {
		for(JSError each : result.warnings) {
			getLog().warn(each.toString());
		}
	}
	
	protected void assertNoErrors(Result result) throws MojoExecutionException {
		if(result.errors.length > 0) {
			for(JSError each : result.errors) {
				getLog().error(each.toString());
			}
			throw new MojoExecutionException("BUILD FAILED: " + result.errors.length + " Errors, " + result.warnings.length + " Warnings");
		}
	}

}
