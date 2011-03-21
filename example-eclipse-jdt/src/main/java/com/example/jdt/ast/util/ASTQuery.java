package com.example.jdt.ast.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jdt.core.dom.ASTNode;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.internal.corext.dom.GenericVisitor;

public abstract class ASTQuery {
	
	public static <T extends ASTNode> List<T> find(final ASTNode node, final Class<T> clazz, final IFilter<T> filter) {
		if(node == null) {
			return new ArrayList<T>();
		}
		final List<T> result = new ArrayList<T>();
		node.accept(new GenericVisitor(){
			@Override @SuppressWarnings("unchecked")
			public boolean visitNode(ASTNode n) {
				if(clazz.isInstance(n)) {
					return filter.filter((T)n, result);
				}
				return true;
			}
		});
		return result;
	}
	
	public static List<MethodDeclaration> findMethodDeclarationsByName(ASTNode node, final String name) {
		return find(
				node, 
				MethodDeclaration.class,
				new IFilter<MethodDeclaration>(){
					public boolean filter(MethodDeclaration md, List<MethodDeclaration> result) {
						if(StringUtils.equals(name, md.getName().toString())) {
							result.add(md);
						}
						return true;
					}
				}
		);
	}
	
	public static List<MethodInvocation> findMethodInvocationsByName(ASTNode node, final String name) {
		return find(
				node,
				MethodInvocation.class,
				new IFilter<MethodInvocation>(){
					public boolean filter(MethodInvocation mi, List<MethodInvocation> results) {
						if(StringUtils.equals(mi.getName().getIdentifier(), name)) {
							results.add(mi);
						}
						return true;
					}
				}
		);
	}
	
	public static List<SimpleName> findSimpleNamesByIdentifier(ASTNode node, final String identifier) {
		return find(
				node, 
				SimpleName.class,
				new IFilter<SimpleName>(){
					public boolean filter(SimpleName sn, List<SimpleName> result) {
						if(StringUtils.equals(sn.getIdentifier(), identifier)) {
							result.add(sn);
						}
						return true;
					}
				}
		);
	}

}
