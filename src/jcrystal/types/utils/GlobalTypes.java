package jcrystal.types.utils;

import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;

import jcrystal.types.IJType;
import jcrystal.types.JType;

public class GlobalTypes{
	
	public static final IJType VOID = GlobalTypes.load(Void.TYPE);
	
	public static final IJType STRING = GlobalTypes.load(String.class);
	public static final IJType Object = GlobalTypes.load(Object.class);
	public static final IJType DATE = GlobalTypes.load(Date.class);
	
	public static final IJType LONG = GlobalTypes.load(long.class);
	public static final IJType INT = GlobalTypes.load(int.class);
	public static final IJType DOUBLE = GlobalTypes.load(double.class);
	public static final IJType BOOLEAN = GlobalTypes.load(boolean.class);
	public static final IJType FLOAT= GlobalTypes.load(float.class);
	public static final IJType CHAR = GlobalTypes.load(char.class);
	public static final IJType BYTE = GlobalTypes.load(byte.class);
	public static final IJType SHORT = GlobalTypes.load(short.class);
	
	public static final IJType OBJ_LONG = GlobalTypes.load(Long.class);
	public static final IJType OBJ_INT = GlobalTypes.load(Integer.class);
	public static final IJType OBJ_DOUBLE = GlobalTypes.load(Double.class);
	public static final IJType OBJ_FLOAT = GlobalTypes.load(Float.class);
	public static final IJType OBJ_BOOLEAN = GlobalTypes.load(Boolean.class);
	public static final IJType OBJ_CHAR = GlobalTypes.load(Character.class);
	public static final IJType OBJ_BYTE = GlobalTypes.load(Byte.class);
	public static final IJType OBJ_SHORT = GlobalTypes.load(Short.class);
	
	public static final IJType[] primitives = {LONG, INT, DOUBLE, BOOLEAN, FLOAT, CHAR, BYTE, SHORT};
	public static final IJType[] primitiveObjects = {OBJ_LONG, OBJ_INT, OBJ_DOUBLE, OBJ_FLOAT, OBJ_BOOLEAN, OBJ_CHAR, OBJ_BYTE, OBJ_SHORT};
	public static final IJType[] primitivesAndObjects = {LONG, INT, DOUBLE, BOOLEAN, FLOAT, CHAR, BYTE, SHORT, OBJ_LONG, OBJ_INT, OBJ_DOUBLE, OBJ_FLOAT, OBJ_BOOLEAN, OBJ_CHAR, OBJ_BYTE, OBJ_SHORT};
	public static final class ARRAY{
		public static IJType DOUBLE = GlobalTypes.load(double[].class);
		public static IJType STRING = GlobalTypes.load(String[].class);
		public static IJType Object = GlobalTypes.load(Object[].class);
	}
	public static final class ARRAY2D{
		public static IJType DOUBLE = GlobalTypes.load(double[][].class);
	}
	public static final class Google{
		public static final class DataStore{
			public static final IJType KEY = new JType(null, "com.google.appengine.api.datastore.Key");
			public static final IJType Text = new JType(null, "com.google.appengine.api.datastore.Text");
		}
	}
	public static final class Java{
		public static final IJType PrintWriter = new JType(null, PrintWriter.class);
		public static final IJType PrintStream = new JType(null, PrintStream.class);
	}
	public static final class Json{
		public static final IJType JSONObject = new JType(null, "org.json.JSONObject");
		public static final IJType JSONArray = new JType(null, "org.json.JSONArray");
	}
	public static final class jCrystal{
		public static final IJType ErrorListener = new JType(null, "OnErrorListener");
		public static final IJType VoidSuccessListener = new JType(null, "OnVoidSuccessListener");
		private static TreeMap<IJType, IJType> NativeSuccessListener = new TreeMap<>();
		static {
			for(IJType type : GlobalTypes.primitivesAndObjects)
				NativeSuccessListener.put(type, new JType(null, "On" + type.getSimpleName() + "SuccessListener").nullable(false));
		}
		public static final IJType NativeSuccessListener(IJType type) {
			IJType ret = NativeSuccessListener.get(type);
			if(ret == null)
				throw new NullPointerException();
			return ret;
		}
		public static final boolean isSuccessListener(IJType type) {
			return type.getSimpleName().startsWith("On") && type.getSimpleName().endsWith("SuccessListener");
		}
		public static final IJType OnSuccessListener(List<IJType> innerTypes) {
			JType ret = new JType(null, "On" + innerTypes.size() + "SuccessListener").nullable(false);
			ret.innerTypes.addAll(innerTypes);
			return ret;
		}
		public static final IJType OnSuccessListener(IJType...innerTypes) {
			JType ret = new JType(null, "On" + innerTypes.length + "SuccessListener").nullable(false);
			for(IJType type : innerTypes)
				ret.innerTypes.add(type);
			return ret;
		}
	}
	public static final Map<IJType, Object> defaultValues = new TreeMap<IJType, Object>();
	public static final Map<IJType, String> defaultValuesStr = new TreeMap<IJType, String>();
	
	// load
	static {
		defaultValues.put(BOOLEAN, Boolean.FALSE);
		defaultValues.put(BYTE, new Byte((byte)0));
		defaultValues.put(SHORT, new Short((short)0));
		defaultValues.put(INT, new Integer(0));
		defaultValues.put(LONG, new Long(0L));
		defaultValues.put(CHAR, new Character('\0'));
		defaultValues.put(FLOAT, new Float(0.0F));
		defaultValues.put(DOUBLE, new Double(0.0));

		defaultValuesStr.put(BOOLEAN, "false");
		defaultValuesStr.put(BYTE, "0");
		defaultValuesStr.put(SHORT, "0");
		defaultValuesStr.put(INT, "0");
		defaultValuesStr.put(LONG, "0l");
		defaultValuesStr.put(CHAR, "'\\0'");
		defaultValuesStr.put(FLOAT, "0.0f");
		defaultValuesStr.put(DOUBLE, "0.0");
	}
	private static IJType load(Class<?> clase){
		return new JType(null, clase);
	}
}
