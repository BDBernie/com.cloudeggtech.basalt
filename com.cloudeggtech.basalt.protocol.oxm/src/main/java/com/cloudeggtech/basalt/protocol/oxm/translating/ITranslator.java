package com.cloudeggtech.basalt.protocol.oxm.translating;

import com.cloudeggtech.basalt.protocol.core.Protocol;

public interface ITranslator<T> {
	Protocol getProtocol();
	String translate(T object, IProtocolWriter writer, ITranslatingFactory translatingFactory);
}
