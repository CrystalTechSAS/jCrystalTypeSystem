package jcrystal.preprocess.descriptions;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class JMethod implements JIAnnotable, JIHasModifiers, Serializable{
	private static final long serialVersionUID = -202642428369017987L;
	
	public JType returnType;
	public String name;
	public int modifiers;
	public boolean isVoid;
	public List<JVariable> params = new ArrayList<>();
	public List<JAnnotation> annotations= new ArrayList<>();
	public String declaringClass;
	
	public JMethod(Method m) {
		modifiers = m.getModifiers();
		name = m.getName();
		returnType = JTypeSolver.load(m.getReturnType(), m.getGenericReturnType());
		isVoid = m.getReturnType() == Void.TYPE;
		for(Parameter p : m.getParameters()) {
			params.add(new JVariable(p));
		}
		loadAnnotations(m.getAnnotations());
		declaringClass = m.getDeclaringClass().getName();
	}
	public JMethod(Constructor<?> m) {
		modifiers = m.getModifiers();
		name = m.getName();
		returnType = null;
		isVoid = true;
		for(Parameter p : m.getParameters()) {
			params.add(new JVariable(p));
		}
		loadAnnotations(m.getAnnotations());
		declaringClass = m.getDeclaringClass().getName();
	}
	public int getModifiers() {
		return modifiers;
	}
	public List<JAnnotation> getAnnotations() {
		return annotations;
	}
	public String getName() {
		return name;
	}
	public JType getReturnType() {
		return returnType;
	}
	
}
