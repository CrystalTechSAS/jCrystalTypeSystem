package jcrystal.types.convertions;

import java.lang.annotation.Annotation;

import jcrystal.types.JIAnnotable;

public interface IAnnotationResolver {
	public <A extends Annotation> A resolveAnnotation(Class<A> annotationClass, JIAnnotable element);
}
