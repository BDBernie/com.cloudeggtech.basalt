package com.cloudeggtech.basalt.protocol.oxm.parsers.xep;

import org.junit.Before;
import org.junit.Test;

import com.cloudeggtech.basalt.protocol.HandyUtils;
import com.cloudeggtech.basalt.protocol.core.ProtocolChain;
import com.cloudeggtech.basalt.protocol.core.ProtocolException;
import com.cloudeggtech.basalt.protocol.core.stanza.Iq;
import com.cloudeggtech.basalt.protocol.oxm.IOxmFactory;
import com.cloudeggtech.basalt.protocol.oxm.OxmService;
import com.cloudeggtech.basalt.protocol.oxm.TestData;
import com.cloudeggtech.basalt.protocol.oxm.convention.NamingConventionParserFactory;
import com.cloudeggtech.basalt.protocol.oxm.xep.ibr.TIqRegister;
import com.cloudeggtech.basalt.protocol.oxm.xep.ibr.TRegistrationField;
import com.cloudeggtech.basalt.protocol.oxm.xep.ibr.TRegistrationForm;
import com.cloudeggtech.basalt.protocol.oxm.xep.ibr.TRemove;
import com.cloudeggtech.basalt.protocol.oxm.xep.oob.TXOob;
import com.cloudeggtech.basalt.protocol.oxm.xep.xdata.TField;
import com.cloudeggtech.basalt.protocol.oxm.xep.xdata.TField.Type;
import com.cloudeggtech.basalt.protocol.oxm.xep.xdata.TOption;
import com.cloudeggtech.basalt.protocol.oxm.xep.xdata.TXData;

import junit.framework.Assert;

public class IqRegisterTest {
	private IOxmFactory oxmFactory;
	
	@Before
	public void before() {
		oxmFactory = OxmService.createStandardOxmFactory();
		
		oxmFactory.register(
				ProtocolChain.
					first(Iq.PROTOCOL).
					next(TIqRegister.PROTOCOL),
				new TIqRegisterParserFactory()
			);
		
		oxmFactory.register(
				ProtocolChain.
					first(Iq.PROTOCOL).
					next(TIqRegister.PROTOCOL).
					next(TXData.PROTOCOL),
				new NamingConventionParserFactory<>(
						TXData.class)
			);
		oxmFactory.register(
				ProtocolChain.
					first(Iq.PROTOCOL).
					next(TIqRegister.PROTOCOL).
					next(TXOob.PROTOCOL),
				new NamingConventionParserFactory<>(
						TXOob.class)
			);
	}
	
	@Test
	public void parseNullRegistration() {
		String iqRegisterMessage = TestData.getData(this.getClass(), "iqRegisterMessage1");
		
		Object obj = oxmFactory.parse(iqRegisterMessage);
		Assert.assertTrue(obj instanceof Iq);
		Iq iq = (Iq)obj;
		Assert.assertEquals(Iq.Type.GET, iq.getType());
		Assert.assertEquals("shakespeare.lit", iq.getTo().toString());
		Assert.assertEquals("reg1", iq.getId());
		
		Assert.assertNotNull(iq.getObject());
		Assert.assertTrue(iq.getObject() instanceof TIqRegister);
		TIqRegister iqRegister = (TIqRegister)iq.getObject();
		Assert.assertNull(iqRegister.getRegister());
		Assert.assertNull(iqRegister.getXData());
		Assert.assertNull(iqRegister.getOob());
	}
	
	@Test
	public void parseReturnedRegistrationForm() {
		String instructions1 = TestData.getData(this.getClass(), "instructions1");
		String iqRegisterMessage = TestData.getData(this.getClass(), "iqRegisterMessage2");
		
		Object obj = oxmFactory.parse(iqRegisterMessage);
		Assert.assertTrue(obj instanceof Iq);
		Iq iq = (Iq)obj;
		Assert.assertEquals(Iq.Type.RESULT, iq.getType());
		Assert.assertEquals("reg1", iq.getId());
		
		Assert.assertNotNull(iq.getObject());
		Assert.assertTrue(iq.getObject() instanceof TIqRegister);
		TIqRegister iqRegister = (TIqRegister)iq.getObject();
		Assert.assertNotNull(iqRegister.getRegister());
		Assert.assertTrue(iqRegister.getRegister() instanceof TRegistrationForm);
		
		TRegistrationForm form = (TRegistrationForm)iqRegister.getRegister();
		
		Assert.assertEquals(4, form.getFields().size());
		Assert.assertTrue(checkRegistrationField("instructions", instructions1, form));
		Assert.assertTrue(checkRegistrationField("username", null, form));
		Assert.assertTrue(checkRegistrationField("password", null, form));
		Assert.assertTrue(checkRegistrationField("email", null, form));
	}
	
	@Test
	public void parseRegistrationForm() {
		String iqRegisterMessage = TestData.getData(this.getClass(), "iqRegisterMessage3");
		
		Object obj = oxmFactory.parse(iqRegisterMessage);
		Assert.assertTrue(obj instanceof Iq);
		Iq iq = (Iq)obj;
		Assert.assertEquals(Iq.Type.RESULT, iq.getType());
		Assert.assertEquals("reg1", iq.getId());
		
		Assert.assertNotNull(iq.getObject());
		Assert.assertTrue(iq.getObject() instanceof TIqRegister);
		TIqRegister iqRegister = (TIqRegister)iq.getObject();
		Assert.assertNotNull(iqRegister.getRegister());
		Assert.assertTrue(iqRegister.getRegister() instanceof TRegistrationForm);
		
		TRegistrationForm form = (TRegistrationForm)iqRegister.getRegister();
		
		Assert.assertEquals(3, form.getFields().size());
		Assert.assertTrue(checkRegistrationField("username", "juliet", form));
		Assert.assertTrue(checkRegistrationField("password", "R0m30", form));
		Assert.assertTrue(checkRegistrationField("email", "juliet@capulet.com", form));
		
		Assert.assertTrue(form.isRegistered());
	}
	
	@Test
	public void parseOob() {
		String iqRegisterMessage = TestData.getData(this.getClass(), "iqRegisterMessage4");
				
		Object obj = oxmFactory.parse(iqRegisterMessage);
		Assert.assertTrue(obj instanceof Iq);
		Iq iq = (Iq)obj;
		Assert.assertEquals(Iq.Type.RESULT, iq.getType());
		Assert.assertEquals("reg3",iq.getId());
		
		Assert.assertEquals("juliet@capulet.com/balcony", iq.getTo().toString());
		
		Assert.assertNotNull(iq.getObject());
		Assert.assertTrue(iq.getObject() instanceof TIqRegister);
		
		TIqRegister iqRegister = (TIqRegister)iq.getObject();
		
		Assert.assertNotNull(iqRegister.getOob());
		
		TXOob oob = iqRegister.getOob();
		
		Assert.assertEquals("http://www.shakespeare.lit/contests.php", oob.getUrl());
	}
	
	@Test
	public void parseConflict() {
		String iqRegisterMessage = TestData.getData(this.getClass(), "iqRegisterMessage5");

		try {
			oxmFactory.parse(iqRegisterMessage);
			Assert.fail();
		} catch (ProtocolException e) {
			// should run to here
		}

	}
	
	@Test
	public void parseRemove() {
		String iqRegisterMessage = TestData.getData(this.getClass(), "iqRegisterMessage6");
		
		Object obj = oxmFactory.parse(iqRegisterMessage);
		Assert.assertTrue(obj instanceof Iq);
		Iq iq = (Iq)obj;
		Assert.assertEquals(Iq.Type.RESULT, iq.getType());
		Assert.assertEquals("reg1", iq.getId());
		
		Assert.assertNotNull(iq.getObject());
		Assert.assertTrue(iq.getObject() instanceof TIqRegister);
		TIqRegister iqRegister = (TIqRegister)iq.getObject();
		Assert.assertNotNull(iqRegister.getRegister());
		Assert.assertTrue(iqRegister.getRegister() instanceof TRemove);
	}

	private boolean checkRegistrationField(String name, String value, TRegistrationForm form) {
		for (TRegistrationField field : form.getFields()) {
			if (name.equals(field.getName())) {
				if (value == null) {
					return field.getValue() == null;
				} else {
					return value.equals(field.getValue());
				}
			}
		}
		
		return false;
	}
	
	@Test
	public void parseReturnedXDataRegistrationForm() {
		String instructions2 = TestData.getData(this.getClass(), "instructions2");
		String instructions3 = TestData.getData(this.getClass(), "instructions3");
		String iqRegisterMessage =TestData.getData(this.getClass(), "iqRegisterMessage7");
		
		Object obj = oxmFactory.parse(iqRegisterMessage);
		Assert.assertTrue(obj instanceof Iq);
		Iq iq = (Iq)obj;
		Assert.assertEquals(Iq.Type.RESULT, iq.getType());
		Assert.assertEquals("reg3",iq.getId());
		
		Assert.assertEquals("juliet@capulet.com/balcony", iq.getTo().toString());
		
		Assert.assertNotNull(iq.getObject());
		Assert.assertTrue(iq.getObject() instanceof TIqRegister);
		
		TIqRegister iqRegister = (TIqRegister)iq.getObject();
		
		Assert.assertNull(iqRegister.getOob());
		
		Assert.assertTrue(iqRegister.getRegister() instanceof TRegistrationForm);
		TRegistrationForm form = (TRegistrationForm)iqRegister.getRegister();
		
		Assert.assertEquals(1, form.getFields().size());
		
		checkRegistrationField("instructions", instructions2, form);
		
		Assert.assertNotNull(iqRegister.getXData());
		
		TXData xData = iqRegister.getXData();
		
		Assert.assertEquals(1, xData.getInstructions().size());
		Assert.assertEquals(instructions3, xData.getInstructions().get(0));
		
		Assert.assertEquals(5, xData.getFields().size());
		
		Assert.assertTrue(checkXDataHiddenOrTextSingleField(TField.Type.HIDDEN, null, "form_type", false, new String[] {"jabber:iq:register"}, xData.getFields().get(0)));
		Assert.assertTrue(checkXDataHiddenOrTextSingleField(TField.Type.TEXT_SINGLE, "Given Name", "first", true, new String[0], xData.getFields().get(1)));
		Assert.assertTrue(checkXDataHiddenOrTextSingleField(TField.Type.TEXT_SINGLE, "Family Name", "last", true, new String[0], xData.getFields().get(2)));
		Assert.assertTrue(checkXDataHiddenOrTextSingleField(TField.Type.TEXT_SINGLE, "Email Address", "email", true, new String[0], xData.getFields().get(3)));
		
		Assert.assertTrue(checkXDataListSingleField("Gender", "x-gender", new TOption[] {new TOption("Male", "M"), new TOption("Female", "F")}, xData.getFields().get(4)));		
	}

	private boolean checkXDataListSingleField(String label, String var, TOption[] options, TField field) {
		if (!HandyUtils.equalsEvenNull(label, field.getLabel()))
			return false;
		
		if (!HandyUtils.equalsEvenNull(var, field.getVar()))
			return false;
			
		if (options.length == 0 && (field.getOptions() != null || field.getOptions().size() != 0))
			return false;
			
		if (options.length != 0 && (field.getOptions() == null || field.getOptions().size() != options.length))
			return false;
			
		for (int i = 0; i < options.length; i++) {
			TOption option = options[i];
			TOption fOption = field.getOptions().get(i);
				
			if (!HandyUtils.equalsEvenNull(option.getLabel(), fOption.getLabel()))
				return false;
		
			if (!HandyUtils.equalsEvenNull(option.getValue(), fOption.getValue()))
				return false;
		}
		
		return true;
	}

	private boolean checkXDataHiddenOrTextSingleField(Type type, String label, String var, boolean required, String[] values, TField field) {
		if (field.getType() != type)
			return false;
			
		if (!HandyUtils.equalsEvenNull(field.getLabel(), label))
			return false;
			
		if (!HandyUtils.equalsEvenNull(field.getVar(), var))
			return false;
			
		if (field.isRequired() != required)
			return false;
			
		if (values.length == 0 && field.getValues().size() != 0)
			return false;
			
		if (values.length != 0 && (field.getValues().size() == 0 || values.length != field.getValues().size()))
			return false;
			
		for (int i = 0; i < values.length; i++) {
			if (!values[i].equals(field.getValues().get(i)))
				return false;
		}
		
		return true;
	}
}
