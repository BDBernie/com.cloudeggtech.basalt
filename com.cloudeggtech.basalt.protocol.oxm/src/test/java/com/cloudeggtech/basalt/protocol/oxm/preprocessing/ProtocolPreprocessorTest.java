package com.cloudeggtech.basalt.protocol.oxm.preprocessing;

import org.junit.Before;
import org.junit.Test;

import com.cloudeggtech.basalt.protocol.oxm.TestData;
import com.cloudeggtech.basalt.protocol.oxm.xml.preprocessing.XmlProtocolPreprocessor;

import junit.framework.Assert;

public class ProtocolPreprocessorTest {
	private IProtocolPreprocessor preprocessor;
	
	private String complexMessage = TestData.getData(this.getClass(), "complexMessage");
	private String simpleMessage = TestData.getData(this.getClass(), "simpleMessage");
	private String openStreamMessage = TestData.getData(this.getClass(), "openStreamMessage");
	private String closeStreamMessage = TestData.getData(this.getClass(), "closeStreamMessage");
	private String uncompletedMessagePart1 = TestData.getData(this.getClass(), "uncompletedMessagePart1");
	private String uncompletedMessagePart2 = TestData.getData(this.getClass(), "uncompletedMessagePart2");
	private String anotherSimpleMessage = TestData.getData(this.getClass(), "anotherSimpleMessage");
	private String firstCharBrokenMessagePart1 = TestData.getData(this.getClass(), "firstCharBrokenMessagePart1");
	private String firstCharBrokenMessagePart2 = TestData.getData(this.getClass(), "firstCharBrokenMessagePart2");
	private String invalidMessage = TestData.getData(this.getClass(), "invalidMessage");
	private String oneCharMoreMessage = TestData.getData(this.getClass(), "oneCharMoreMessage");
	private String oneCharLessMessage = TestData.getData(this.getClass(), "oneCharLessMessage");
	private String oneCharTextElementMessage = TestData.getData(this.getClass(), "oneCharTextElementMessage");
	private String uncompletedMessagePart3 = TestData.getData(this.getClass(), "uncompletedMessagePart3");
	private String uncompletedMessagePart4 = TestData.getData(this.getClass(), "uncompletedMessagePart4");
	
	@Before
	public void before() {
		preprocessor = new XmlProtocolPreprocessor(1024 * 1024);
	}
	
	@Test
	public void parse() throws Exception {
		String message = complexMessage + simpleMessage;
		
		char[] bytes = message.toCharArray();
		String[] result = preprocessor.parse(bytes, bytes.length);
		
		Assert.assertEquals(2, result.length);
		Assert.assertEquals(complexMessage.trim(), result[0]);
		Assert.assertEquals(simpleMessage.trim(), result[1]);
		Assert.assertEquals(0, preprocessor.getBuffer().length);
		
		message = complexMessage + openStreamMessage + simpleMessage +
				closeStreamMessage + uncompletedMessagePart1;
		
		bytes = message.toCharArray();
		result = preprocessor.parse(bytes, bytes.length);

		Assert.assertEquals(4, result.length);
		Assert.assertEquals(complexMessage.trim(), result[0]);
		Assert.assertEquals(openStreamMessage.trim().substring(22, openStreamMessage.length()), result[1]);
		Assert.assertEquals(simpleMessage.trim(), result[2]);
		Assert.assertEquals(closeStreamMessage.trim(), result[3]);
		Assert.assertEquals(uncompletedMessagePart1, new String(preprocessor.getBuffer()));
		
		message = uncompletedMessagePart2 + anotherSimpleMessage;
		
		bytes = message.toCharArray();
		result = preprocessor.parse(bytes, bytes.length);
		
		Assert.assertEquals(2, result.length);
		Assert.assertEquals((uncompletedMessagePart1 + uncompletedMessagePart2).trim(), result[0]);
		Assert.assertEquals(anotherSimpleMessage.trim(), result[1]);
		Assert.assertEquals(0, preprocessor.getBuffer().length);
		
		bytes = firstCharBrokenMessagePart1.toCharArray();
		result = preprocessor.parse(bytes, bytes.length);
		
		Assert.assertEquals(0, result.length);
		
		bytes = firstCharBrokenMessagePart2.toCharArray();
		result = preprocessor.parse(bytes, bytes.length);
		
		Assert.assertEquals(1, result.length);
		Assert.assertEquals(firstCharBrokenMessagePart1+ firstCharBrokenMessagePart2, result[0]);
		
		message = invalidMessage + anotherSimpleMessage;
		
		bytes = message.toCharArray();
		try {
			result = preprocessor.parse(bytes, bytes.length);
			Assert.fail();
		} catch (Exception e) {
			// should run to here
			preprocessor.clear();
		}
		
		message = oneCharMoreMessage;
		bytes = message.toCharArray();
		
		result = preprocessor.parse(bytes, bytes.length);
		Assert.assertEquals(1, result.length);
		Assert.assertEquals(1, preprocessor.getBuffer().length);
		
		message = oneCharLessMessage;
		bytes = message.toCharArray();
		
		result = preprocessor.parse(bytes, bytes.length);
		Assert.assertEquals(1, result.length);
		Assert.assertEquals(0, preprocessor.getBuffer().length);
		
		message = oneCharTextElementMessage;
		bytes = message.toCharArray();
		
		result = preprocessor.parse(bytes, bytes.length);
		Assert.assertEquals(1, result.length);
		Assert.assertEquals(0, preprocessor.getBuffer().length);
		
		message = uncompletedMessagePart3;
		bytes = message.toCharArray();
		
		result = preprocessor.parse(bytes, bytes.length);
		Assert.assertEquals(0, result.length);
		Assert.assertEquals(message.length(), preprocessor.getBuffer().length);
		
		message = uncompletedMessagePart4;
		bytes = message.toCharArray();
		
		result = preprocessor.parse(bytes, bytes.length);
		Assert.assertEquals(1, result.length);
		Assert.assertEquals(uncompletedMessagePart3 + uncompletedMessagePart4, result[0]);
		Assert.assertEquals(0, preprocessor.getBuffer().length);
	}
}
