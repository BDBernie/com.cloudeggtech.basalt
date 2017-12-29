package com.cloudeggtech.basalt.protocol.oxm.parsers.error;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.stanza.error.StanzaError;

public class StanzaErrorDetailsParserFactory extends ErrorDetailsParserFactory {

	@Override
	public Protocol getProtocol() {
		return StanzaError.PROTOCOL;
	}

}
