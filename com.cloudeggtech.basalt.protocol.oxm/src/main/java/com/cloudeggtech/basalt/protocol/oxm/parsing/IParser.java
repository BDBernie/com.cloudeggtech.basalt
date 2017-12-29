package com.cloudeggtech.basalt.protocol.oxm.parsing;

import com.cloudeggtech.basalt.protocol.core.Protocol;

public interface IParser<T> {
	T createObject();
	IElementParser<T> getElementParser(IParsingPath parsingPath);
	void processEmbeddedObject(IParsingContext<T> context, Protocol protocol, Object embedded);
}
