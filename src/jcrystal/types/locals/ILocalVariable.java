package jcrystal.types.locals;

import java.lang.annotation.Annotation;

public interface ILocalVariable {
    ILocalType type();
	String getName();
	String getValue();
	int getModifiers();
	String staticDefaultValue();
	Annotation[] annotations();
}
