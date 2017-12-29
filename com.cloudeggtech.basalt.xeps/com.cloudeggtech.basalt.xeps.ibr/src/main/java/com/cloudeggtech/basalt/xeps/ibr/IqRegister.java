package com.cloudeggtech.basalt.xeps.ibr;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.ProtocolObject;
import com.cloudeggtech.basalt.xeps.oob.XOob;
import com.cloudeggtech.basalt.xeps.xdata.XData;

@ProtocolObject(namespace="jabber:iq:register", localName="query")
public class IqRegister {
	public static final Protocol PROTOCOL = new Protocol("jabber:iq:register", "query");
	
	private Object register;
	
	private XData xData;
	private XOob oob;
	
	public Object getRegister() {
		return register;
	}
	
	public void setRegister(Object register) {
		this.register = register;
	}
	
	public XData getXData() {
		return xData;
	}
	
	public void setXData(XData xData) {
		this.xData = xData;
	}
	
	public XOob getOob() {
		return oob;
	}
	
	public void setOob(XOob oob) {
		this.oob = oob;
	}
	
}
