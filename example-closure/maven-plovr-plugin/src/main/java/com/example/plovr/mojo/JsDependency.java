package com.example.plovr.mojo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

public class JsDependency {
	
	private final File sourceFile;
	
	private final List<String> provides;
	
	private final List<String> requires;
	
	public JsDependency(File sourceFile, List<String> provides, List<String> requires) {
		if(sourceFile == null) {
			throw new IllegalArgumentException();
		}
		this.sourceFile = sourceFile;
		this.provides = new ArrayList<String>();
		this.provides.addAll(provides);
		this.requires = new ArrayList<String>();
		this.requires.addAll(requires);
	}

	public File getSourceFile() {
		return sourceFile;
	}

	public List<String> getProvides() {
		return provides;
	}
	
	public List<String> getRequires() {
		return requires;
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof JsDependency)) {
			return false;
		}
		JsDependency rhs = (JsDependency)other;
		return new EqualsBuilder()
					.append(provides, rhs.provides)
					.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 47)
					.append(provides)
					.toHashCode();
	}
}
