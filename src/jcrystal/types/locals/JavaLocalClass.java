package jcrystal.types.locals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class JavaLocalClass extends JavaLocalType implements ILocalClass {

    public JavaLocalClass(Class<?> localClass, Type genericType) {
        super(localClass, genericType);
    }

    @Override
    public boolean isStatic() {
        return Modifier.isStatic(localClass.getModifiers());
    }

    @Override
    public boolean inner() {
        return localClass.isMemberClass();
    }

    @Override
    public List<ILocalType> interfaces() {
        Class<?>[] ifaces = localClass.getInterfaces();
        List<ILocalType> interfaces = new ArrayList<>();
		for(int e = 0; e < ifaces.length; e++)
			interfaces.add(new JavaLocalType(ifaces[e], localClass.getGenericInterfaces()[e]));
        return interfaces;
    }

    @Override
    public List<ILocalVariable> attributes() {
        return Arrays.stream(localClass.getDeclaredFields()).map(f->{
			return (ILocalVariable)new JavaLocalField(f);
		}).collect(Collectors.toList());
    }

    @Override
    public List<ILocalMethod> methods() {
        return Arrays.stream(localClass.getDeclaredMethods())
        .filter(m->!m.getName().startsWith("lambda$"))
        .map(m->{
            return (ILocalMethod)new JavaLocalMethod(m);
		})
        .collect(Collectors.toList());
    }

    @Override
    public List<ILocalMethod> constructors() {
        return Arrays.stream(localClass.getConstructors())
        .map(c->{
			return (ILocalMethod)new JavaLocalConstructor(c);
		})
        .collect(Collectors.toList());
    }

    @Override
    public ILocalEnum enumData() {
        if(isEnum())
			return new JavaLocalEnum(localClass);
		return null;
    }

    @Override
    public ILocalType superClass() {
        if(localClass.getSuperclass() != null)
			return new JavaLocalType(localClass.getSuperclass(), localClass.getGenericSuperclass());
        return null;
    }

    @Override
    public ILocalType declaringClass() {
		if(localClass.getDeclaringClass() != null)
			return new JavaLocalType(localClass.getDeclaringClass(), null);
        return null;
    }

    @Override
    public Annotation[] annotations() {
        return localClass.getAnnotations();
    }
    
}
