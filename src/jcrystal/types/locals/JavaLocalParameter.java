package jcrystal.types.locals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class JavaLocalParameter implements ILocalVariable {

    Parameter parameter;
    public JavaLocalParameter(Parameter parameter){
        this.parameter = parameter;
    }

    @Override
    public ILocalType type() {
        return new JavaLocalType(parameter.getType(), parameter.getParameterizedType());
    }

    @Override
    public String getName() {
        return parameter.getName();
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public int getModifiers() {
        return parameter.getModifiers();
    }

    @Override
    public String staticDefaultValue() {
        return null;
    }

    @Override
    public Annotation[] annotations() {
        return parameter.getAnnotations();
    }    
}
