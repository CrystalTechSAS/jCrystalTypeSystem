package jcrystal.types.locals;

import java.lang.annotation.Annotation;
import java.util.List;

public class GenericLocalClass extends GenericLocalType implements ILocalClass {

    private boolean isStatic;
    private boolean inner;
    private List<ILocalType> interfaces;
    private List<ILocalVariable> attributes;
    private List<ILocalMethod> methods;
    private List<ILocalMethod> constructors;
    private ILocalEnum enumData;
    private ILocalType superClass;
    private ILocalType declaringClass;
    private Annotation[] annotations;

    public GenericLocalClass() {
    }

    public GenericLocalClass(boolean isStatic,
                      boolean inner,
                      List<ILocalType> interfaces,
                      List<ILocalVariable> attributes,
                      List<ILocalMethod> methods,
                      List<ILocalMethod> constructors,
                      ILocalEnum enumData,
                      ILocalType superClass,
                      ILocalType declaringClass,
                      Annotation[] annotations) {
        this.isStatic = isStatic;
        this.inner = inner;
        this.interfaces = interfaces;
        this.attributes = attributes;
        this.methods = methods;
        this.constructors = constructors;
        this.enumData = enumData;
        this.superClass = superClass;
        this.declaringClass = declaringClass;
        this.annotations = annotations;
    }

    @Override
    public boolean isStatic() {
        return isStatic;
    }

    public void setStatic(boolean aStatic) {
        isStatic = aStatic;
    }

    @Override
    public boolean inner() {
        return inner;
    }

    public void setInner(boolean inner) {
        this.inner = inner;
    }

    @Override
    public List<ILocalType> interfaces() {
        return interfaces;
    }

    public void setInterfaces(List<ILocalType> interfaces) {
        this.interfaces = interfaces;
    }

    @Override
    public List<ILocalVariable> attributes() {
        return attributes;
    }

    public void setAttributes(List<ILocalVariable> attributes) {
        this.attributes = attributes;
    }

    @Override
    public List<ILocalMethod> methods() {
        return methods;
    }

    public void setMethods(List<ILocalMethod> methods) {
        this.methods = methods;
    }

    @Override
    public List<ILocalMethod> constructors() {
        return constructors;
    }

    public void setConstructors(List<ILocalMethod> constructors) {
        this.constructors = constructors;
    }

    @Override
    public ILocalEnum enumData() {
        return enumData;
    }

    public void setEnumData(ILocalEnum enumData) {
        this.enumData = enumData;
    }

    @Override
    public ILocalType superClass() {
        return superClass;
    }

    public void setSuperClass(ILocalType superClass) {
        this.superClass = superClass;
    }

    @Override
    public ILocalType declaringClass() {
        return declaringClass;
    }

    public void setDeclaringClass(ILocalType declaringClass) {
        this.declaringClass = declaringClass;
    }

    @Override
    public Annotation[] annotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }
}
