package com.cloudeggtech.basalt.protocol.oxm.translators.core.stream;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.stream.Feature;
import com.cloudeggtech.basalt.protocol.core.stream.Features;
import com.cloudeggtech.basalt.protocol.oxm.parsing.FlawedProtocolObject;
import com.cloudeggtech.basalt.protocol.oxm.translating.IProtocolWriter;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatingFactory;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslator;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatorFactory;

public class FeaturesTranslatorFactory implements ITranslatorFactory<Features> {
	private static final ITranslator<Features> translator = new FeaturesTranslator();

	@Override
	public Class<Features> getType() {
		return Features.class;
	}

	@Override
	public ITranslator<Features> create() {
		return translator;
	}
	
	private static class FeaturesTranslator implements ITranslator<Features> {

		@Override
		public Protocol getProtocol() {
			return Features.PROTOCOL;
		}

		@Override
		public String translate(Features features, IProtocolWriter writer, ITranslatingFactory translatingFactory) {
			writer.writeProtocolBegin(Features.PROTOCOL);
			
			for (Feature feature : features.getFeatures()) {
				if (feature instanceof FlawedProtocolObject)
					continue;
				
				writer.writeString(translatingFactory.translate(feature));
			}
			
			writer.writeProtocolEnd();
			
			return writer.getDocument();
		}
		
	}

}
