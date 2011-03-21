package com.example.jdt.ast.util;

import java.util.List;

import org.eclipse.jdt.core.dom.MethodInvocation;
import org.eclipse.jdt.core.dom.SimpleName;
import org.eclipse.jdt.internal.corext.dom.ASTNodes;

public abstract class MethodInvocationUtils {
	
	public static SimpleName getInvokingVariableName(MethodInvocation mi) {
		List<SimpleName> names = ASTQuery.find(
				mi,
				SimpleName.class, 
				new IFilter<SimpleName>(){
					public boolean filter(SimpleName name, List<SimpleName> results) {
						if(ASTNodes.getVariableBinding(name) != null){
							results.add(name);
							return false;
						}
						return true;
					}
				}
		);
		return names.isEmpty() ? null : names.get(0);
	}

}
