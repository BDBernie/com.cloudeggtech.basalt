package com.cloudeggtech.basalt.xeps.muc.admin;

import java.util.ArrayList;
import java.util.List;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.Array;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.ProtocolObject;
import com.cloudeggtech.basalt.xeps.muc.user.Item;

@ProtocolObject(namespace="http://jabber.org/protocol/muc#admin", localName="query")
public class MucAdmin {
	public static final Protocol PROTOCOL = new Protocol("http://jabber.org/protocol/muc#admin", "query");
	
	@Array(type=Item.class, elementName="item")
	private List<Item> items;

	public List<Item> getItems() {
		if (items == null)
			items = new ArrayList<>();
		
		return items;
	}

	public void setItems(List<Item> items) {
		this.items = items;
	}
	
}
