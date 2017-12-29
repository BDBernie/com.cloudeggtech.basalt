package com.cloudeggtech.basalt.protocol.im.roster;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.im.roster.Item.Ask;
import com.cloudeggtech.basalt.protocol.im.roster.Item.Subscription;
import com.cloudeggtech.basalt.protocol.oxm.Attribute;
import com.cloudeggtech.basalt.protocol.oxm.Attributes;
import com.cloudeggtech.basalt.protocol.oxm.Value;
import com.cloudeggtech.basalt.protocol.oxm.translating.IProtocolWriter;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatingFactory;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslator;
import com.cloudeggtech.basalt.protocol.oxm.translating.ITranslatorFactory;

public class RosterTranslatorFactory implements ITranslatorFactory<Roster> {
	private static final ITranslator<Roster> translator = new RosterTranslator();

	@Override
	public Class<Roster> getType() {
		return Roster.class;
	}

	@Override
	public ITranslator<Roster> create() {
		return translator;
	}
	
	private static class RosterTranslator implements ITranslator<Roster> {

		@Override
		public Protocol getProtocol() {
			return Roster.PROTOCOL;
		}

		@Override
		public String translate(Roster roster, IProtocolWriter writer, ITranslatingFactory translatingFactory) {
			writer.writeProtocolBegin(Roster.PROTOCOL); // roster
			
			if (roster.getItems().length != 0) {
				writer.writeArrayBegin("items"); // items
				for (Item item : roster.getItems()) {
					Subscription subscription = item.getSubscription();
					Ask ask = item.getAsk();
					writer.writeElementBegin("item"); // item
					writer.writeAttributes(new Attributes().
							add(new Attribute("jid", item.getJid().toString())).
							add(new Attribute("name", item.getName())).
							add(new Attribute("subscription", subscription == null ? null : subscription.toString().toLowerCase())).
							add(new Attribute("ask", ask == null ? null : ask.toString().toLowerCase())).
							get()
					);
					
					if (item.getGroups().size() != 0) {
						writer.writeArrayBegin("groups"); // groups
						for (String group : item.getGroups()) {
							writer.writeTextOnly("group", Value.create(group)); //group
						}
						writer.writeArrayEnd(); // end of groups						
					}
					
					writer.writeElementEnd(); // end of item
				}
				
				writer.writeArrayEnd(); // end of items				
			}
			
			writer.writeProtocolEnd(); // end of roster
			
			return writer.getDocument();
		}
		
	}

}
