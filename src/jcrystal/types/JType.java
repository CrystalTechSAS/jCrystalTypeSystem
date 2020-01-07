package jcrystal.types;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import jcrystal.types.loaders.IJClassLoader;

public class JType implements JIAnnotable, Serializable, IJType{
	private static final long serialVersionUID = -875202507362620017L;

	public String name;
	public String simpleName;
	public String packageName;
	public boolean isArray;
	public boolean isEnum;
	boolean primitive;
	boolean nullable;
	public List<IJType> innerTypes = new ArrayList<>();
	public IJClassLoader jClassLoader;
	public JType(IJClassLoader jClassLoader, Class<?> f, Type genericType) {
		this(jClassLoader, f);
		if(!isArray && !isEnum && genericType != null) {
			if(genericType instanceof  ParameterizedType) {
				for(Type tipo : ((ParameterizedType) genericType).getActualTypeArguments()) {
					if(tipo instanceof ParameterizedType) {
						ParameterizedType pType = (ParameterizedType)tipo;
						innerTypes.add(jClassLoader.load((Class<?>)pType.getRawType(), pType));
					}
					else if(tipo instanceof WildcardType);
					else {
						innerTypes.add(jClassLoader.load((Class<?>)tipo, null));
					}
				}	
			}
		}
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
		this.jClassLoader = jClassLoader;
		name = f.getName();
		simpleName = f.getSimpleName();
		if(f.getPackage() != null)
			packageName = f.getPackage().getName();
		isArray = f.isArray();
		isEnum = f.isEnum();
		primitive = f.isPrimitive();
		nullable = !f.isPrimitive();
		if(!name.startsWith("java.")) {
			if(isArray)
				innerTypes.add(new JType(jClassLoader, f.getComponentType(), (Type)null));
		}
		CodeSource src = f.getProtectionDomain().getCodeSource();
		if(!isArray) {
			if (src != null) {
				System.out.println(name + " : " + src.getLocation().toString());
			}else {
				System.out.println(name+" : null");
			}
		}
	}
	public List<IJType> getInnerTypes() {
		return innerTypes;
	}
	@Override
	public JClass resolve() {
		IJType ret = jClassLoader.forName(name);
		if(ret == null || !(ret instanceof JClass))
			throw new NullPointerException("Class not found: " + name);
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
	public IJClassLoader classLoader() {
		return jClassLoader;
	}
	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> clase) {
		if(jClassLoader != null) {
			IJType c = jClassLoader.forName(name);
			//If we get a reference for a type, we try to get the class
			if(c != null && c instanceof JClass)
				return c.isAnnotationPresent(clase);
		}
		if(!primitive)
			try {
				return Class.forName(name).isAnnotationPresent(clase);
			} catch (ClassNotFoundException e) {
			}
		return false;
	}
	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		if(jClassLoader != null) {
			IJType c = jClassLoader.forName(name);
			if(c != null && c instanceof JClass)
				return c.getAnnotation(annotationClass);
		}
		if(!primitive)
			try {
				return Class.forName(name).getAnnotation(annotationClass);
			} catch (ClassNotFoundException e) {
			}
		return null; 
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
		for(IJType c : classes)if(c.getName().equals(name))return true;
		return false;
	}
	@Override
	public final String getName() {
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
	public List<JAnnotation> getAnnotations() {
		if(jClassLoader != null) {
			IJType c = jClassLoader.forName(name);
			if(c != null && c instanceof JClass)
				return ((JClass)c).annotations;
		}
		return Collections.emptyList();
	}
	@Override
	public String toString() {
		if(getInnerTypes().isEmpty())
			return getName()+":";
		else if(isArray())
			return getInnerTypes().get(0)+":[]";
		else
			return getName()+":<" + getInnerTypes().stream().map(f->f.toString()).collect(Collectors.joining(", ")) + ">";
	}
	@Override
	public boolean nullable() {
		return nullable;
	}
	@Override
	public JAnnotation getJAnnotationWithAncestorCheck(String name) {
		JAnnotation ret = getJAnnotation(name);
		if(ret == null) {
			JPackage p = getPackage();
			if(p != null)
				ret = p.getJAnnotationWithAncestorCheck(name);
		}
		return ret;
	}
}
