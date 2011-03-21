package com.example.jdt.ast.util;

import java.util.List;

import org.eclipse.jdt.core.dom.ASTNode;

public interface IFilter<T extends ASTNode> {
	
	boolean filter(T node, List<T> results);
	
}
