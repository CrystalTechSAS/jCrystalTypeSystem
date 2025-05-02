package jcrystal.types.loaders;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.TreeMap;

import jcrystal.types.IJType;
import jcrystal.types.JClass;
import jcrystal.types.JPackage;
import jcrystal.types.JType;
import jcrystal.types.locals.ILocalClass;
import jcrystal.types.locals.ILocalType;

public class JClassLoader implements IJClassLoader, Serializable{

	private static final long serialVersionUID = -7756184243183779455L;
	
	public IJClassLoader parentClassLoader;
	
	TreeMap<String, IJType> loadedClasses = new TreeMap<>();
	TreeMap<String, IJType> loadedSimpleNameClasses = new TreeMap<>();
	TreeMap<String, JPackage> loadedPackages = new TreeMap<>();
	
	@Override
	public TreeMap<String, IJType> getLoadedClasses() {
		return loadedClasses;
	}

	@Override
	public TreeMap<String, JPackage> getLoadedPackages() {
		return loadedPackages;
	}
	public IJType load(Class<?> clase, Type genericType) {
		if(genericType == null || genericType instanceof  Class<?> || (genericType instanceof  ParameterizedType && ((ParameterizedType) genericType).getActualTypeArguments().length == 0)) {
			IJType ret = loadedClasses.get(clase.getName()); 
			if(ret == null)
				loadedClasses.put(clase.getName(), ret = new JType(this, clase));
			return ret;
		}else{
			IJType ret = loadedClasses.get(genericType.getTypeName()); 
			if(ret == null)
				loadedClasses.put(genericType.getTypeName(), ret = new JType(this, clase, genericType));
			return ret;
		}
	}

	public IJType forName(String name) {
		return loadedClasses.get(name);
	}

	@Override
	public IJType forSimpleName(String name) {
		return loadedSimpleNameClasses.get(name);
	}

	public JPackage packageForName(String name) {
		JPackage ret = loadedPackages.get(name);
		if(ret == null) {
			Package p = Package.getPackage(name);
			if(p!=null)//TODO: potential bug.
				loadedPackages.put(name, ret = new JPackage(this, p));
		}
		return ret;
	}

	@Override
	public void load(JPackage jPackage) {
		loadedPackages.put(jPackage.getName(), jPackage);
	}
	
	@Override
	public void load(IJType type) {
		if(type == null)
			return;
		if(!loadedClasses.containsKey(type.name())) {
			loadedClasses.put(type.name(), type);
			loadedSimpleNameClasses.put(type.getSimpleName(), type);
		}
	}
	@Override
	public IJType load(ILocalType type) {
		if(type == null)
			return null;
		if(!loadedClasses.containsKey(type.getName())) {
			JType newType = new JType(this, type);
			loadedClasses.put(type.getName(), newType);
			loadedSimpleNameClasses.put(type.getSimpleName(), newType);
		}
		return loadedClasses.get(type.getName());
	}
	public IJType load(ILocalClass type) {
		if(type == null)
			return null;
		JClass newType = new JClass(this, type);
		newType.load(type);
		loadedClasses.put(type.getName(), newType);
		loadedSimpleNameClasses.put(type.getSimpleName(), newType);
		return loadedClasses.get(type.getName());
	}
	@Override
	public IJClassLoader getParentClassLoader() {
		return parentClassLoader;
	}


}
