package com.cloudeggtech.basalt.xeps.ibr.oxm;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.oxm.translating.IProtocolWriter;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatingFactory;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslator;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatorFactory;
import com.cloudeggtech.basalt.xeps.ibr.Register;

public class RegisterTranslatorFactory implements ITranslatorFactory<Register> {
	private static final ITranslator<Register> translator = new RegisterTranslator();

	@Override
	public Class<Register> getType() {
		return Register.class;
	}

	@Override
	public ITranslator<Register> create() {
		return translator;
	}
	
	private static class RegisterTranslator implements ITranslator<Register> {
		

		@Override
		public Protocol getProtocol() {
			return Register.PROTOCOL;
		}

		@Override
		public String translate(Register register, IProtocolWriter writer, ITranslatingFactory translatingFactory) {
			writer.writeProtocolBegin(Register.PROTOCOL);
			writer.writeProtocolEnd();
			
			return writer.getDocument();
		}
		
	}
}
