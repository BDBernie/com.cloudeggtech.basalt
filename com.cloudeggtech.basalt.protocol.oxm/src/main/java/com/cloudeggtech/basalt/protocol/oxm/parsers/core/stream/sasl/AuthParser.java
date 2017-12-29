package com.cloudeggtech.basalt.protocol.oxm.parsers.core.stream.sasl;

import java.util.List;

import com.cloudeggtech.basalt.protocol.core.ProtocolException;
import com.cloudeggtech.basalt.protocol.core.stream.error.BadFormat;
import com.cloudeggtech.basalt.protocol.core.stream.sasl.Auth;
import com.cloudeggtech.basalt.protocol.oxm.Attribute;
import com.cloudeggtech.basalt.protocol.oxm.Value;
import com.cloudeggtech.basalt.protocol.oxm.annotations.Parser;
import com.cloudeggtech.basalt.protocol.oxm.annotations.ProcessAttributes;
import com.cloudeggtech.basalt.protocol.oxm.annotations.ProcessText;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParsingContext;

@Parser(namespace="urn:ietf:params:xml:ns:xmpp-sasl", localName="auth", objectType=Auth.class)
public class AuthParser {
	@ProcessAttributes("/")
	public void processAttributes(IParsingContext<Auth> context, List<Attribute> attributes) {
		for (Attribute attribute : attributes) {
			if (Auth.ATTRIBUTE_NAME_MECHANISM.equals(attribute.getName()) && attribute.getPrefix() == null) {
				context.getObject().setMechanism(attribute.getValue().getString());
			}
		}
			
	}
	
	@ProcessText("/")
	public void processText(IParsingContext<Auth> context, Value<?> value) {
		if (context.getObject().getMechanism() == null)
			throw new ProtocolException(new BadFormat(String.format("Must have a mechanism attribute[protocol %s].", context.getProtocolChain())));
	}
}
