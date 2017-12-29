package com.cloudeggtech.basalt.protocol.oxm.translators.core.stream.sasl;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.stream.sasl.Failure;
import com.cloudeggtech.basalt.protocol.core.stream.sasl.Failure.ErrorCondition;
import com.cloudeggtech.basalt.protocol.oxm.translating.IProtocolWriter;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatingFactory;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslator;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatorFactory;

public class FailureTranslatorFactory implements ITranslatorFactory<Failure> {
	private static final ITranslator<Failure> translator = new FailureTranslator();

	@Override
	public Class<Failure> getType() {
		return Failure.class;
	}

	@Override
	public ITranslator<Failure> create() {
		return translator;
	}
	
	private static class FailureTranslator implements ITranslator<Failure> {

		@Override
		public Protocol getProtocol() {
			return Failure.PROTOCOL;
		}

		@Override
		public String translate(Failure failure, IProtocolWriter writer, ITranslatingFactory translatingFactory) {
			writer.writeProtocolBegin(Failure.PROTOCOL);
			
			if (failure.getErrorCondition() != null)
				writer.writeEmptyElement(getErrorConditionString(failure.getErrorCondition()));
			
			writer.writeProtocolEnd();
			
			return writer.getDocument();
		}

		private String getErrorConditionString(ErrorCondition errorCondition) {
			return errorCondition.toString().toLowerCase().replaceAll("_", "-");
		}
		
	}

}
