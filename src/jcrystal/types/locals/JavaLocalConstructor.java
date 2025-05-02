package jcrystal.types.locals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class JavaLocalConstructor implements ILocalMethod {

    Constructor<?> constructor;
    
    public JavaLocalConstructor(Constructor<?> constructor){
        this.constructor = constructor;
    }

    @Override
    public ILocalType getReturnType() {
        return null;
    }

    @Override
    public String getName() {
		return constructor.getName();
    }

    @Override
    public int getModifiers() {
        return constructor.getModifiers();
    }

    @Override
    public boolean isVoid() {
        return true;
    }

    @Override
    public List<ILocalVariable> params() {
        List<ILocalVariable> params = new ArrayList<>();
        for(Parameter p : constructor.getParameters()) {
			params.add(new JavaLocalParameter(p));
		}
        return params;
    }

    @Override
    public Annotation[] annotations() {
        return constructor.getAnnotations();
    }
}
