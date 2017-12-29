package com.cloudeggtech.basalt.protocol.oxm;

import com.cloudeggtech.basalt.protocol.oxm.translating.IProtocolWriter;

public interface IProtocolWriterFactory {
	IProtocolWriter create();
}
