package com.cloudeggtech.basalt.protocol.oxm.convention.validation;

import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.CustomValidator;
import com.cloudeggtech.basalt.protocol.oxm.validation.IValidator;

public class ValidatorFactory implements IValidatorFactory<CustomValidator> {

	@Override
	public IValidator<?> create(CustomValidator annotation) {
		Class<?> type = annotation.value();
		
		if (!IValidator.class.isAssignableFrom(type)) {
			throw new IllegalArgumentException(String.format("%s should implmenet interface %s",
					type.getName(), IValidator.class.getName()));
		}
		
		try {
			return (IValidator<?>)type.newInstance();
		} catch (Exception e) {
			throw new RuntimeException(String.format("can't create validator. type %s", type.getName()));
		}
	}

}
