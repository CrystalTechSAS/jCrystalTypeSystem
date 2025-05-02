package jcrystal.types.locals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class JavaLocalMethod implements ILocalMethod {

    Method method;
    
    public JavaLocalMethod(Method method){
        this.method = method;
    }

    @Override
    public ILocalType getReturnType() {
        return new JavaLocalType(method.getReturnType(), method.getGenericReturnType());
    }

    @Override
    public String getName() {
		return method.getName();
    }

    @Override
    public int getModifiers() {
		return method.getModifiers();
    }

    @Override
    public boolean isVoid() {
        return method.getReturnType() == Void.TYPE;
    }

    @Override
    public List<ILocalVariable> params() {
        List<ILocalVariable> params = new ArrayList<>();
        for(Parameter p : method.getParameters()) {
			params.add(new JavaLocalParameter(p));
		}
        return params;
    }

    @Override
    public Annotation[] annotations() {
        return method.getAnnotations();
    }
}
