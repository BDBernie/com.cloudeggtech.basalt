package com.cloudeggtech.basalt.protocol.oxm;

import com.cloudeggtech.basalt.protocol.oxm.parsing.IParsingFactory;
import com.cloudeggtech.basalt.protocol.oxm.preprocessing.IProtocolPreprocessor;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatingFactory;
import com.cloudeggtech.basalt.protocol.oxm.xml.XmlParsingFactory;
import com.cloudeggtech.basalt.protocol.oxm.xml.XmlProtocolWriterFactory;
import com.cloudeggtech.basalt.protocol.oxm.xml.preprocessing.XmlProtocolPreprocessor;

public class OxmService {
	public static IOxmFactory createStreamOxmFactory() {
		return new StreamOxmFactory(createParsingFactory(), createTranslatingFactory());
	}
	
	public static IOxmFactory createMinimumOxmFactory() {
		return new MinimumOxmFactory(createParsingFactory(), createTranslatingFactory());
	}
	
	public static IOxmFactory createStandardOxmFactory() {
		return new StandardOxmFactory(createParsingFactory(), createTranslatingFactory());
	}
	
	public static ITranslatingFactory createTranslatingFactory () {
		return new TranslatingFactory(createProtocolWriterFactory());
	}
	
	public static IProtocolWriterFactory createProtocolWriterFactory() {
		return new XmlProtocolWriterFactory();
	}
	
	public static IParsingFactory createParsingFactory() {
		return new XmlParsingFactory();
	}
	
	public static IProtocolPreprocessor createProtocolPreprocessor() {
		return new XmlProtocolPreprocessor();
	}
}
