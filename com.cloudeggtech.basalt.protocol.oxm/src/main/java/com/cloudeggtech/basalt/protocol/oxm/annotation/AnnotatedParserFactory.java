package com.cloudeggtech.basalt.protocol.oxm.annotation;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import com.cloudeggtech.basalt.protocol.core.Protocol;
import com.cloudeggtech.basalt.protocol.oxm.Attribute;
import com.cloudeggtech.basalt.protocol.oxm.Value;
import com.cloudeggtech.basalt.protocol.oxm.annotations.Parser;
import com.cloudeggtech.basalt.protocol.oxm.annotations.ProcessAttributes;
import com.cloudeggtech.basalt.protocol.oxm.annotations.ProcessEmbeddedObject;
import com.cloudeggtech.basalt.protocol.oxm.annotations.ProcessText;
import com.cloudeggtech.basalt.protocol.oxm.parsing.ElementParserAdaptor;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IElementParser;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParser;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParserFactory;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParsingContext;
import com.cloudeggtech.basalt.protocol.oxm.parsing.IParsingPath;
import com.cloudeggtech.basalt.protocol.oxm.parsing.ParsingPath;

public class AnnotatedParserFactory<T> implements IParserFactory<T> {
	private Object annotatedParser;
	private Protocol protocol;
	private Class<T> objectType;
	private static final String NULL_NAMESPACE = "";
	private Map<String, IElementParser<T>> elementParsers;
	private Method processEmbeddedObjectMethod;
	private boolean stateless;
	private AnnotatedParser parser;
	
	@SuppressWarnings("unchecked")
	public AnnotatedParserFactory(Class<?> annotatedParserType) {
		Parser parserAnnotation = annotatedParserType.getAnnotation(Parser.class);
		if (parserAnnotation == null) {
			throw new IllegalArgumentException("No @Parser found.");
		}
		
		stateless = parserAnnotation.stateless();
		String namespace = parserAnnotation.namespace();
		protocol = new Protocol(NULL_NAMESPACE.equals(getImplSpecificParsingPath(namespace)) ? null : getImplSpecificParsingPath(namespace), parserAnnotation.localName());
		
		objectType = (Class<T>)parserAnnotation.objectType();
		
		Method[] methods = annotatedParserType.getMethods();
		elementParsers = assembleElementParsers(methods);
		scanProcessEmbeddedObjectMethod(methods);
		
		try {
			annotatedParser = annotatedParserType.newInstance();
		} catch (Exception e) {
			throw new RuntimeException("Can't initiate annoated parser.", e);
		}
		
		if (stateless) {
			parser = new AnnotatedParser();
		}
	}
	
	private void scanProcessEmbeddedObjectMethod(Method[] methods) {
		for (Method method : methods) {
			ProcessEmbeddedObject processEmbeddedObject = method.getAnnotation(ProcessEmbeddedObject.class);
			if (processEmbeddedObject != null) {
				if (!checkProcessEmbeddedObjectMethodSigunature(method)) {
					throw new IllegalArgumentException("Illegal process embedded object method. " +
						"Method sigunature should be void processEmbeddedObject(IParsingContext<T> context, Protocol protocol, Object object).");
				}
				
				processEmbeddedObjectMethod = method;
			}
		}
	}

	public boolean getStateless() {
		return stateless;
	}
	
	private Map<String, IElementParser<T>> assembleElementParsers(Method[] methods) {
		Map<String, IElementParser<T>> elementParsers = new HashMap<>();
		Map<String, Method> processTextMethods = new HashMap<>();
		Map<String, Method> processAttributesMethods = new HashMap<>();
		List<String> paths = new ArrayList<>();
		
		for (Method method : methods) {
			if (method.getDeclaringClass() == Object.class)
				continue;
			
			ProcessAttributes processAttributes = method.getAnnotation(ProcessAttributes.class);
			if (processAttributes != null) {
				if (!checkProcessAttributesMethodSignature(method)) {
					throw new IllegalArgumentException("Illegal process attributes method sigunature. " +
							"Method sigunature should be void processAttributes(IParsingContext<T> context, List<Attribute> attributes).");
				}
				
				String path = getImplSpecificParsingPath(processAttributes.value());
				processAttributesMethods.put(path, method);
				if (!paths.contains(path)) {
					paths.add(path);
				}
				
				continue;
			}
			
			ProcessText processText = method.getAnnotation(ProcessText.class);
			if (processText != null) {
				if (!checkProcessTextMethodSigunature(method)) {
					throw new IllegalArgumentException("Illegal process text method sigunature. " +
						"Method sigunature should be void processText(IParsingContext<T> context, Value<?> text).");
				}
				
				String path = getImplSpecificParsingPath(processText.value());
				processTextMethods.put(path, method);
				if (!paths.contains(path)) {
					paths.add(path);
				}
			}
		}
		
		for (String path : paths) {
			Method processAttributesMethod = processAttributesMethods.get(path);
			Method processTextMethod = processTextMethods.get(path);
			
			AnnotatedElementParser elementParser = new AnnotatedElementParser(
					processAttributesMethod, processTextMethod);
			elementParsers.put(path, elementParser);
		}
		
		return elementParsers;
	}

	private String getImplSpecificParsingPath(String parsingPathPattern) {
		if ("/".equals(parsingPathPattern)) {
			return parsingPathPattern;
		}
		
		if (parsingPathPattern.indexOf("/[") == -1)
			return parsingPathPattern;
		
		StringTokenizer st;
		
		if (parsingPathPattern.startsWith("/")) {
			st = new StringTokenizer(parsingPathPattern.substring(1), "/");
		} else {
			st = new StringTokenizer(parsingPathPattern, "/");
		}
		
		ParsingPath implSpecific = new ParsingPath();
		while (st.hasMoreTokens()) {
			String path = st.nextToken();
			if (path.startsWith("[") && path.endsWith("]")) {
				if (implSpecific.getPaths().size() <= 1) {
					throw new IllegalArgumentException("Invalid parsing pattern. Wrong array position?");
				}
				
				if (path.length() == 2) {
					throw new IllegalArgumentException("Invalid parsing pattern. Null array element name?");
				}
				
				implSpecific.exit(); // remove array name
				implSpecific.enter(path.substring(1, path.length() - 1)); // remove '[' and ']'
			} else {
				implSpecific.enter(path);
			}
		}
		
		return implSpecific.toString();
	}
	
	private boolean checkProcessTextMethodSigunature(Method method) {
		if (method.getReturnType() != void.class) {
			return false;
		}
		
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length != 2)
			return false;
		
		if (!paramTypes[0].equals(IParsingContext.class)) {
			return false;
		}
		
		if (!paramTypes[1].equals(Value.class)) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkProcessAttributesMethodSignature(Method method) {
		if (method.getReturnType() != void.class) {
			return false;
		}
		
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length != 2)
			return false;
		
		if (!paramTypes[0].equals(IParsingContext.class)) {
			return false;
		}
		
		if (!paramTypes[1].equals(List.class)) {
			return false;
		}
		
		return true;
	}
	
	private boolean checkProcessEmbeddedObjectMethodSigunature(Method method) {
		if (method.getReturnType() != void.class) {
			return false;
		}
		
		Class<?>[] paramTypes = method.getParameterTypes();
		if (paramTypes.length != 3)
			return false;
		
		if (!paramTypes[0].equals(IParsingContext.class)) {
			return false;
		}
		
		if (!paramTypes[1].equals(Protocol.class)) {
			return false;
		}
		
		if (!paramTypes[2].equals(Object.class)) {
			return false;
		}
		
		return true;
	}

	@Override
	public Protocol getProtocol() {
		return protocol;
	}
	
	private class AnnotatedElementParser extends ElementParserAdaptor<T> {
		private Method processAttributesMethod;
		private Method processTextMethod;
		
		public AnnotatedElementParser(Method processAttributesMethod, Method processTextMethod) {
			this.processAttributesMethod = processAttributesMethod;
			this.processTextMethod = processTextMethod;
		}
		
		@Override
		public void processText(IParsingContext<T> context, Value<?> text) {
			if (processTextMethod != null) {
				try {
					processTextMethod.invoke(annotatedParser, new Object[] {context, text});
				} catch (Exception e) {
					throw new RuntimeException("Can't invoke process text method.", e);
				}
			} else {
				super.processText(context, text);
			}
		}

		@Override
		public void processAttributes(IParsingContext<T> context, List<Attribute> attributes) {
			if (processAttributesMethod != null) {
				try {
					processAttributesMethod.invoke(annotatedParser, new Object[] {context, attributes});
				} catch (Exception e) {
					throw new RuntimeException("Can't invoke process attributes method.", e);
				}
			} else {
				super.processAttributes(context, attributes);
			}
		}
	}
	
	@Override
	public IParser<T> create() {
		if (stateless) {
			return parser;
		} else {
			return new AnnotatedParser();
		}
	}
	
	private class AnnotatedParser implements IParser<T> {
		
		@Override
		public T createObject() {
			try {
				return objectType.newInstance();
			} catch (Exception e) {
				throw new RuntimeException("Can't create protocol object.", e);
			}
		}

		@Override
		public IElementParser<T> getElementParser(IParsingPath parsingPath) {
			IElementParser<T> parser = elementParsers.get(parsingPath.toString());
			
			if (parser == null) {
				String sParsingPath = parsingPath.toString();
				for (String explicit : elementParsers.keySet()) {
					if (explicit.startsWith(sParsingPath)) {
						parser = new ElementParserAdaptor<>();
						break;
					}
				}
				
				if (processEmbeddedObjectMethod != null) {
					parser = new ElementParserAdaptor<>();
				}
			}
			
			return parser;
		}

		@Override
		public void processEmbeddedObject(IParsingContext<T> context, Protocol protocol, Object embedded) {
			if (processEmbeddedObjectMethod != null) {
				try {
					processEmbeddedObjectMethod.invoke(annotatedParser, new Object[] {context, protocol, embedded});
				} catch (Exception e) {
					throw new RuntimeException("Can't invoke process embedded object method.", e);
				}
			}
		}
	}
}
