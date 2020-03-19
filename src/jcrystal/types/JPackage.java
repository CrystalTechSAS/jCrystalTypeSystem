package jcrystal.types;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.TreeMap;

import jcrystal.types.loaders.IJClassLoader;

public class JPackage implements JIAnnotable, Serializable{
	private static final long serialVersionUID = -5704843477884875405L;
	
	public IJClassLoader jClassLoader;
	
	String name;
	private String parentName;
	Map<String, JAnnotation> annotations= new TreeMap<>();
	
	public JPackage(IJClassLoader jClassLoader, String paquete){
		this.jClassLoader = jClassLoader;
		this.name = paquete;
		int pos = name.lastIndexOf('.');
		if(pos > 0)
			parentName = name.substring(0, pos);
	}
	public JPackage(IJClassLoader jClassLoader, Package paquete){
		this(jClassLoader, paquete.getName());
		for(Annotation a : paquete.getAnnotations())
			annotations.put(a.annotationType().getName(), new JAnnotation(a));
	}
	
	@Override
	public Map<String, JAnnotation> getAnnotations() {
		return annotations;
	}
	
	public String getName() {
		return name;
	}
	
	@Override
	public JAnnotation getJAnnotationWithAncestorCheck(String name) {
		JAnnotation ret = getJAnnotation(name);
		if(ret != null)
			return ret;
		if(parentName != null) {
			JPackage parent = jClassLoader.packageForName(parentName);
			if(parent != null)
				return parent.getJAnnotationWithAncestorCheck(name);
		}
		return null;
	}
	
	public String getParentName() {
		return parentName;
	}
}
