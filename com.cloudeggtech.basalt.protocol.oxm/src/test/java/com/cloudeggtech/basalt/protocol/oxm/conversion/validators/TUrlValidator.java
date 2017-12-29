package com.cloudeggtech.basalt.protocol.oxm.conversion.validators;

import com.cloudeggtech.basalt.protocol.oxm.validation.IValidator;
import com.cloudeggtech.basalt.protocol.oxm.validation.ValidationException;

public class TUrlValidator implements IValidator<String> {

	@Override
	public void validate(String url) throws ValidationException {
		org.apache.commons.validator.UrlValidator validator = new org.apache.commons.validator.UrlValidator();
		if (!validator.isValid(url)) {
			throw new ValidationException(String.format("%s isn't a valid url", url));
		}
	}

}
