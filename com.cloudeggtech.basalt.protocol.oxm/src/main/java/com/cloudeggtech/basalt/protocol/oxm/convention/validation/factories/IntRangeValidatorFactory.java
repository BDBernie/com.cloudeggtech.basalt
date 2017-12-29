package com.cloudeggtech.basalt.protocol.oxm.convention.validation.factories;

import com.cloudeggtech.basalt.protocol.oxm.convention.validation.IValidatorFactory;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.IntRange;
import com.cloudeggtech.basalt.protocol.oxm.validation.IValidator;
import com.cloudeggtech.basalt.protocol.oxm.validation.validators.IntRangeValidator;

public class IntRangeValidatorFactory implements IValidatorFactory<IntRange> {

	@Override
	public IValidator<Integer> create(IntRange intRange) {
		return new IntRangeValidator(intRange.min(), intRange.max());
	}

}
