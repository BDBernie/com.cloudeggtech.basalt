package com.cloudeggtech.basalt.protocol.oxm.validation;

public interface IValidator<T> {
	void validate(T object) throws ValidationException;
}
