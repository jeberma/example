package com.example.plovr.mojo;

import java.io.File;
import java.util.List;

public class BuildConfig extends Config {
	
	/**
	 * @parameter
	 */
	protected File outputFile;
	
	@Override
	public List<String> validate() {
		List<String> errors = super.validate();
		if(outputFile == null) {
			errors.add("outputFile is required");
		}
		return errors;
	}

}
