package jcrystal.types.locals;

import java.lang.annotation.Annotation;
import java.util.List;
import jcrystal.types.JVariable;

public interface ILocalMethod {
    ILocalType getReturnType();
	String getName();
	int getModifiers();
	boolean isVoid();
	List<ILocalVariable> params();
	Annotation[] annotations();
}
