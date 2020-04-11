package jcrystal.types;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import jcrystal.types.loaders.IJClassLoader;

public class JVariable implements JIAnnotable, Serializable, JIVariable{
	private static final long serialVersionUID = 2251144499897925662L;
	public IJType type;
	public String name;
	public int modifiers;
	Map<String, JAnnotation> annotations = new TreeMap<>();
	public String staticDefaultValue;
	private JIAnnotable parent;
	public JVariable(int modifiers, IJType type, String name) {
		this.modifiers = modifiers;
		this.type = type;
		this.name = name;
	}
	public JVariable(IJClassLoader jClassLoader, JMethod parent, Parameter p) {
		this.parent = parent;
		name = p.getName();
		type = jClassLoader.load(p.getType(), p.getParameterizedType());
		modifiers = p.getModifiers();
		loadAnnotations(p.getAnnotations());
	}
	public JVariable(IJClassLoader jClassLoader, JClass parent, Field f) {
		this.parent = parent;
		name = f.getName();
		type = jClassLoader.load(f.getType(), f.getGenericType());
		modifiers = f.getModifiers();
		Arrays.stream(f.getAnnotations()).sorted((c1,c2)->c1.annotationType().getName().compareTo(c2.annotationType().getName())).forEach(a->{
			try {
				annotations.put(a.annotationType().getName(), new JAnnotation(a));				
			}catch (Exception e) {
				throw new NullPointerException();
			}
		});
		
		if(Modifier.isStatic(modifiers)) {
			try {
				f.setAccessible(true);
				Object defaultValue = f.get(null);
				if(defaultValue != null) {
					staticDefaultValue = defaultValue.toString();
					if(type().isPrimitive()) {
						if(f.getType() == long.class && defaultValue.equals(new Long(0)))
							staticDefaultValue = null;		
						else if(f.getType() == int.class && defaultValue.equals(new Integer(0)))
							staticDefaultValue = null;
						else if(f.getType() == double.class && defaultValue.equals(new Double(0)))
							staticDefaultValue = null;
						else if(f.getType() == boolean.class && defaultValue.equals(new Boolean(false)))
							staticDefaultValue = null;
					}else if(type().isEnum()) {
						staticDefaultValue = f.getType().getSimpleName()+"."+f.getType().getMethod("name").invoke(defaultValue).toString();
					}else if(defaultValue.equals(new Long(0)))
						staticDefaultValue = null;		
					else if(defaultValue.equals(new Integer(0)))
						staticDefaultValue = null;
					else if(defaultValue.equals(new Double(0)))
						staticDefaultValue = null;
					else if(defaultValue.equals(new Boolean(false)))
						staticDefaultValue = null;

				}
				if(defaultValue != null && f.getType().isEnum())
					defaultValue = f.getType().getSimpleName()+"."+f.getType().getMethod("name").invoke(defaultValue);
			} catch (Exception e) {}
		}
	}
	@Override
	public Map<String, JAnnotation> getAnnotations() {
		return annotations;
	}
	@Override
	public IJType type() {
		return type;
	}
	@Override
	public String name() {
		return name;
	}
	@Override
	public int modifiers() {
		return modifiers;
	}
	@Override
	public JAnnotation getJAnnotationWithAncestorCheck(String name) {
		JAnnotation ret = getJAnnotation(name);
		if(ret == null && parent != null)
			ret = parent.getJAnnotationWithAncestorCheck(name);
		return ret;
	}
}
