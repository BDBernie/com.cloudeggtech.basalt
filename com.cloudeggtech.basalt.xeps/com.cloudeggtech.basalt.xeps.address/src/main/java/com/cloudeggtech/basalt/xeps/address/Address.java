package com.cloudeggtech.basalt.xeps.address;

import com.cloudeggtech.basalt.protocol.core.JabberId;
import com.cloudeggtech.basalt.protocol.core.ProtocolException;
import com.cloudeggtech.basalt.protocol.core.stanza.error.BadRequest;
import com.cloudeggtech.basalt.protocol.oxm.convention.conversion.annotations.String2Enum;
import com.cloudeggtech.basalt.protocol.oxm.convention.conversion.annotations.String2JabberId;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.Validate;
import com.cloudeggtech.basalt.protocol.oxm.convention.validation.annotations.ValidationClass;

@ValidationClass
public class Address {
	public enum Type {
		TO,
		CC,
		BCC,
		REPLYTO,
		REPLYROOM,
		NOREPLY
	}
	
	@String2JabberId
	private JabberId jid;
	private String uri;
	private String node;
	private String desc;
	private Boolean delivered;
	@String2Enum(type=Address.Type.class)
	private Type type;
	
	public JabberId getJid() {
		return jid;
	}
	
	public void setJid(JabberId jid) {
		this.jid = jid;
	}
	
	public String getUri() {
		return uri;
	}
	
	public void setUri(String uri) {
		this.uri = uri;
	}
	
	public String getNode() {
		return node;
	}
	
	public void setNode(String node) {
		this.node = node;
	}
	
	public String getDesc() {
		return desc;
	}
	
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
	public Boolean getDelivered() {
		return delivered;
	}
	
	public void setDelivered(Boolean delivered) {
		this.delivered = delivered;
	}
	
	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}
	
	@Validate("/")
	public void validAddress(Address address) {
		if (Boolean.FALSE.equals(delivered)) {
			throw new ProtocolException(new BadRequest("value of attribute 'delivered' must be set to 'true'"));
		}
	}
	
}
