package com.originatelabs.example.gwt.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.ServiceDefTarget;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class Application implements EntryPoint {

	public void onModuleLoad() {
		Image img = new Image("http://code.google.com/webtoolkit/logo-185x175.png");
		Button button = new Button("Click me");
		
		VerticalPanel vPanel = new VerticalPanel();
		vPanel.addStyleName("widePanel");
		vPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		vPanel.add(img);
		vPanel.add(button);
		
		RootPanel.get().add(vPanel);
		
		final DialogBox dialogBox = new DialogBox();
		dialogBox.setText("Welcome to GWT");
		dialogBox.setAnimationEnabled(true);
		Button closeButton = new Button("close");
		VerticalPanel dialogVPanel = new VerticalPanel();
		dialogVPanel.setWidth("100%");
		dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_CENTER);
		dialogVPanel.add(closeButton);
		
		closeButton.addClickListener(new ClickListener() {
			public void onClick(Widget sender) {
				dialogBox.hide();
			}
		});
		
		dialogBox.setWidget(dialogVPanel);
		
		button.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				dialogBox.center();
				dialogBox.show();
			}
		});
		
		final Label label = new Label();
		final Button rpcButton = new Button("Run GWT Async RPC!");
		rpcButton.setTitle("This will execute the RPC call to the Java server");
		rpcButton.addClickListener(new ClickListener(){
			public void onClick(Widget sender) {
				SampleRemoteServiceAsync sampleRemoteService = 
					(SampleRemoteServiceAsync)GWT.create(SampleRemoteService.class);
				
				ServiceDefTarget endpoint = (ServiceDefTarget)sampleRemoteService;
				endpoint.setServiceEntryPoint(GWT.getModuleBaseURL() + "sampleRemoteService");
				sampleRemoteService.doComplimentMe(new AsyncCallback<String>() {
					public void onSuccess(String result) {
						label.setText(result);
					}
					public void onFailure(Throwable t) {
						label.setText("DAMMIT! This didn't work");
					}
				});
			}
		});
		
		RootPanel.get().add(new HTML("<br/><br/>"));
		RootPanel.get().add(label);
		RootPanel.get().add(rpcButton);
		RootPanel.get().add(new HTML("<br/><br/>"));
		RootPanel.get().add(new HTML("change URL to append\"?locale=fr\" to see the fancy french text"));
		RootPanel.get().add(new HTML("<br/><br/>"));
	}
}
