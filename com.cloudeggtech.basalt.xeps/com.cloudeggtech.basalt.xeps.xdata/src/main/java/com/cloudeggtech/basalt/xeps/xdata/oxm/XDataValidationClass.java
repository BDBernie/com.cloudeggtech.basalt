package com.cloudeggtech.basalt.xeps.xdata.oxm;

import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.Validate;
import com.cloudeggtech.basalt.protocol.oxm.validation.ValidationException;
import com.cloudeggtech.basalt.xeps.xdata.Field;

public class XDataValidationClass {
	
	@Validate({"/fields/[field]", "/reported/fields/[field]", "/item/fields/[field]"})
	public void validateField(Field field) throws ValidationException {
		if (field.getOptions() != null && field.getOptions().size() > 0) {
			if (field.getType() != Field.Type.LIST_MULTI && field.getType() != Field.Type.LIST_SINGLE)
				throw new ValidationException(String.format("options is only allowed in list_multi or list_single type field," +
						" but this is a %s type field", field.getType()));
		}
	}

}
