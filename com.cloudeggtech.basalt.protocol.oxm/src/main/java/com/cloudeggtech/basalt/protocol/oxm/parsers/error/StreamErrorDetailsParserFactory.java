package com.cloudeggtech.basalt.protocol.oxm.parsers.error;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.stream.error.StreamError;

public class StreamErrorDetailsParserFactory extends ErrorDetailsParserFactory {

	@Override
	public Protocol getProtocol() {
		return StreamError.PROTOCOL;
	}

}
