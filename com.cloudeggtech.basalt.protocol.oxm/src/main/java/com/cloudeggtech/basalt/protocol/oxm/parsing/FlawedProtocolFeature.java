package com.cloudeggtech.basalt.protocol.oxm.parsing;

import java.util.List;

import com.cloudeggtech.basalt.protocol.core.ProtocolChain;
import com.cloudeggtech.basalt.protocol.core.stream.Feature;

public class FlawedProtocolFeature extends FlawedProtocolObject implements Feature {
	public FlawedProtocolFeature() {
		super();
	}
	
	public FlawedProtocolFeature(List<ProtocolChain> flaws) {
		super(flaws);
	}
}
