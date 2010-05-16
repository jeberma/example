package com.originatelabs.example.gwt.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SampleRemoteServiceAsync {
	
	public void doComplimentMe(AsyncCallback<String> callback);

}
