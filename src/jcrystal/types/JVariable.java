package jcrystal.types;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;

import jcrystal.types.loaders.IJClassLoader;
import jcrystal.types.locals.ILocalVariable;
import jcrystal.types.vars.AbsJAccessor;

public class JVariable implements JIAnnotable, Serializable, JIVariable{
	private static final long serialVersionUID = 2251144499897925662L;
	public IJType type;
	public String name;
	public String value;
	public int modifiers;
	Map<String, JAnnotation> annotations = new TreeMap<>();
	public String staticDefaultValue;
	private JIAnnotable parent;
	public JVariable(IJType type, String name) {
		this(0, type, name);
	}
	public JVariable(IJType type, String name, String value) {
		this(0, type, name);
		this.value = value; 
	}
	public JVariable(int modifiers, IJType type, String name) {
		this.modifiers = modifiers;
		this.type = type;
		this.name = name;
	}
	public JVariable(IJClassLoader jClassLoader, JMethod parent, ILocalVariable p) {
		this.parent = parent;
		name = p.getName();
		type = jClassLoader.load(p.type());
		modifiers = p.getModifiers();
		loadAnnotations(p.annotations());
	}
	public JVariable(IJClassLoader jClassLoader, JClass parent, ILocalVariable f) {
		this.parent = parent;
		name = f.getName();
		type = jClassLoader.load(f.type());
		modifiers = f.getModifiers();
		Arrays.stream(f.annotations()).sorted((c1,c2)->c1.annotationType().getName().compareTo(c2.annotationType().getName())).forEach(a->{
			try {
				annotations.put(a.annotationType().getName(), new JAnnotation(a));				
			}catch (Exception e) {
				throw new NullPointerException();
			}
		});
		staticDefaultValue = f.staticDefaultValue();
	}
	@Override
	public Map<String, JAnnotation> getAnnotations() {
		return annotations;
	}
	@Override
	public IJType type() {
		return type;
	}
	public void type(IJType type) {
		this.type = type;
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
	private AbsJAccessor accessor;
	public AbsJAccessor accessor() {
		if(accessor == null)
			accessor = new AbsJAccessor() {
				@Override
				public IJType type() {
					return JVariable.this.type();
				}
				
				@Override
				public String name() {
					return JVariable.this.name();
				}
				
				
			};
		return accessor.asField().prefix(null);
	}
}
