package jcrystal.types.locals;

import java.lang.annotation.Annotation;
import java.util.List;

public interface ILocalClass extends ILocalType {
    boolean isStatic();
	boolean inner();
	List<ILocalType> interfaces();
	List<ILocalVariable> attributes();
	List<ILocalMethod> methods();
	List<ILocalMethod> constructors();
	ILocalEnum enumData();
	ILocalType superClass();
	ILocalType declaringClass();
	Annotation[] annotations();
}
