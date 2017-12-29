package com.cloudeggtech.basalt.protocol.oxm.xml;

import java.util.List;
import java.util.Stack;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.core.stream.Stream;
import com.cloudeggtech.basalt.protocol.oxm.Attribute;
import com.cloudeggtech.basalt.protocol.oxm.Value;
import com.cloudeggtech.basalt.protocol.oxm.translating.IProtocolWriter;

public class XmlProtocolWriter implements IProtocolWriter {
	private StringBuilder buffer = new StringBuilder();
	private Stack<Context> contexts = new Stack<>();
	
	private static class Context {
		public enum State {
			EMPTY,
			ATTRIBUTES_HAS_WRITTEN,
			TEXT_HAS_WRITTEN,
			SUB_ELEMENT_HAS_WRITTEN
		}
		
		public enum Type {
			PROTOCOL_ELEMENT,
			NORMAL_ELEMENT
		}
		
		public Type type;
		public State state;
		public Object data;
		
		public Context(Type type, Object data) {
			this.type = type;
			this.data = data;
			state = State.EMPTY;
			
		}
	}
	
	@Override
	public IProtocolWriter writeString(String string) {
		closeStartTagIfNeed(Context.State.SUB_ELEMENT_HAS_WRITTEN);
		
		buffer.append(string);		
		return this;
	}

	private void closeStartTagIfNeed(Context.State state) {
		if (!contexts.isEmpty()) {
			Context context = contexts.peek();
			if (context.state == Context.State.EMPTY || context.state == Context.State.ATTRIBUTES_HAS_WRITTEN) {
				buffer.append('>');
			}
			
			context.state = state;
		}
	}
	
	@Override
	public IProtocolWriter writeProtocolBegin(Protocol protocol) {
		closeStartTagIfNeed(Context.State.SUB_ELEMENT_HAS_WRITTEN);
		
		buffer.append('<');
		if (Stream.PROTOCOL.getNamespace().equals(protocol.getNamespace())) {
			buffer.append("stream:").append(protocol.getLocalName());
			buffer.append(' ').append("xmlns:stream=\"").append(Stream.PROTOCOL.getNamespace()).append("\"");
		} else {
			buffer.append(protocol.getLocalName());
			
			if (protocol.getNamespace() != null) {
				buffer.append(" xmlns=").
					append('"').
					append(protocol.getNamespace()).
					append('"');
			}
		}
		
		contexts.push(new Context(Context.Type.PROTOCOL_ELEMENT, protocol));
		
		return this;
	}
	
	@Override
	public IProtocolWriter writeProtocolEnd() {
		Context context = contexts.pop();
		
		if (context.type != Context.Type.PROTOCOL_ELEMENT) {
			throw new IllegalStateException("current context isn't protocol element");
		}
		
		if (context.state == Context.State.EMPTY || context.state == Context.State.ATTRIBUTES_HAS_WRITTEN) {
			buffer.append('/').append('>');
		} else {
			Protocol protocol = (Protocol)context.data;
			
			buffer.append('<').
				append('/');
			
			if (Stream.PROTOCOL.getNamespace().equals(protocol.getNamespace())) {
				buffer.append("stream:");
			}
			
			buffer.append(protocol.getLocalName()).
				append('>');
		}
		
		return this;
	}

	@Override
	public IProtocolWriter writeElementBegin(String prefix, String localName) {
		if (contexts.isEmpty()) {
			throw new IllegalStateException("Normal element mustn't be at the top level.");
		}
		
		closeStartTagIfNeed(Context.State.SUB_ELEMENT_HAS_WRITTEN);
		
		buffer.append('<');
		if (prefix != null) {
			buffer.append(prefix).append(':');
		}
		
		buffer.append(localName);
		
		String elementName = prefix == null ? localName : String.format("%s:%s", prefix, localName);
		contexts.push(new Context(Context.Type.NORMAL_ELEMENT, elementName));
		
		return this;
	}

	@Override
	public IProtocolWriter writeElementBegin(String localName) {
		return writeElementBegin(null, localName);
	}

	@Override
	public IProtocolWriter writeElementEnd() {
		if (contexts.size() <= 1) {
			throw new IllegalStateException("Current context isn't normal element.");
		}
		
		Context context = contexts.pop();
		
		if (context.type != Context.Type.NORMAL_ELEMENT) {
			throw new IllegalStateException("Current context isn't normal element.");
		}
		
		if (context.state == Context.State.EMPTY || context.state == Context.State.ATTRIBUTES_HAS_WRITTEN) {
			buffer.append('/').append('>');
		} else {
			String elementName = (String)context.data;
			buffer.append('<').append('/');
			buffer.append(elementName).append('>');
		}
		
		return this;
	}

	@Override
	public IProtocolWriter writeEmptyElement(String prefix, String localName) {
		if (contexts.isEmpty()) {
			throw new IllegalStateException("Normal element mustn't be at the top level.");
		}
		
		closeStartTagIfNeed(Context.State.SUB_ELEMENT_HAS_WRITTEN);
		
		buffer.append('<');
		if (prefix != null) {
			buffer.append(prefix).append(':');
		}
		
		buffer.append(localName).append('/').append('>');
		
		return this;
	}

	@Override
	public IProtocolWriter writeEmptyElement(String localName) {
		return writeEmptyElement(null, localName);
	}

	@Override
	public IProtocolWriter writeArrayBegin(String prefix, String localName) {
		return this;
	}

	@Override
	public IProtocolWriter writeArrayBegin(String localName) {
		return this;
	}

	@Override
	public IProtocolWriter writeArrayEnd() {
		return this;
	}

	@Override
	public IProtocolWriter writeAttributes(List<Attribute> attributes) {
		buffer.append(' ');
		for (Attribute attribute : attributes) {
			if (attribute.getPrefix() != null) {
				buffer.append(attribute.getPrefix()).append(':');
			}
			
			buffer.append(attribute.getLocalName()).append('=').append('"');
			buffer.append(escape(attribute.getValue().toString())).append('"').append(' ');
		}
		
		if (buffer.charAt(buffer.length() - 1) == ' ') {
			buffer.delete(buffer.length() - 1, buffer.length());
		}
		
		contexts.peek().state = Context.State.ATTRIBUTES_HAS_WRITTEN;
		
		return this;
	}

	private String escape(String string) {
		char[] chars = string.toCharArray();
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < chars.length; i++) {
			char c = chars[i];
			switch (c) {
			case '&':
				sb.append("&amp;");
				break;
			case '"':
				sb.append("&quot;");
				break;
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case '\'':
				sb.append("&apos;");
				break;
			default:
				sb.append(c);
			}
		}
		
		return sb.toString();
	}

	@Override
	public IProtocolWriter writeText(Value<?> value) {
		return writeText(value.getString());
	}

	@Override
	public IProtocolWriter writeText(String value) {
		closeStartTagIfNeed(Context.State.TEXT_HAS_WRITTEN);
		
		if (value != null) {
			buffer.append(escape(value));
		}
		
		return this;
	}

	@Override
	public IProtocolWriter writeTextOnly(String prefix, String localName, Value<?> value) {
		return writeTextOnly(prefix, localName, value == null ? null : value.toString());
	}

	@Override
	public IProtocolWriter writeTextOnly(String localName, Value<?> value) {
		return writeTextOnly(null, localName, value);
	}

	@Override
	public IProtocolWriter writeTextOnly(String prefix, String localName, String value) {
		if (value != null) {
			writeElementBegin(prefix, localName);
			writeText(value);
			writeElementEnd();			
		}
		
		return this;
	}

	@Override
	public IProtocolWriter writeTextOnly(String localName, String value) {
		return writeTextOnly(null, localName, value);
	}

	@Override
	public String getDocument() {
		return buffer.toString();
	}

	@Override
	public IProtocolWriter clear() {
		contexts.clear();
		buffer.delete(0, buffer.length());
		
		return this;
	}
	
	@Override
	public String toString() {
		return buffer.toString();
	}

}
