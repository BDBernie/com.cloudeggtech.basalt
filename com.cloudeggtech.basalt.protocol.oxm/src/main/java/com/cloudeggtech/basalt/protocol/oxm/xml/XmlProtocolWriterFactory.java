package com.cloudeggtech.basalt.protocol.oxm.xml;

import com.cloudeggtech.basalt.protocol.oxm.IProtocolWriterFactory;
import com.cloudeggtech.basalt.protocol.oxm.translating.IProtocolWriter;

public class XmlProtocolWriterFactory implements IProtocolWriterFactory {

	@Override
	public IProtocolWriter create() {
		return new XmlProtocolWriter();
	}

}
