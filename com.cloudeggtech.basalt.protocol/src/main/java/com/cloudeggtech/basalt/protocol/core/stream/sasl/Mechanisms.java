package com.cloudeggtech.basalt.protocol.core.stream.sasl;

import java.util.ArrayList;
import java.util.List;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.stream.Feature;

public class Mechanisms implements Feature {
	public static final Protocol PROTOCOL = new Protocol("urn:ietf:params:xml:ns:xmpp-sasl", "mechanisms");
	
	private List<String> mechanisms;

	public List<String> getMechanisms() {
		if (mechanisms == null)
			mechanisms = new ArrayList<>();
		
		return mechanisms;
	}

	public void setMechanisms(List<String> mechanisms) {
		this.mechanisms = mechanisms;
	}
	
}
