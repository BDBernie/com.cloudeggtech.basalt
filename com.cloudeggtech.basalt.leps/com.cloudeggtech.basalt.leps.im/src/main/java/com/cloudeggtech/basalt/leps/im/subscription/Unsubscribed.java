package com.cloudeggtech.basalt.leps.im.subscription;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.ProtocolObject;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.TextOnly;

@ProtocolObject(namespace="urn:lep:subscription", localName="unsubscribed")
public class Unsubscribed {
	public static final Protocol PROTOCOL = new Protocol("urn:lep:subscription", "unsubscribed");
	
	@TextOnly
	private String reason;
	
	public Unsubscribed() {}
	
	public Unsubscribed(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}
	
	public void setReason(String reason) {
		this.reason = reason;
	}
}
