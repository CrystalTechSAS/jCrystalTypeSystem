package jcrystal.types;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jcrystal.types.loaders.IJClassLoader;

public class WrapStringJType implements Serializable, IJType{
	private static final long serialVersionUID = -875202507362620017L;
	private String type;
	public WrapStringJType(String type) {
		this.type = type;
	}
	@Override
	public JClass resolve() {
		throw new NullPointerException(type);
	}
	@Override
	public JClass tryResolve() {
		throw new NullPointerException(type);
	}
	@Override
	public boolean isEnum() {
		throw new NullPointerException(type);
	}
	@Override
	public boolean isArray() {
		throw new NullPointerException(type);
	}
	@Override
	public boolean isPrimitive() {
		throw new NullPointerException(type);
	}
	@Override
	public boolean isSubclassOf(Class<?> clase) {
		throw new NullPointerException(type);
	}
	@Override
	public boolean isSubclassOf(IJType clase) {
		throw new NullPointerException(type);
	}
	@Override
	public boolean is(Class<?>... classes) {
		for(Class<?> c : classes)
			if(c.getName().equals(type))
				return true;
		return false;
	}
	@Override
	public boolean is(IJType... classes) {
		for(IJType c : classes)
			if(c.getName().equals(type))
				return true;
		return false;
	}
	@Override
	public String getName() {
		return type;
	}
	@Override
	public String getSimpleName() {
		return type;
	}
	@Override
	public JPackage getPackage() {
		throw new NullPointerException(type);
	}
	@Override
	public String getPackageName() {
		throw new NullPointerException(type);
	}
	@Override
	public List<IJType> getInnerTypes() {
		throw new NullPointerException(type);
	}
	@Override
	public Map<String, JAnnotation> getAnnotations() {
		return Collections.EMPTY_MAP;
	}
	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> clase) {
		return false;
	}
	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return null;
	}
	@Override
	public String toString() {
		return type;
	}
	@Override
	public boolean nullable() {
		throw new NullPointerException(type);
	}
	@Override
	public JAnnotation getJAnnotationWithAncestorCheck(String name) {
		throw new NullPointerException(type);
	}
	@Override
	public IJClassLoader classLoader() {
		return null;
	}
}
