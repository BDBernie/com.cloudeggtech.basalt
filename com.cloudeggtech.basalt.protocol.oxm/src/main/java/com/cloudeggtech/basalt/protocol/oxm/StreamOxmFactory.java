package com.cloudeggtech.basalt.protocol.oxm;

import com.cloudeggtech.basalt.protocol.core.IError;
import com.cloudeggtech.basalt.protocol.core.ProtocolChain;
import com.cloudeggtech.basalt.protocol.core.stream.Stream;
import com.cloudeggtech.basalt.protocol.core.stream.error.StreamError;
import com.cloudeggtech.basalt.protocol.oxm.annotation.AnnotatedParserFactory;
import com.cloudeggtech.basalt.protocol.oxm.parsers.core.stream.StreamParser;
import com.cloudeggtech.basalt.protocol.oxm.parsers.error.ErrorParserFactory;
import com.cloudeggtech.basalt.protocol.oxm.parsers.error.StreamErrorDetailsParserFactory;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParsingFactory;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatingFactory;
import com.cloudeggtech.basalt.protocol.oxm.translators.core.stream.StreamTranslatorFactory;
import com.cloudeggtech.basalt.protocol.oxm.translators.error.StreamErrorTranslatorFactory;

public class StreamOxmFactory extends OxmFactory {

	public StreamOxmFactory(IParsingFactory parsingFactory, ITranslatingFactory translatingFactory) {
		super(parsingFactory, translatingFactory);
		registerStreamNegotiationProtocols();
	}

	private void registerStreamNegotiationProtocols() {
		// register parsers;
		register(ProtocolChain.first(Stream.PROTOCOL), new AnnotatedParserFactory<>(StreamParser.class));
		register(ProtocolChain.first(StreamError.PROTOCOL), new ErrorParserFactory<>(IError.Type.STREAM));
		register(ProtocolChain.first(StreamError.PROTOCOL).next(StreamError.PROTOCOL_ERROR_DEFINED_CONDITION),
				new StreamErrorDetailsParserFactory());
		
		// register translators
		register(Stream.class, new StreamTranslatorFactory());
		register(StreamError.class, new StreamErrorTranslatorFactory());
	}

}
