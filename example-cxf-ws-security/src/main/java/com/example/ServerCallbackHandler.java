package com.example;

import java.io.IOException;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

import org.apache.ws.security.WSPasswordCallback;

/**
 * Simple password callback handler. This just handles two cases: matching the
 * username and password, and providing the password used for access to the
 * private key.
 */
public class ServerCallbackHandler implements CallbackHandler {
	public void handle(Callback[] callbacks) throws IOException,
			UnsupportedCallbackException {
		for (int i = 0; i < callbacks.length; i++) {
			WSPasswordCallback pwcb = (WSPasswordCallback) callbacks[i];
			String id = pwcb.getIdentifier();
			switch (pwcb.getUsage()) {
			case WSPasswordCallback.USERNAME_TOKEN_UNKNOWN:

				// used when plaintext password in message
				if (!"libuser".equals(id)
						|| !"books".equals(pwcb.getPassword())) {
					throw new UnsupportedCallbackException(callbacks[i],
							"check failed");
				}
				break;

			case WSPasswordCallback.DECRYPT:
			case WSPasswordCallback.SIGNATURE:

				// used to retrieve password for private key
				if ("serverkey".equals(id)) {
					pwcb.setPassword("serverpass");
				}
				break;
			}
		}
	}
}
