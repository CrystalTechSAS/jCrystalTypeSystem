package jcrystal.types;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import jcrystal.types.convertions.AnnotationResolverHolder;

public interface JIAnnotable {
	public List<JAnnotation> getAnnotations();
	public default void loadAnnotations(Annotation[] annotations) {
		Arrays.stream(annotations).forEach(a->{
			try {
				getAnnotations().add(new JAnnotation(a));
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
		return getAnnotations().stream().anyMatch(f->f.name.equals(name));
	}
	public default boolean isAnnotationPresent(Class<? extends Annotation> clase) {
		return getAnnotations().stream().anyMatch(f->f.name.equals(clase.getName()));
	}
	@SuppressWarnings("unchecked")
	public default boolean isAnyAnnotationPresent(Class<? extends Annotation>...clases) {
		for(Class<? extends Annotation> c : clases)
			if(isAnnotationPresent(c))
				return true;
		return false;
	}
	public default JAnnotation getJAnnotation(String name) {
		return getAnnotations().stream().filter(f->f.name.equals(name)).findFirst().orElse(null);
	}
	public default <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return AnnotationResolverHolder.CUSTOM_RESOLVER.resolveAnnotation(annotationClass, this);
	}
	public default void ifJAnnotation(String name, Consumer<JAnnotation> consumer) {
		getAnnotations().stream().filter(f->f.name.equals(name)).findFirst().ifPresent(consumer);
	}
	public default <A extends Annotation> boolean ifAnnotation(Class<A> clase, Consumer<A> consumer) {
		A annotation = getAnnotation(clase);
		if(annotation != null)
			consumer.accept(annotation);
		return annotation != null;
	}
	public JAnnotation getJAnnotationWithAncestorCheck(String name);
}
