package com.cloudeggtech.basalt.protocol.oxm.convention.validation.factories;

import com.cloudeggtech.basalt.protocol.oxm.convention.validation.IValidatorFactory;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.Email;
import com.cloudeggtech.basalt.protocol.oxm.validation.IValidator;
import com.cloudeggtech.basalt.protocol.oxm.validation.validators.EmailValidator;

public class EmailValidatorFactory implements IValidatorFactory<Email> {
	private static final IValidator<String> validator = new EmailValidator();

	@Override
	public IValidator<String> create(Email annotation) {
		return validator;
	}

}
