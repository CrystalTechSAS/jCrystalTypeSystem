package jcrystal.types.locals;

import java.lang.annotation.Annotation;

public class GenericLocalVariable implements ILocalVariable{
    private ILocalType type;
    private String name;
    private String value;
    private int modifiers;
    private String staticDefaultValue;
    private Annotation[] annotations;

    public GenericLocalVariable() {
    }

    public GenericLocalVariable(ILocalType type, String name, String value, int modifiers,
                                String staticDefaultValue, Annotation[] annotations) {
        this.type = type;
        this.name = name;
        this.value = value;
        this.modifiers = modifiers;
        this.staticDefaultValue = staticDefaultValue;
        this.annotations = annotations;
    }

    @Override
    public ILocalType type() {
        return type;
    }

    public void setType(ILocalType type) {
        this.type = type;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }

    @Override
    public String staticDefaultValue() {
        return staticDefaultValue;
    }

    public void setStaticDefaultValue(String staticDefaultValue) {
        this.staticDefaultValue = staticDefaultValue;
    }

    @Override
    public Annotation[] annotations() {
        return annotations;
    }

    public void setAnnotations(Annotation[] annotations) {
        this.annotations = annotations;
    }
}
