package jcrystal.types.locals;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GenericLocalType implements ILocalType {

    String name;
    String simpleName;
    String packageName;
    boolean isArray;
    boolean isInterface;
    boolean isEnum;
    boolean isPrimitive;
    boolean isClientType;
    List<GenericLocalType> innerTypes;
    int modifiers;

    public GenericLocalType() {
    }

    public GenericLocalType(String name, String simpleName, String packageName,
                            boolean isArray, boolean isInterface, boolean isEnum, boolean isPrimitive, boolean isClientType,
                            List<GenericLocalType> innerTypes, int modifiers) {
        this.name = name;
        this.simpleName = simpleName;
        this.packageName = packageName;
        this.isArray = isArray;
        this.isInterface = isInterface;
        this.isEnum = isEnum;
        this.isPrimitive = isPrimitive;
        this.isClientType = isClientType;
        this.innerTypes = innerTypes;
        this.modifiers = modifiers;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public boolean isArray() {
        return isArray;
    }

    public void setArray(boolean array) {
        isArray = array;
    }

    public boolean isEnum() {
        return isEnum;
    }

    public void setEnum(boolean anEnum) {
        isEnum = anEnum;
    }

    public boolean isPrimitive() {
        return isPrimitive;
    }

    public boolean isInterface() {
        return isInterface;
    }

    public void setIsInterface(boolean isInterface) {
        this.isInterface = isInterface;
    }

    public void setPrimitive(boolean primitive) {
        isPrimitive = primitive;
    }

    public boolean isClientType() {
        return isClientType;
    }

    public void setClientType(boolean clientType) {
        isClientType = clientType;
    }

    public List<ILocalType> getInnerTypes() {
        if(innerTypes == null)
            return Collections.emptyList();
        return innerTypes.stream().map(f->(ILocalType)f).collect(Collectors.toList());
    }

    public void setInnerTypes(List<GenericLocalType> innerTypes) {
        this.innerTypes = innerTypes;
    }

    public int getModifiers() {
        return modifiers;
    }

    public void setModifiers(int modifiers) {
        this.modifiers = modifiers;
    }
}
