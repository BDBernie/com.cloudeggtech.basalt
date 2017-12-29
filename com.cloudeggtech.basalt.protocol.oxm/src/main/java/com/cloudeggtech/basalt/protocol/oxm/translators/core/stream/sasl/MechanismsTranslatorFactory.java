package com.cloudeggtech.basalt.protocol.oxm.translators.core.stream.sasl;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.stream.sasl.Mechanisms;
import com.cloudeggtech.basalt.protocol.oxm.Value;
import com.cloudeggtech.basalt.protocol.oxm.translating.IProtocolWriter;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatingFactory;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslator;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatorFactory;

public class MechanismsTranslatorFactory implements ITranslatorFactory<Mechanisms> {
	private static final ITranslator<Mechanisms> translator = new MechanismsTranslator();

	@Override
	public Class<Mechanisms> getType() {
		return Mechanisms.class;
	}

	@Override
	public ITranslator<Mechanisms> create() {
		return translator;
	}
	
	private static class MechanismsTranslator implements ITranslator<Mechanisms> {

		@Override
		public Protocol getProtocol() {
			return Mechanisms.PROTOCOL;
		}

		@Override
		public String translate(Mechanisms mechanisms, IProtocolWriter writer, ITranslatingFactory translatingFactory) {
			writer.writeProtocolBegin(Mechanisms.PROTOCOL);
			
			writer.writeArrayBegin("mechanisms");
			
			for (String mechanism : mechanisms.getMechanisms()) {
				writer.writeTextOnly("mechanism", Value.create(mechanism));
			}
			
			writer.writeArrayEnd();
			
			writer.writeProtocolEnd();
			
			return writer.getDocument();
		}
		
	}

}
