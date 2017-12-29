package com.cloudeggtech.basalt.leps.im.subscription;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.ProtocolObject;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.TextOnly;

@ProtocolObject(namespace="urn:lep:subscription", localName="subscribe")
public class Subscribe {
	public static final Protocol PROTOCOL = new Protocol("urn:lep:subscription", "subscribe");
	
	@TextOnly
	private String message;
	
	public Subscribe() {}
	
	public Subscribe(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
}
