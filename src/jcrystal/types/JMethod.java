package jcrystal.types;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jcrystal.types.loaders.IJClassLoader;
import jcrystal.types.locals.ILocalMethod;
import jcrystal.types.locals.ILocalVariable;

public class JMethod implements JIAnnotable, JIHasModifiers, Serializable{
	private static final long serialVersionUID = -202642428369017987L;
	
	public IJType returnType;
	public String name;
	public int modifiers;
	public boolean isVoid;
	public List<JVariable> params = new ArrayList<>();
	public Map<String, JAnnotation> annotations= new TreeMap<>();
	public JClass declaringClass;
	
	public JMethod(IJClassLoader jClassLoader, JClass declaringClass, ILocalMethod m) {
		this.declaringClass = declaringClass;
		modifiers = m.getModifiers();
		name = m.getName();
		returnType = jClassLoader.load(m.getReturnType());
		isVoid = m.isVoid();
		for(ILocalVariable p : m.params()) {
			params.add(new JVariable(jClassLoader, this, p));
		}
		loadAnnotations(m.annotations());
	}
	public int getModifiers() {
		return modifiers;
	}
	@Override
	public Map<String, JAnnotation> getAnnotations() {
		return annotations;
	}
	public String name() {
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
