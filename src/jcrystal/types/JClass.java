package jcrystal.types;

import java.io.File;
import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import jcrystal.types.convertions.AnnotationResolverHolder;
import jcrystal.types.loaders.IJClassLoader;
import jcrystal.types.locals.ILocalClass;
import jcrystal.types.locals.ILocalType;
import jcrystal.types.locals.JavaLocalClass;

public class JClass extends JType implements JIHasModifiers {
	private static final long serialVersionUID = 143568675432L;
	boolean isStatic;
	boolean inner;
	public int modifiers;
	public List<IJType> interfaces = new ArrayList<>();
	public List<JVariable> attributes = new ArrayList<>();
	public List<JMethod> methods = new ArrayList<>();
	public List<JMethod> constructors = new ArrayList<>();
	public JEnum enumData;
	public IJType superClass;
	public IJType declaringClass;
	
	public JClass(IJClassLoader jClassLoader, Class<?> clase){
		super(jClassLoader, clase);
		modifiers = clase.getModifiers();
	}
	public JClass(IJClassLoader jClassLoader, ILocalType clase){
		super(jClassLoader, clase);
		modifiers = clase.getModifiers();
	}
	
	public JClass load(Class<?> clase) {
		return this.load(new JavaLocalClass(clase, null));
	}
	public JClass load(ILocalClass clase) {
		isEnum = clase.isEnum();
		isStatic = Modifier.isStatic(clase.getModifiers());
		inner = clase.inner();
		superClass = jClassLoader.load(clase.superClass());
		declaringClass = jClassLoader.load(clase.declaringClass());
		for(ILocalType type : clase.interfaces())
			interfaces.add(jClassLoader.load(type));
		packageName = clase.getPackageName();
		clase.attributes().forEach(f->{
			attributes.add(new JVariable(jClassLoader, this, f));
		});
		clase.constructors().forEach(c->{
			constructors.add(new JMethod(jClassLoader, this, c));
		});
		clase.methods().forEach(m->{
			methods.add(new JMethod(jClassLoader, this, m));
		});
		loadAnnotations(clase.annotations());
		if(isEnum)
			enumData = new JEnum(jClassLoader, clase.enumData());
		return this;
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
	
	public File getFile(File srcFile){
		return new File(srcFile, name.replace(".", "/")+".java");
	}
	public IJType getSuperClass() {
		return superClass;
	}
	public IJType getDeclaringClass() {
		return declaringClass;
	}
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		A b = AnnotationResolverHolder.CUSTOM_RESOLVER.resolveAnnotation(annotationClass, this);
		return b;
	}
	@Override
	public final JClass resolve() {
		return this;
	}
	@Override
	public JClass tryResolve() {
		return this;
	}
}
