package jcrystal.types;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Map;
import java.util.function.Consumer;

import jcrystal.types.convertions.AnnotationResolverHolder;

public interface JIAnnotable {
	public Map<String, JAnnotation> getAnnotations();
	public default void loadAnnotations(Annotation[] annotations) {
		Arrays.stream(annotations).forEach(a->{
			try {
				getAnnotations().put(a.annotationType().getName(), new JAnnotation(a));
			}catch (Exception e) {
				e.printStackTrace();
				throw new NullPointerException();
			}
		});
	}
	public default boolean isJAnnotationPresent(IJType type) {
		return type != null && isJAnnotationPresent(type.getName());
	}
	public default boolean isJAnnotationPresent(String name) {
		return getAnnotations().containsKey(name);
	}
	public default boolean isJAnnotationPresent(Class<?> clase) {
		return getAnnotations().containsKey(clase.getName());
	}
	public default boolean isAnnotationPresent(Class<? extends Annotation> clase) {
		return getAnnotations().containsKey(clase.getName());
	}
	
	@SuppressWarnings("unchecked")
	public default boolean isAnyAnnotationPresent(Class<? extends Annotation>...clases) {
		for(Class<? extends Annotation> c : clases)
			if(isAnnotationPresent(c))
				return true;
		return false;
	}
	public default JAnnotation getJAnnotation(String name) {
		return getAnnotations().get(name);
	}
	@SuppressWarnings("unchecked")
	public default <A extends JAnnotation> A getJAnnotation(Class<A> annotationClass) {
		return (A)getJAnnotation(annotationClass.getName());
	}
	public default <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return AnnotationResolverHolder.CUSTOM_RESOLVER.resolveAnnotation(annotationClass, this);
	}
	public default boolean ifJAnnotation(String name, Consumer<JAnnotation> consumer) {
		JAnnotation a = getJAnnotation(name);
		if(a != null) {
			consumer.accept(a);
			return true;
		}
		return false;
	}
	public default <A extends Annotation> boolean ifAnnotation(Class<A> clase, Consumer<A> consumer) {
		A annotation = getAnnotation(clase);
		if(annotation != null)
			consumer.accept(annotation);
		return annotation != null;
	}
	public default void addAnnotation(JAnnotation annotation) {
		getAnnotations().put(annotation.name, annotation);
	}
	
	public JAnnotation getJAnnotationWithAncestorCheck(String name);
}