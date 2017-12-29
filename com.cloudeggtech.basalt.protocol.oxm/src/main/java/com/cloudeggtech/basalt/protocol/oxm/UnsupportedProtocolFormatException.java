package com.cloudeggtech.basalt.protocol.oxm;

public class UnsupportedProtocolFormatException extends RuntimeException {

	private static final long serialVersionUID = 4157602245337368277L;

	public UnsupportedProtocolFormatException() {
		super();
	}

	public UnsupportedProtocolFormatException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public UnsupportedProtocolFormatException(String arg0) {
		super(arg0);
	}

	public UnsupportedProtocolFormatException(Throwable arg0) {
		super(arg0);
	}

}
