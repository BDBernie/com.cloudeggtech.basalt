package com.cloudeggtech.basalt.protocol.core.stream.sasl;

import com.cloudeggtech.basalt.protocol.core.Protocol;

public class Auth {
	public static final Protocol PROTOCOL = new Protocol("urn:ietf:params:xml:ns:xmpp-sasl", "auth");
	public static final String ATTRIBUTE_NAME_MECHANISM = "mechanism";
	
	private String mechanism;
	
	public Auth() {}
	
	public Auth(String mechanism) {
		this.mechanism = mechanism;
	}

	public String getMechanism() {
		return mechanism;
	}

	public void setMechanism(String mechanism) {
		this.mechanism = mechanism;
	}
	
}
