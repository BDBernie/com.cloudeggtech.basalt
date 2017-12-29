package com.cloudeggtech.basalt.protocol.oxm.parsing;

import java.util.List;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.ProtocolChain;
import com.cloudeggtech.basalt.protocol.core.stanza.Stanza;
import com.cloudeggtech.basalt.protocol.core.stream.Feature;
import com.cloudeggtech.basalt.protocol.core.stream.Features;

public class FlawedProtocolObject {
	public static final Protocol PROTOCOL = new Protocol("urn:lep:flawed", "object");
	
	private List<ProtocolChain> flaws;
	
	public FlawedProtocolObject() {}
	
	public FlawedProtocolObject(List<ProtocolChain> flaws) {
		this.flaws = flaws;
	}
	
	public List<ProtocolChain> getFlaws() {
		return flaws;
	}
	
	public void setFlaws(List<ProtocolChain> flaws) {
		this.flaws = flaws;
	}
	
	public static boolean isFlawed(Object object) {
		if (object instanceof Stanza) {
			return ((Stanza)object).getObjectProtocols().containsKey(FlawedProtocolObject.class);
		} else if (object instanceof Features) {
			Features features = (Features)object;
			if (features.getFeatures() == null || features.getFeatures().isEmpty())
				return false;
			
			for (Feature feature : features.getFeatures()) {
				if (feature instanceof FlawedProtocolFeature)
					return true;
			}
			
			return false;
		} else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("FlawedProtocolObject[");
		if (flaws != null && flaws.size() > 0) {
			for (ProtocolChain protocolChain : flaws) {
				sb.append(protocolChain.toString()).append(',');
			}
			
			if (sb.charAt(sb.length() - 1) == ',') {
				sb.deleteCharAt(sb.length() - 1);
			}
		}		
		sb.append(']');
		
		return sb.toString();
	}
}
