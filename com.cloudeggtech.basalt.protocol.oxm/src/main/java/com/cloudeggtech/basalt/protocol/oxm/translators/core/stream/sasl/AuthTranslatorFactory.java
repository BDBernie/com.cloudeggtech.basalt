package com.cloudeggtech.basalt.protocol.oxm.translators.core.stream.sasl;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.stream.sasl.Auth;
import com.cloudeggtech.basalt.protocol.oxm.Attributes;
import com.cloudeggtech.basalt.protocol.oxm.translating.IProtocolWriter;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatingFactory;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslator;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatorFactory;

public class AuthTranslatorFactory implements ITranslatorFactory<Auth> {
	private static final ITranslator<Auth> translator = new AuthTranslator();

	@Override
	public Class<Auth> getType() {
		return Auth.class;
	}

	@Override
	public ITranslator<Auth> create() {
		return translator;
	}
	
	private static class AuthTranslator implements ITranslator<Auth> {

		@Override
		public Protocol getProtocol() {
			return Auth.PROTOCOL;
		}

		@Override
		public String translate(Auth auth, IProtocolWriter writer, ITranslatingFactory translatingFactory) {
			writer.writeProtocolBegin(Auth.PROTOCOL);
			
			writer.writeAttributes(new Attributes().
					add(Auth.ATTRIBUTE_NAME_MECHANISM, auth.getMechanism()).
					get()
				);
			
			writer.writeProtocolEnd();
			
			return writer.getDocument();
		}
		
	}

}
