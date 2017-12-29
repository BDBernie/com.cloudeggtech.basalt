package com.cloudeggtech.basalt.protocol.oxm.parsing;

import com.cloudeggtech.basalt.protocol.core.ProtocolChain;

public interface IParsingContext<T> {
	ProtocolChain getProtocolChain();
	IParsingPath getParsingPath();
	T getObject();
	void setAttribute(Object key, Object value);
	<K> K getAttribute(Object key);
	<K> K removeAttribute(Object key);
	void setMessage(String message);
	String getMessage();
}
