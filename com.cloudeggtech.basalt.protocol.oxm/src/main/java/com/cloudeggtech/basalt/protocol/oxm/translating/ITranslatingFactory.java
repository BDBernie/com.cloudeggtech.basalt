package com.cloudeggtech.basalt.protocol.oxm.translating;

import com.cloudeggtech.basalt.protocol.oxm.IProtocolWriterFactory;

public interface ITranslatingFactory {
	String translate(Object object);
	
	void register(Class<?> type, ITranslatorFactory<?> translatorFactory);
	void unregister(Class<?> type);
	
	IProtocolWriterFactory getWriterFactory();
}
