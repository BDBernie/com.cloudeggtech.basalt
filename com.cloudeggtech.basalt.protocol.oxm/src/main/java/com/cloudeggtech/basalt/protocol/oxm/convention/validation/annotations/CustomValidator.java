package com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.Arg;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.Validator;
import com.cloudeggtech.basalt.protocol.oxm.validation.IValidator;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Validator
public @interface CustomValidator {
	Class<? extends IValidator<?>> value();
	Arg[] args() default {};
}
