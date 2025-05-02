package jcrystal.types;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jcrystal.types.loaders.IJClassLoader;

public class JTypeWrapper implements JIAnnotable, Serializable, IJType{
	private static final long serialVersionUID = -875202507362620017L;

	protected IJType wrappedType;
	private String name;
	private String simpleName;
	private List<IJType> innerTypes;
	public JTypeWrapper(IJType type) {
		this.wrappedType = type;
		name = type.name();
		simpleName = type.getSimpleName();
		innerTypes = new ArrayList<>(type.getInnerTypes());
	}
	public IJType wrappedType() {
		return wrappedType;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setSimpleName(String simpleName) {
		this.simpleName = simpleName;
	}
	@Override
	public JClass resolve() {
		return wrappedType.resolve();
	}
	@Override
	public JClass tryResolve() {
		return wrappedType.tryResolve();
	}
	@Override
	public final boolean isEnum() {
		return wrappedType.isEnum();
	}
	@Override
	public final boolean isArray() {
		return wrappedType.isArray();
	}
	@Override
	public final boolean isInterface() {
		return wrappedType.isInterface();
	}
	@Override
	public final boolean isPrimitive() {
		return wrappedType.isPrimitive();
	}
	@Override
	public boolean isAnnotationPresent(Class<? extends Annotation> clase) {
		return wrappedType.isAnnotationPresent(clase);
	}
	@Override
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return wrappedType.getAnnotation(annotationClass); 
	}
	@Override
	public final boolean isSubclassOf(Class<?> clase) {
		return wrappedType.isSubclassOf(clase);
	}
	@Override
	public final boolean isSubclassOf(IJType clase) {
		return wrappedType.isSubclassOf(clase);
	}
	@Override
	public final boolean is(Class<?> ... classes) {
		return wrappedType.is(classes);
	}
	@Override
	public final boolean is(IJType ... classes) {
		return wrappedType.is(classes);
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
		return wrappedType.getPackage();
	}
	@Override
	public final String getPackageName() {
		return wrappedType.getPackageName();
	}
	@Override
	public Map<String, JAnnotation> getAnnotations() {
		return wrappedType.getAnnotations();
	}
	@Override
	public List<IJType> getInnerTypes() {
		return innerTypes;
	}
	@Override
	public String toString() {
		if(getInnerTypes().isEmpty())
			return name();
		else if(isArray())
			return getInnerTypes().get(0)+"[]";
		else
			return name()+":<" + getInnerTypes().stream().map(f->f.toString()).collect(Collectors.joining(", ")) + ">";
	}
	@Override
	public boolean nullable() {
		return wrappedType.nullable();
	}
	@Override
	public JAnnotation getJAnnotationWithAncestorCheck(String name) {
		return wrappedType.getJAnnotationWithAncestorCheck(name);
	}
	@Override
	public IJClassLoader classLoader() {
		return wrappedType.classLoader();
	}
	public IJType getWrappedType() {
		return wrappedType;
	}
}
