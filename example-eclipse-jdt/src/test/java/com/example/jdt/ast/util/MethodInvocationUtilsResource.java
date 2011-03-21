package com.example.jdt.ast.util;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.Context;
import android.provider.ContactsContract.Contacts;

public class MethodInvocationUtilsResource {
	
	private List<String> list;
	
	public void method1() {
		List<String> list = new ArrayList<String>();
		list.add("bar");
		((ArrayList<String>)list).add(new String("foo"));
	}
	
	public void method2(Context context) {
		((ContentResolver) context.getContentResolver()).query(
				Contacts.CONTENT_LOOKUP_URI, 
				null, 
				null, 
				null, 
				null
		);
	}
	
	public void method3() {
		List<String> list = new ArrayList<String>();
		list.add("foo");
		this.list.add("bar");
		list.remove("foo");
		this.list.remove("bar");
	}

}
