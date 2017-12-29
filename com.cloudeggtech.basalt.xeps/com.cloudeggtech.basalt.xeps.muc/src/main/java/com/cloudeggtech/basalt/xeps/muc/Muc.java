package com.cloudeggtech.basalt.xeps.muc;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.ProtocolObject;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.TextOnly;

@ProtocolObject(namespace="http://jabber.org/protocol/muc", localName="x")
public class Muc {
	public static final Protocol PROTOCOL = new Protocol("http://jabber.org/protocol/muc", "x");
	
	private History history;
	@TextOnly
	private String password;
	
	public History getHistory() {
		return history;
	}
	
	public void setHistory(History history) {
		this.history = history;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
}
