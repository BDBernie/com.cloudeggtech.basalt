package com.cloudeggtech.basalt.protocol.oxm.preprocessing;

import com.cloudeggtech.basalt.protocol.core.ProtocolException;

public interface IProtocolPreprocessor {
	String[] parse(char[] bytes, int readBytes) throws OutOfMaxBufferSizeException, ProtocolException;
	String[] getDocuments();
	void setMaxBufferSize(int maxBufferSize);
	int getMaxBufferSize();
	char[] getBuffer();
	void resetBuffer();
	void clear();
}