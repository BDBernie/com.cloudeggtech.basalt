package com.cloudeggtech.basalt.protocol.core.stanza.error;

import com.cloudeggtech.basalt.protocol.core.LangText;

public class UnexpectedRequest extends StanzaError {
	public static final String DEFINED_CONDITION = "unexpected-request";
	
	public UnexpectedRequest() {
		this(null);
	}
	
	public UnexpectedRequest(String text) {
		this(text, null);
	}
	
	public UnexpectedRequest(String text, String lang) {
		super(StanzaError.Type.WAIT, DEFINED_CONDITION, text == null ? null : new LangText(text, lang));
	}
}
