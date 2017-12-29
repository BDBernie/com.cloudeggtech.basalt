package com.cloudeggtech.basalt.protocol.oxm.convention.validation;

import java.lang.annotation.Annotation;

import com.cloudeggtech.basalt.protocol.oxm.validation.IValidator;

public interface IValidatorFactory<T extends Annotation> {
	IValidator<?> create(T annotation);
}
