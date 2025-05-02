package jcrystal.types.loaders;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.TreeMap;

import jcrystal.types.IJType;
import jcrystal.types.JClass;
import jcrystal.types.JPackage;
import jcrystal.types.JType;
import jcrystal.types.locals.ILocalType;

public interface IJClassLoader {
	public IJType load(Class<?> clase, Type genericType);
	public IJType load(ILocalType type);
	public TreeMap<String, IJType> getLoadedClasses();
	public TreeMap<String, JPackage> getLoadedPackages();
	
	public IJType forName(String name);
	public IJType forSimpleName(String name);
	public JPackage packageForName(String name);
	
	public default boolean subclassOf(IJType jtype, Class<?> clase) {
		if(jtype.name().equals(clase.getName()))//Equivalencia
			return true;
		if(jtype.name().equals("java.lang.Object"))//0
			return false;
		if(jtype instanceof JClass)
			return subclassOf((JClass)jtype, clase);
		IJType loaded = getLoadedClasses().get(jtype.name());
		if(loaded != null && loaded instanceof JClass)
			return subclassOf((JClass)loaded, clase);
		if(getParentClassLoader() != null)
			return getParentClassLoader().subclassOf(jtype, clase);
		return false;
	}
	public default boolean subclassOf(IJType jtype, IJType clase) {
		if(getLoadedClasses().containsKey(jtype.name()))
			return subclassOf((JClass)getLoadedClasses().get(jtype.name()), clase);
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
		if(getParentClassLoader() != null)
			return getParentClassLoader().isAnnotationPresent(jtype, annotation);
		return false;
	}
	
	public void load(IJType type);
	public void load(JPackage jPackage);
	
	public IJClassLoader getParentClassLoader();
}