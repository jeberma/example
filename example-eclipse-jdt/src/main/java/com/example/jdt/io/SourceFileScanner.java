package com.example.jdt.io;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FilenameUtils;

public class SourceFileScanner extends DirectoryWalker {

	public Collection<File> scan(File dir) throws IOException {
		Set<File> result = new HashSet<File>();
		walk(dir, result);
		return result;
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void handleFile(File file, int depth, @SuppressWarnings("rawtypes") Collection results)
			throws IOException {
		if("java".equals(FilenameUtils.getExtension(file.getAbsolutePath()))) {
			results.add(file);
		}
	}
}
