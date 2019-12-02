package jcrystal.types.loaders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.TreeMap;

import jcrystal.types.IJType;
import jcrystal.types.JClass;
import jcrystal.types.JPackage;
import jcrystal.types.JType;

public interface IJClassLoader {

	public IJType load(Class<?> clase, Type genericType);
	public TreeMap<String, IJType> getLoadedClasses();
	public TreeMap<String, JPackage> getLoadedPackages();
	
	public IJType forName(String name);
	public JPackage packageForName(String name);
	
	public default boolean subclassOf(IJType jtype, Class<?> clase) {
		if(jtype instanceof JClass)
			return subclassOf((JClass)jtype, clase);
		IJType loaded = getLoadedClasses().get(jtype.getName());
		if(loaded != null && loaded instanceof JClass)
			return subclassOf((JClass)loaded, clase);
		try {
			Class<?> c = Class.forName(jtype.getName());
			return clase.isAssignableFrom(c);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	public default boolean subclassOf(IJType jtype, IJType clase) {
		if(getLoadedClasses().containsKey(jtype.getName()))
			return subclassOf(getLoadedClasses().get(jtype.getName()), clase);
		return false;
	}
	public default boolean subclassOf(JClass jtype, IJType clase) {
		if(jtype.superClass != null && jtype.superClass.is(clase))
			return true;
		return jtype.interfaces.stream().anyMatch(f->f.isSubclassOf(clase));
	}
	public default boolean subclassOf(JClass jclase, Class<?> clase) {
		if(clase.getName().equals(jclase.name))
			return true;
		if(jclase.superClass != null && subclassOf(jclase.superClass, clase))
			return true;
		return jclase.interfaces.stream().anyMatch(f->subclassOf(f, clase));
	}
	public default boolean isAnnotationPresent(JType jtype, Class<? extends Annotation> annotation) {
		if(getLoadedClasses().containsKey(jtype.name))
			return getLoadedClasses().get(jtype.name).isAnnotationPresent(annotation);
		try {
			Class<?> c = Class.forName(jtype.name);
			return c.isAnnotationPresent(annotation);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void load(IJType type);
	public void load(JPackage jPackage);
}