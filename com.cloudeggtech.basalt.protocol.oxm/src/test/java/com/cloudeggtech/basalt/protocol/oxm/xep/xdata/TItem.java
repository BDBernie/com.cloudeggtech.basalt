package com.cloudeggtech.basalt.protocol.oxm.xep.xdata;

import java.util.ArrayList;
import java.util.List;

import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.Array;

public class TItem {
	@Array(type=TField.class, elementName="field")
	private List<TField> fields;

	public List<TField> getFields() {
		if (fields == null) {
			fields = new ArrayList<>();
		}
		
		return fields;
	}

	public void setFields(List<TField> fields) {
		this.fields = fields;
	}
	
}
