package jcrystal.types.locals;

import java.util.List;

public interface ILocalType {
    String getName();
    String getSimpleName();
    String getPackageName();
    boolean isArray();
    boolean isEnum();
    boolean isPrimitive();
    boolean isInterface();
    boolean isClientType();
	List<ILocalType> getInnerTypes();
    int getModifiers();
}
