package com.example.jdt.ast.util;

import static ch.lambdaj.Lambda.collect;
import static ch.lambdaj.Lambda.on;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.DirectoryWalker;
import org.apache.commons.io.FilenameUtils;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.FileASTRequestor;
import org.junit.BeforeClass;

public abstract class AbstractCompilationUnitTest {
	
	private static File testSourceDir;
	
	private static File sourceDir;
	
	private static File dependencyDir;
	
	private static List<String> classPaths;
	
	private static File outputDir;
	
	private static File testOutputDir;
	
	@BeforeClass
	public static void resolveDirs() throws Exception {
		Properties props = new Properties();
		props.load(AbstractCompilationUnitTest.class.getResourceAsStream("/environment.properties"));
		String basedir = props.getProperty("basedir");
		
		testSourceDir = new File(basedir, FilenameUtils.separatorsToSystem("src/test/java"));
		sourceDir = new File(basedir, FilenameUtils.separatorsToSystem("src/main/java"));
		dependencyDir = new File(basedir, FilenameUtils.separatorsToSystem("target/dependency"));
		outputDir = new File(basedir, FilenameUtils.separatorsToSystem("target/classes"));
		testOutputDir = new File(basedir, FilenameUtils.separatorsToSystem("target/test-classes"));
		
		List<File> jars = new JarFinder().find(dependencyDir);
		classPaths = collect(jars, on(File.class).getAbsolutePath());
		classPaths.add(outputDir.getAbsolutePath());
		classPaths.add(testOutputDir.getAbsolutePath());
	}
	
	// see http://www.eclipse.org/forums/index.php?t=msg&goto=657704&S=b6114aad2af819995ea6e8566d40c854
	protected static CompilationUnit createCompilationUnit(Class<?> clazz) {
		try {
			File sourceFile = new File(
				testSourceDir,
				clazz.getName().replaceAll("\\.", File.separator) + ".java"
			);
			
			ASTParser parser = ASTParser.newParser(AST.JLS3);
			parser.setKind(ASTParser.K_COMPILATION_UNIT);
			parser.setEnvironment(
					classPaths.toArray(new String[]{}),
					new String[]{sourceDir.getAbsolutePath(), testSourceDir.getAbsolutePath()}, 
					null, 
					true
			);
			
			final List<CompilationUnit> result = new ArrayList<CompilationUnit>();
			parser.setResolveBindings(true);
			parser.createASTs(
					new String[] {sourceFile.getAbsolutePath()}, 
					null, 
					new String[0], 
					new FileASTRequestor(){
						public void acceptAST(String sourceFilePath, CompilationUnit ast) {
							result.add(ast);
						}
					}, 
					null
			);
			return result.get(0);
		}
		catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private static class JarFinder extends DirectoryWalker {
		
		public List<File> find(File basedir) throws IOException {
			List<File> result = new ArrayList<File>();
			walk(basedir, result);
			return result;
		}
		
		@Override
		@SuppressWarnings({"unchecked","rawtypes"})
		protected void handleFile(File file, int depth, Collection results)
				throws IOException {
			if("jar".equals(FilenameUtils.getExtension(file.getAbsolutePath()))) {
				results.add(file);
			}
		}
	}
}
