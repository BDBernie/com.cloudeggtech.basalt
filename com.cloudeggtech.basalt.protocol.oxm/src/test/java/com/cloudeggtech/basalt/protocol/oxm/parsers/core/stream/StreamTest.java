package com.cloudeggtech.basalt.protocol.oxm.parsers.core.stream;

import org.junit.Test;

import com.cloudeggtech.basalt.protocol.core.ProtocolChain;
import com.cloudeggtech.basalt.protocol.core.stream.Stream;
import com.cloudeggtech.basalt.protocol.oxm.IOxmFactory;
import com.cloudeggtech.basalt.protocol.oxm.OxmService;
import com.cloudeggtech.basalt.protocol.oxm.TestData;
import com.cloudeggtech.basalt.protocol.oxm.annotation.AnnotatedParserFactory;

import junit.framework.Assert;

public class StreamTest {
	
	@Test
	public void testStream() {
		IOxmFactory oxmFactory = OxmService.createStandardOxmFactory();
		
		oxmFactory.register(
				ProtocolChain.
					first(Stream.PROTOCOL),
				new AnnotatedParserFactory<>(StreamParser.class)
			);
		
		String openStreamMessage = TestData.getData(this.getClass(), "openStreamMessage");
		
		Stream openStream = (Stream)oxmFactory.parse(openStreamMessage);
		
		Assert.assertEquals("chat.cloudegg-tech.com", openStream.getTo().toString());
		Assert.assertEquals("en", openStream.getLang());
		Assert.assertEquals("1.0", openStream.getVersion());
		Assert.assertEquals(false, openStream.getClose());
		
		Assert.assertEquals("jabber:client", openStream.getDefaultNamespace());
		
		String closeStreamMessage = TestData.getData(this.getClass(), "closeStreamMessage");
		
		Stream closeStream = (Stream)oxmFactory.parse(closeStreamMessage);
		
		Assert.assertEquals(true, closeStream.getClose());
	}
}
