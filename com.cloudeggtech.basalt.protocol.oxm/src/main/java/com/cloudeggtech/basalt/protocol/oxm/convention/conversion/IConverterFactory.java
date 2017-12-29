package com.cloudeggtech.basalt.protocol.oxm.convention.conversion;

import java.lang.annotation.Annotation;

import com.cloudeggtech.basalt.protocol.oxm.conversion.IConverter;

public interface IConverterFactory {
	IConverter<?, ?> create(Annotation annotation);
}
