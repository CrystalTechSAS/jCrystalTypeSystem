package jcrystal.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jcrystal.types.loaders.IJClassLoader;
import jcrystal.types.locals.ILocalEnum;
import jcrystal.types.locals.ILocalEnumValue;
import jcrystal.types.locals.JavaLocalEnum;
import jcrystal.types.utils.GlobalTypes;

public class JEnum implements Serializable{
	private static final long serialVersionUID = 1342567432L;
	public final String name;
	public final List<EnumValue> valores = new ArrayList<>();
	public final Map<String, IJType> propiedades = new TreeMap<>();
	public JEnum(IJClassLoader jClassLoader, Class<?> clase) {
		this(jClassLoader, new JavaLocalEnum(clase));
	}
	public JEnum(IJClassLoader jClassLoader, ILocalEnum en) {
		this.name = en.getName();
		en.getProperties().forEach((name, type)->{
			propiedades.put(name, new JType(jClassLoader, type) );
		});
		propiedades.put("rawName", GlobalTypes.STRING);
		for (ILocalEnumValue o : en.getValues()) {
			valores.add(new EnumValue(this, o));
		}
	}
	public class EnumValue implements Serializable{
		private static final long serialVersionUID = 7767854074029064604L;
		public final Map<String, Object> propiedades = new TreeMap<>();
		public final String name;
		public EnumValue(JEnum en, ILocalEnumValue value) {
			name = value.getName();
			en.propiedades.keySet().forEach(key->{
				propiedades.put(key, value.getPropertyValue(key));
			});
			propiedades.put("rawName", name);	
		}
	}
}
