package com.cloudeggtech.basalt.protocol.oxm.conversion.converters;

import com.cloudeggtech.basalt.protocol.core.JabberId;
import com.cloudeggtech.basalt.protocol.core.MalformedJidException;
import com.cloudeggtech.basalt.protocol.oxm.conversion.ConversionException;
import com.cloudeggtech.basalt.protocol.oxm.conversion.IConverter;

public class String2JabberIdConverter implements IConverter<String, JabberId> {

	@Override
	public JabberId from(String jidString) throws ConversionException {
		try {
			return JabberId.parse(jidString);
		} catch (MalformedJidException e) {
			throw new ConversionException("Not a valid jid.", e);
		}
	}

	@Override
	public String to(JabberId jid) throws ConversionException {
		return jid.toString();
	}

}
