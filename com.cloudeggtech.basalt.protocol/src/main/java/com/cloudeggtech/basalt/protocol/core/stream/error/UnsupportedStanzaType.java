package com.cloudeggtech.basalt.protocol.core.stream.error;

import com.cloudeggtech.basalt.protocol.core.LangText;

public class UnsupportedStanzaType extends StreamError {
	public static final String DEFINED_CONDITION = "unspported-stanza-type";
	
	public UnsupportedStanzaType() {
		this(null);
	}
	
	public UnsupportedStanzaType(String text) {
		super(DEFINED_CONDITION);
		if (text != null) {
			setText(new LangText(text));
		}
	}
}
