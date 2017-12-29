package com.cloudeggtech.basalt.leps.im.subscription;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.oxm.convention.annotations.ProtocolObject;

@ProtocolObject(namespace="urn:lep:subscription", localName="subscribed")
public class Subscribed {
	public static final Protocol PROTOCOL = new Protocol("urn:lep:subscription", "subscribed");
}
