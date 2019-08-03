package jcrystal.preprocess.descriptions;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class JMethod implements JIAnnotable, JIHasModifiers, Serializable{
	private static final long serialVersionUID = -202642428369017987L;
	
	public IJType returnType;
	public String name;
	public int modifiers;
	public boolean isVoid;
	public List<JVariable> params = new ArrayList<>();
	public List<JAnnotation> annotations= new ArrayList<>();
	public JClass declaringClass;
	
	public JMethod(JClass declaringClass, Method m) {
		this.declaringClass = declaringClass;
		modifiers = m.getModifiers();
		name = m.getName();
		returnType = JTypeSolver.load(m.getReturnType(), m.getGenericReturnType());
		isVoid = m.getReturnType() == Void.TYPE;
		for(Parameter p : m.getParameters()) {
			params.add(new JVariable(this, p));
		}
		loadAnnotations(m.getAnnotations());
	}
	public JMethod(JClass declaringClass, Constructor<?> m) {
		this.declaringClass = declaringClass;
		modifiers = m.getModifiers();
		name = m.getName();
		returnType = null;
		isVoid = true;
		for(Parameter p : m.getParameters()) {
			params.add(new JVariable(this, p));
		}
		loadAnnotations(m.getAnnotations());
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
	public IJType getReturnType() {
		return returnType;
	}
	@Override
	public JAnnotation getJAnnotationWithAncestorCheck(String name) {
		JAnnotation ret = getJAnnotation(name);
		if(ret != null)
			return ret;
		return declaringClass.getJAnnotationWithAncestorCheck(name);
	}
	
}
