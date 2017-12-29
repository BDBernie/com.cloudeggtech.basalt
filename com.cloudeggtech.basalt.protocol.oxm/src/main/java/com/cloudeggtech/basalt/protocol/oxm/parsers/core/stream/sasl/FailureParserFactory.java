package com.cloudeggtech.basalt.protocol.oxm.parsers.core.stream.sasl;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.ProtocolException;
import com.cloudeggtech.basalt.protocol.core.stream.error.BadFormat;
import com.cloudeggtech.basalt.protocol.core.stream.sasl.Failure;
import com.cloudeggtech.basalt.protocol.oxm.Value;
import com.cloudeggtech.basalt.protocol.oxm.parsing.ElementParserAdaptor;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IElementParser;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParser;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParserFactory;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParsingContext;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParsingPath;
import com.cloudeggtech.basalt.protocol.oxm.parsing.ParserAdaptor;

public class FailureParserFactory implements IParserFactory<Failure> {
	private static final IParser<Failure> parser = new FailureParser();
	
	@Override
	public Protocol getProtocol() {
		return Failure.PROTOCOL;
	}
	
	@Override
	public IParser<Failure> create() {
		return parser;
	}
	
	private static class FailureParser extends ParserAdaptor<Failure> {
		
		public FailureParser() {
			super(null);
		}
		
		@Override
		public Failure createObject() {
			return new Failure();
		}
		
		@Override
		public IElementParser<Failure> getElementParser(IParsingPath parsingPath) {
			if (parsingPath.match("/"))
				return new ElementParserAdaptor<Failure>() {
				@Override
				public void processText(IParsingContext<Failure> context, Value<?> text) {
					if (context.getObject().getErrorCondition() == null)
						throw new ProtocolException(new BadFormat(String.format("Null error condition[%s].", context.getProtocolChain())));
				}
			};
			
			String elementName = parsingPath.toString().substring(1);
			
			if (elementName.indexOf('/') != -1) {
				throw new ProtocolException(new BadFormat(String.format("Illegal parsing path %s[PROTOCOL: %s].",
						parsingPath, Failure.PROTOCOL)));
			}
			
			
			final Failure.ErrorCondition errorCondition = getErrorConditionEnum(elementName);
			if (errorCondition != null) {
				return new ElementParserAdaptor<Failure>() {
					@Override
					public void processText(IParsingContext<Failure> context, Value<?> text) {
						super.processText(context, text);
						
						if (context.getObject().getErrorCondition() != null) {
							throw new ProtocolException(new BadFormat("Only one error condition allowed[PROTOCOL: %s]."));
						}
						
						context.getObject().setErrorCondition(errorCondition);
					}
				};
			} else {
				throw new ProtocolException(new BadFormat(String.format("Invalid failure error condition %s[PROTOCOL: %s].",
						elementName, Failure.PROTOCOL)));
			}
			
		}

		private Failure.ErrorCondition getErrorConditionEnum(String elementName) {
			for (Failure.ErrorCondition errorCondition : Failure.ErrorCondition.values()) {
				if (errorCondition.toString().toLowerCase().replaceAll("_", "-").equals(elementName))
					return errorCondition;
			}
			
			return null;
		}
		
	}
	
}
