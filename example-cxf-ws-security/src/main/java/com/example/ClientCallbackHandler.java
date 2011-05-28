package com.example;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;

import org.apache.ws.security.WSPasswordCallback;

/**
 * Simple password callback handler. This just checks if the password for the
 * private key is being requested, and if so sets that value.
 */
public class ClientCallbackHandler implements CallbackHandler {
	public void handle(Callback[] callbacks) throws IOException {
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
			String id = pwcb.getIdentifier();
			int usage = pwcb.getUsage();
			if (usage == WSPasswordCallback.DECRYPT
					|| usage == WSPasswordCallback.SIGNATURE) {

				// used to retrieve password for private key
				if ("clientkey".equals(id)) {
					pwcb.setPassword("clientpass");
				}

			}
		}
	}
}
