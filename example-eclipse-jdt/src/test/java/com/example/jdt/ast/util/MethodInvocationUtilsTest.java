package com.example.jdt.ast.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.internal.corext.dom.ASTNodes;
import org.eclipse.jdt.internal.corext.dom.LinkedNodeFinder;
import org.eclipse.jdt.internal.ui.text.correction.ASTResolving;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MethodInvocationUtilsTest extends AbstractCompilationUnitTest {
	
	private static CompilationUnit resource;
	
	private static MethodDeclaration method1;
	
	private static MethodDeclaration method2;
	
	private static MethodDeclaration method3;
	
	@BeforeClass
	public static void setUpClass() {
		resource = createCompilationUnit(MethodInvocationUtilsResource.class);
		method1 = ASTQuery.findMethodDeclarationsByName(resource, "method1").get(0);
		method2 = ASTQuery.findMethodDeclarationsByName(resource, "method2").get(0);
		method3 = ASTQuery.findMethodDeclarationsByName(resource, "method3").get(0);
	}
	
	@AfterClass 
	public static void tearDownClass() {
		resource = null;
		method1 = null;
		method2 = null;
		method3 = null;
	}
	
	@Test
	public void getInvokingVariableName_basic() {
		MethodInvocation mi = ASTQuery.findMethodInvocationsByName(method1, "add").get(0);
		SimpleName name = MethodInvocationUtils.getInvokingVariableName(mi);
		assertEquals("list", name.getIdentifier());
	}
	
	@Test
	public void getInvokingVariableName_cast() {
		MethodInvocation mi =ASTQuery.findMethodInvocationsByName(method1, "add").get(1);
		SimpleName name = MethodInvocationUtils.getInvokingVariableName(mi);
		assertEquals("list", name.getIdentifier());
	}
	
	@Test
	public void getInvokingVariableName_field() {
		MethodInvocation mi = ASTQuery.findMethodInvocationsByName(method3, "add").get(1);
		SimpleName name = MethodInvocationUtils.getInvokingVariableName(mi);
		
		assertEquals("list", name.getIdentifier());
		assertNotNull(ASTNodes.getFieldBinding(name));
		
		System.out.println(ASTNodes.getReceiverTypeBinding(mi));
		
		for(SimpleName each : LinkedNodeFinder.findByNode(method3, name)){
			System.out.println(ASTResolving.findParentStatement(each));
		}
	}

}
