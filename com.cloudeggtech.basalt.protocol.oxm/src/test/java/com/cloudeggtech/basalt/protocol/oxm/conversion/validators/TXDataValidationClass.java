package com.cloudeggtech.basalt.protocol.oxm.conversion.validators;

import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.Validate;
import com.cloudeggtech.basalt.protocol.oxm.validation.ValidationException;
import com.cloudeggtech.basalt.protocol.oxm.xep.xdata.TField;

public class TXDataValidationClass {
	
	@Validate({"/fields/[field]", "/reported/fields/[field]", "/item/fields/[field]"})
	public void validateField(TField field) throws ValidationException {
		if (field.getOptions() != null && field.getOptions().size() > 0) {
			if (field.getType() != TField.Type.LIST_MULTI && field.getType() != TField.Type.LIST_SINGLE)
				throw new ValidationException(String.format("options is only allowed in list_multi or list_single type field," +
						" but this is a %s type field", field.getType()));
		}
	}

}
