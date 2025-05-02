package jcrystal.types.locals;

import java.lang.annotation.Annotation;
import java.util.List;

public class GenericLocalMethod implements ILocalMethod {
    private ILocalType returnType;
    private String name;
    private int modifiers;
    private boolean isVoid;
    private List<ILocalVariable> params;
    private Annotation[] annotations;

    public GenericLocalMethod() {
    }

    public GenericLocalMethod(ILocalType returnType, String name, int modifiers, boolean isVoid,
                       List<ILocalVariable> params, Annotation[] annotations) {
        this.returnType = returnType;
        this.name = name;
        this.modifiers = modifiers;
        this.isVoid = isVoid;
        this.params = params;
        this.annotations = annotations;
    }

    @Override
    public ILocalType getReturnType() {
        return returnType;
    }

    public void setReturnType(ILocalType returnType) {
        this.returnType = returnType;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    public boolean isVoid() {
        return isVoid;
    }

    public void setVoid(boolean aVoid) {
        isVoid = aVoid;
    }

    @Override
    public List<ILocalVariable> params() {
        return params;
    }

    public void setParams(List<ILocalVariable> params) {
        this.params = params;
    }

    @Override
    public Annotation[] annotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }
}
