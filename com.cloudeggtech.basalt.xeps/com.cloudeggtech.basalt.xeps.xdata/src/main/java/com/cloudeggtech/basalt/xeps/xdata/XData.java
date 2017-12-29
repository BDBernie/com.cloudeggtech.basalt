package com.cloudeggtech.basalt.xeps.xdata;

import java.util.ArrayList;
import java.util.List;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.Array;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.ProtocolObject;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.TextOnly;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.Validate;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.ValidationClass;
import com.cloudeggtech.basalt.protocol.oxm.validation.ValidationException;

@ValidationClass
@ProtocolObject(namespace="jabber:x:data", localName="x")
public class XData {
	public static final Protocol PROTOCOL = new Protocol("jabber:x:data", "x");
	
	public enum Type {
		FORM,
		SUBMIT,
		CANCEL,
		RESULT
	}
	
	@TextOnly
	private String title;
	@String2XDataType
	private Type type;
	@Array(type=String.class, elementName="instructions")
	@TextOnly
	private List<String> instructions;
	private Reported reported;
	@Array(type=Field.class, elementName="field")
	private List<Field> fields;
	@Array(type=Item.class, elementName="item")
	private List<Item> items;
	
	public XData() {}
	
	public XData(Type type) {
		this.type = type;
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public List<String> getInstructions() {
		if (instructions == null)
			instructions = new ArrayList<>();
		
		return instructions;
	}
	
	public void setInstructions(List<String> instructions) {
		this.instructions = instructions;
	}
	
	public Reported getReported() {
		return reported;
	}
	
	public void setReported(Reported reported) {
		this.reported = reported;
	}
	
	public List<Field> getFields() {
		if (fields == null) {
			fields = new ArrayList<>();
		}
		
		return fields;
	}
	
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	
	public List<Item> getItems() {
		if (items == null) {
			items = new ArrayList<>();
		}
		
		return items;
	}
	
	public void setItems(List<Item> items) {
		this.items = items;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
	
	@Validate({"/fields/[field]", "/reported/fields/[field]", "/item/fields/[field]"})
	public void validateField(Field field) throws ValidationException {
		if (field.getOptions() != null && field.getOptions().size() > 0) {
			if (field.getType() != Field.Type.LIST_MULTI && field.getType() != Field.Type.LIST_SINGLE)
				throw new ValidationException(String.format("options is only allowed in list_multi or list_single type field," +
						" but this is a %s type field", field.getType()));
		}
	}
}
