package jcrystal.preprocess.descriptions;

import java.io.File;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jcrystal.preprocess.convertions.AnnotationResolverHolder;

public class JClass extends JType implements JIAnnotable, JIHasModifiers, Serializable{
	private static final long serialVersionUID = 143568675432L;
	boolean isStatic;
	boolean inner;
	public int modifiers;
	public List<JType> interfaces = new ArrayList<>();
	public List<JVariable> attributes = new ArrayList<>();
	public List<JMethod> methods = new ArrayList<>();
	public List<JMethod> constructors = new ArrayList<>();
	public List<JAnnotation> annotations= new ArrayList<>();
	public JEnum enumData;
	public JType superClass;
	public IJType declaringClass;
	
	public JClass(Class<?> clase){
		super(clase);
		modifiers = clase.getModifiers();
		name = clase.getName();
		simpleName = clase.getSimpleName();
		isEnum = clase.isEnum();
		isStatic = Modifier.isStatic(clase.getModifiers());
		inner = clase.isMemberClass();
		if(clase.getSuperclass() != null)
			superClass = JTypeSolver.load(clase.getSuperclass(), clase.getGenericSuperclass());
		if(clase.getDeclaringClass() != null)
			declaringClass = JTypeSolver.load(clase.getDeclaringClass(), null);
		Class<?>[] ifaces = clase.getInterfaces();
		for(int e = 0; e < ifaces.length; e++)
			interfaces.add(JTypeSolver.load(ifaces[e], clase.getGenericInterfaces()[e]));
		packageName = clase.getPackage().getName();
		Arrays.stream(clase.getDeclaredFields())/*.sorted((c1,c2)->c1.getName().compareTo(c2.getName()))*/.forEach(f->{
			attributes.add(new JVariable(this, f));
		});
		Arrays.stream(clase.getConstructors()).forEach(c->{
			constructors.add(new JMethod(this, c));
		});
		Arrays.stream(clase.getDeclaredMethods()).forEach(m->{
			if(!m.getName().startsWith("lambda$"))
				methods.add(new JMethod(this, m));
		});
		loadAnnotations(clase.getAnnotations());
		if(isEnum)
			enumData = new JEnum(clase);
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
	public JType getSuperClass() {
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
