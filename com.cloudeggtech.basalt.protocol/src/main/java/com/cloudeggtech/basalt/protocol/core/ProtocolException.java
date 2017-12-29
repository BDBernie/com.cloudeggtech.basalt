package com.cloudeggtech.basalt.protocol.core;


public class ProtocolException extends RuntimeException {
	private static final long serialVersionUID = 1828997382595546258L;

	private IError error;
	
	public ProtocolException(IError error) {
		this(error, null);
	}
	
	public ProtocolException(IError error, Exception e) {
		super(e);
		this.error = error;
		if (error.getText() == null && e != null && e.getMessage() != null) {
			error.setText(new LangText(e.getMessage()));
		}
	}
	
	public IError getError() {
		return error;
	}
	
	@Override
	public String getMessage() {
		return String.format("ProtocolException['%s', '%s', '%s']", error.getDefinedCondition(),
			(error.getText() == null ? null : error.getText().getText()),
					error.getApplicationSpecificCondition());
	}
	
	@Override
	public String toString() {
		return getMessage();
	}
}
