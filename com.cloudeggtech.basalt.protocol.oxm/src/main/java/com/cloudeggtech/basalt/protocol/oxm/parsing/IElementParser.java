package com.cloudeggtech.basalt.protocol.oxm.parsing;

import java.util.List;

import com.cloudeggtech.basalt.protocol.oxm.Attribute;
import com.cloudeggtech.basalt.protocol.oxm.Value;

public interface IElementParser<T> {
	void processText(IParsingContext<T> context, Value<?> text);
	void processAttributes(IParsingContext<T> context, List<Attribute> attributes);
}
