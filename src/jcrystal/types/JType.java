package jcrystal.types;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import jcrystal.types.loaders.IJClassLoader;
import jcrystal.types.locals.ILocalType;
import jcrystal.types.locals.JavaLocalType;

public class JType implements Serializable, IJType{
	private static final long serialVersionUID = -875202507362620017L;

	public String name;
	public String simpleName;
	public String packageName;
	public boolean isArray;
	public boolean isInterface;
	public boolean isEnum;
	boolean primitive;
	boolean nullable;
	public List<IJType> innerTypes = new ArrayList<>();
	public IJClassLoader jClassLoader;
	public Map<String, JAnnotation> annotations = new TreeMap<>();
	
	/**
	 * True if this Type comes from a Java file written by coder.
	 */
	public boolean isClientType;
	public JType(IJClassLoader jClassLoader, Class<?> f, Type genericType) {
		this(jClassLoader, new JavaLocalType(f, genericType));
	}
	JType(IJClassLoader jClassLoader){
		this.jClassLoader = jClassLoader;
	}
	public JType(IJClassLoader jClassLoader, String name) {
		this.jClassLoader = jClassLoader;
		this.name = name;
		this.nullable = true;
		if(name.indexOf('.') >= 0) {
			this.simpleName = name.substring(name.lastIndexOf(".") + 1);
			this.packageName = name.substring(0, name.lastIndexOf("."));
		}else {
			this.simpleName = name;
			this.packageName = null;
		}
	}
	public JType(IJClassLoader jClassLoader, Class<?> f) {
		this(jClassLoader, new JavaLocalType(f, null));
	}
	public JType(IJClassLoader jClassLoader, ILocalType localType) {
		this.jClassLoader = jClassLoader;
		name = localType.getName();
		simpleName = localType.getSimpleName();
		packageName = localType.getPackageName();
		isArray = localType.isArray();
		isInterface = localType.isInterface();
		isEnum = localType.isEnum();
		primitive = localType.isPrimitive();
		nullable = !localType.isPrimitive();
		isClientType = localType.isClientType();
		innerTypes = localType.getInnerTypes().stream().map(t->{
			return (IJType)new JType(jClassLoader, t);
		}).collect(Collectors.toList());
	}
	public List<IJType> getInnerTypes() {
		return innerTypes;
	}
	@Override
	public JClass tryResolve() {
		IJType ret = jClassLoader.forName(name);
		if(ret == null || !(ret instanceof JClass))
			return null;
		return (JClass)ret;
	}
	@Override
	public final boolean isEnum() {
		return isEnum;
	}
	@Override
	public final boolean isArray() {
		return isArray;
	}
	@Override
	public final boolean isPrimitive() {
		return primitive;
	}
	@Override
	public final boolean isInterface() { return isInterface;}
	@Override
	public IJClassLoader classLoader() {
		return jClassLoader;
	}
	@Override
	public boolean isSubclassOf(Class<?> clase) {
		return !isPrimitive() && (is(clase) || (jClassLoader != null && jClassLoader.subclassOf(this, clase)));
	}
	public boolean isSubclassOf(Class<?>...clases) {
		for(Class<?> clase : clases)
			if(isSubclassOf(clase))
				return true;
		return false;
	}
	@Override
	public final boolean isSubclassOf(IJType clase) {
		return !isPrimitive() && (is(clase) || (jClassLoader != null && jClassLoader.subclassOf(this, clase)));
	}
	@Override
	public final boolean is(Class<?> ... classes) {
		for(Class<?> c : classes)if(c.getName().equals(name))return true;
		return false;
	}
	@Override
	public final boolean is(IJType ... classes) {
		for(IJType c : classes)if(c.name().equals(name))return true;
		return false;
	}
	@Override
	public final String name() {
		return name;
	}
	@Override
	public final String getSimpleName() {
		return simpleName;
	}
	@Override
	public JPackage getPackage() {
		return jClassLoader.packageForName(packageName);
	}
	@Override
	public final String getPackageName() {
		return packageName;
	}
	@Override
	public final Map<String, JAnnotation> getAnnotations() {
		return annotations;
	}
	@Override
	public String toString() {
		if(getInnerTypes().isEmpty())
			return name()+":";
		else if(isArray())
			return getInnerTypes().get(0)+":[]";
		else
			return name()+":<" + getInnerTypes().stream().map(f->f.toString()).collect(Collectors.joining(", ")) + ">";
	}
	
	@Override
	public boolean nullable() {
		return nullable;
	}
	public JType nullable(boolean nullable) {
		this.nullable = nullable;
		return this;
	}
	@Override
	public JAnnotation getJAnnotationWithAncestorCheck(String name) {
		JAnnotation ret = getJAnnotation(name);
		if(ret != null)
			return ret;
		JPackage p = getPackage();
		if(p != null)
			return p.getJAnnotationWithAncestorCheck(name);
		return null;
	}
}
