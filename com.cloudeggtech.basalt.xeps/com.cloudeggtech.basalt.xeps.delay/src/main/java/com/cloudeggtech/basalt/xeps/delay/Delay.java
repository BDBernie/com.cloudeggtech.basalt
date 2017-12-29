package com.cloudeggtech.basalt.xeps.delay;

import com.cloudeggtech.basalt.protocol.core.JabberId;
import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.datetime.DateTime;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.ProtocolObject;
import com.cloudeggtech.basalt.protocol.oxm.convention.conversion.annotations.String2DateTime;
import com.cloudeggtech.basalt.protocol.oxm.convention.conversion.annotations.String2JabberId;

@ProtocolObject(namespace="urn:xmpp:delay", localName="delay")
public class Delay {
	public static final Protocol PROTOCOL = new Protocol("urn:xmpp:delay", "delay");
	
	@String2JabberId
	private JabberId from;
	@String2DateTime
	private DateTime stamp;
	
	public Delay() {}
	
	public Delay(JabberId from, DateTime stamp) {
		this.from = from;
		this.stamp = stamp;
	}
	
	public JabberId getFrom() {
		return from;
	}
	
	public void setFrom(JabberId from) {
		this.from = from;
	}
	
	public DateTime getStamp() {
		return stamp;
	}
	
	public void setStamp(DateTime stamp) {
		this.stamp = stamp;
	}

}
