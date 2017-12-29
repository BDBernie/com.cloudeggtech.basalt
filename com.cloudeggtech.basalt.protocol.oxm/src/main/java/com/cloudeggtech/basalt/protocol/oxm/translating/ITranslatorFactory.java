package com.cloudeggtech.basalt.protocol.oxm.translating;

public interface ITranslatorFactory<T> {
	Class<T> getType();
	ITranslator<T> create();
}
