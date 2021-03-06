package com.cloudeggtech.basalt.protocol.core.stanza.error;

import com.cloudeggtech.basalt.protocol.core.LangText;

public class NotAcceptable extends StanzaError {
	public static final String DEFINED_CONDITION = "not-acceptable";
	
	public NotAcceptable() {
		this(null);
	}
	
	public NotAcceptable(String text) {
		this(text, null);
	}
	
	public NotAcceptable(String text, String lang) {
		super(StanzaError.Type.MODIFY, DEFINED_CONDITION, text == null ? null : new LangText(text, lang));
	}
}
