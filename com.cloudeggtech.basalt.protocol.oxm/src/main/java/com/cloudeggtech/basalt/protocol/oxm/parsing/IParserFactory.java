package com.cloudeggtech.basalt.protocol.oxm.parsing;

import com.cloudeggtech.basalt.protocol.core.Protocol;

public interface IParserFactory<T> {
	Protocol getProtocol();
	IParser<T> create();
}
