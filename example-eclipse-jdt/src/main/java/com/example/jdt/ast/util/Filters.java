package com.example.jdt.ast.util;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

public abstract class Filters {
	
	public static <T extends ASTNode> IFilter<T> first(Class<T> clazz) {
		return new IFilter<T>(){
			public boolean filter(T node, List<T> results) {
				results.add(node);
				return false;
			}
		};
	}
	
	public static <T extends ASTNode> IFilter<T> all(Class<T> clazz) {
		return new IFilter<T>() {
			public boolean filter(T node, List<T> results) {
				results.add(node);
				return true;
			}
		};
	}
}
