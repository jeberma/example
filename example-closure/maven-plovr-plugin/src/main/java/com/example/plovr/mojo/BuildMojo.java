package com.example.plovr.mojo;

import java.io.File;
import java.util.List;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

/**
 * Goal that executes the plovr build command.
 * 
 * @goal build
 * @phase package
 */
public class BuildMojo extends AbstractMojo {
	
	/**
	 * @parameter
	 */
	private List<BuildConfig> buildConfigs;
	
	/**
	 * @parameter expression="${project.build.directory}/plovr"
	 */
	private File workDirectory;
	
	public void execute() throws MojoExecutionException {
		if(buildConfigs == null) {
			return;
		}
		for(BuildConfig each : buildConfigs) {
			if(each.workDirectory == null) {
				each.workDirectory = workDirectory;
			}
			each.compile(getLog());
		}
	}
}
