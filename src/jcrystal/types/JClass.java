package jcrystal.types;

import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jcrystal.types.convertions.AnnotationResolverHolder;
import jcrystal.types.loaders.IJClassLoader;

public class JClass extends JType implements JIAnnotable, JIHasModifiers, Serializable{
	private static final long serialVersionUID = 143568675432L;
	boolean isStatic;
	boolean inner;
	public int modifiers;
	public List<IJType> interfaces = new ArrayList<>();
	public List<JVariable> attributes = new ArrayList<>();
	public List<JMethod> methods = new ArrayList<>();
	public List<JMethod> constructors = new ArrayList<>();
	public List<JAnnotation> annotations= new ArrayList<>();
	public JEnum enumData;
	public IJType superClass;
	public IJType declaringClass;
	
	public JClass(IJClassLoader jClassLoader, Class<?> clase){
		super(jClassLoader, clase);
		modifiers = clase.getModifiers();
		name = clase.getName();
		simpleName = clase.getSimpleName();
		isEnum = clase.isEnum();
		isStatic = Modifier.isStatic(clase.getModifiers());
		inner = clase.isMemberClass();
		if(clase.getSuperclass() != null)
			superClass = jClassLoader.load(clase.getSuperclass(), clase.getGenericSuperclass());
		if(clase.getDeclaringClass() != null)
			declaringClass = jClassLoader.load(clase.getDeclaringClass(), null);
		Class<?>[] ifaces = clase.getInterfaces();
		for(int e = 0; e < ifaces.length; e++)
			interfaces.add(jClassLoader.load(ifaces[e], clase.getGenericInterfaces()[e]));
		if(clase.getPackage() != null)
			packageName = clase.getPackage().getName();
		Arrays.stream(clase.getDeclaredFields()).forEach(f->{
			attributes.add(new JVariable(jClassLoader, this, f));
		});
		Arrays.stream(clase.getConstructors()).forEach(c->{
			constructors.add(new JMethod(jClassLoader, this, c));
		});
		Arrays.stream(clase.getDeclaredMethods()).forEach(m->{
			if(!m.getName().startsWith("lambda$"))
				methods.add(new JMethod(jClassLoader, this, m));
		});
		loadAnnotations(clase.getAnnotations());
		if(isEnum)
			enumData = new JEnum(jClassLoader, clase);
	}
	@Override
	public boolean isSubclassOf(Class<?> clase) {
		return super.isSubclassOf(clase) || interfaces.stream().anyMatch(c->c.isSubclassOf(clase)); 
		
	}
	public boolean hasEmptyConstructor() {
		return constructors.isEmpty() || constructors.stream().anyMatch(f->f.params.isEmpty());
	}
	public boolean isInner() {
		return inner;
	}
	@Override
	public int getModifiers() {
		return modifiers;
	}
	@Override
	public List<JAnnotation> getAnnotations() {
		return annotations;
	}
	
	public File getFile(File srcFile){
		return new File(srcFile, name.replace(".", "/")+".java");
	}
	public IJType getSuperClass() {
		return superClass;
	}
	public IJType getDeclaringClass() {
		return declaringClass;
	}
	public boolean isAnnotationPresent(Class<? extends Annotation> clase) {
		boolean v = getAnnotations().stream().anyMatch(f->f.name.equals(clase.getName()));
		return v;
	}
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		A b = AnnotationResolverHolder.CUSTOM_RESOLVER.resolveAnnotation(annotationClass, this);
		return b;
	}
}
