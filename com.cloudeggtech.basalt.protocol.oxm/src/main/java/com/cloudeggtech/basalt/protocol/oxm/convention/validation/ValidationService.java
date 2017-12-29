package com.cloudeggtech.basalt.protocol.oxm.convention.validation;

import java.lang.annotation.Annotation;

import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.CustomValidator;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.Email;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.IntRange;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.NotNull;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.factories.CustomValidatorFactory;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.factories.EmailValidatorFactory;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.factories.IntRangeValidatorFactory;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.factories.NotNullValidatorFactory;
import com.cloudeggtech.basalt.protocol.oxm.validation.IValidator;
import com.cloudeggtech.basalt.protocol.oxm.validation.ValidationException;

public class ValidationService {
	private static IValidationFactory validationFactory = new ValidationFactory();
	
	static {
		validationFactory.register(CustomValidator.class, new CustomValidatorFactory());
		validationFactory.register(NotNull.class, new NotNullValidatorFactory());
		validationFactory.register(IntRange.class, new IntRangeValidatorFactory());
		validationFactory.register(Email.class, new EmailValidatorFactory());
	}
	
	public static <T extends Annotation> void register(Class<T> type, IValidatorFactory<T> factory) {
		validationFactory.register(type, factory);
	}
	
	public static void unregister(Class<? extends Annotation> type) {
		validationFactory.unregister(type);
	}
	
	public static <T> void validate(Annotation annotation, T value) throws ValidationException {
		@SuppressWarnings("unchecked")
		IValidator<T> validator = (IValidator<T>)validationFactory.getValidator(annotation);
		
		if (validator == null) {
			throw new ValidationException(String.format("Validator factory of annotation class %s not be found.",
					annotation.annotationType().getName()));
		}
		
		validator.validate(value);
	}
}
