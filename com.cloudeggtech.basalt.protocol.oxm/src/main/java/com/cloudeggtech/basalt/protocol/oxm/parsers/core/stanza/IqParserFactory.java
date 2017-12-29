package com.cloudeggtech.basalt.protocol.oxm.parsers.core.stanza;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.ProtocolException;
import com.cloudeggtech.basalt.protocol.core.stanza.Iq;
import com.cloudeggtech.basalt.protocol.core.stanza.error.BadRequest;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IElementParser;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParser;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParserFactory;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParsingContext;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParsingPath;

public class IqParserFactory implements IParserFactory<Iq> {
	@Override
	public Protocol getProtocol() {
		return Iq.PROTOCOL;
	}

	@Override
	public IParser<Iq> create() {
		return new StanzaParser<Iq>() {

			@Override
			public Iq createObject() {
				return new Iq(Iq.Type.GET);
			}

			@Override
			protected IElementParser<Iq> doGetElementParser(IParsingPath parsingPath) {
				return null;
			}
			
			@Override
			protected void processType(IParsingContext<Iq> context, String value) {
				try {
					context.getObject().setType(Iq.Type.valueOf(value.toUpperCase()));
				} catch (IllegalArgumentException e) {
					throw new ProtocolException(new BadRequest(String.format("Invalid iq type '%s'.", value)));
				}
			}
			
			@Override
			public void processEmbeddedObject(IParsingContext<Iq> context,
					Protocol protocol, Object embedded) {
				super.processEmbeddedObject(context, protocol, embedded);
			}
		};
	}

}
