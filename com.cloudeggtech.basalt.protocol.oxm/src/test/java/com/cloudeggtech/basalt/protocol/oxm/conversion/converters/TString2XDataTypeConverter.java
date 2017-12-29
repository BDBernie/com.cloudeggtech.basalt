package com.cloudeggtech.basalt.protocol.oxm.conversion.converters;

import com.cloudeggtech.basalt.protocol.core.ProtocolException;
import com.cloudeggtech.basalt.protocol.core.stanza.error.BadRequest;
import com.cloudeggtech.basalt.protocol.oxm.conversion.ConversionException;
import com.cloudeggtech.basalt.protocol.oxm.conversion.IConverter;
import com.cloudeggtech.basalt.protocol.oxm.xep.xdata.TXData;
import com.cloudeggtech.basalt.protocol.oxm.xep.xdata.TXData.Type;

public class TString2XDataTypeConverter implements IConverter<String, TXData.Type> {

	@Override
	public Type from(String obj) throws ConversionException {
		try {
			return TXData.Type.valueOf(obj.toUpperCase());
		} catch (Exception e) {
			throw new ProtocolException(new BadRequest(), e);
		}
	}

	@Override
	public String to(Type obj) throws ConversionException {
		return obj.toString();
	}

}
