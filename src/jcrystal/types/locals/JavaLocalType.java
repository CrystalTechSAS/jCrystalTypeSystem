package jcrystal.types.locals;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;

public class JavaLocalType implements ILocalType {

    Class<?> localClass;
    Type genericType;

    public JavaLocalType(Class<?> localClass, Type genericType){
        this.localClass = localClass;
        this.genericType = genericType;
    }

    @Override
    public String getName() {
        return localClass.getName();
    }

    @Override
    public String getSimpleName() {
        return localClass.getSimpleName();
    }

    @Override
    public String getPackageName() {
        if(localClass.getPackage() != null)
            return localClass.getPackage().getName();
		return null;
    }

    @Override
    public boolean isArray() {
        return localClass.isArray();
    }

    @Override
    public boolean isInterface() {
        return localClass.isInterface();
    }

    @Override
    public boolean isEnum() {
		return localClass.isEnum();
    }

    @Override
    public boolean isPrimitive() {
		return localClass.isPrimitive();
    }

    @Override
    public boolean isClientType() {
        if(!getName().startsWith("java.")){
            CodeSource src = localClass.getProtectionDomain().getCodeSource();
            return src != null && src.getLocation().toString().endsWith("WEB-INF/classes/");
        }
        return false;
    }

    @Override
    public int getModifiers() {
        return localClass.getModifiers();
    }

    @Override
    public List<ILocalType> getInnerTypes() {
        List<ILocalType> innerTypes = new ArrayList<>();
        if(isArray())
            innerTypes.add(new JavaLocalType(localClass.getComponentType(), (Type)null));
        else if(!isArray() && !isEnum() && genericType != null) {
			if(genericType instanceof  ParameterizedType) {
				for(Type tipo : ((ParameterizedType) genericType).getActualTypeArguments()) {
					if(tipo instanceof ParameterizedType) {
						ParameterizedType pType = (ParameterizedType)tipo;
						innerTypes.add(new JavaLocalType((Class<?>)pType.getRawType(), pType));
					}
					else if(tipo instanceof WildcardType);
					else {
						innerTypes.add(new JavaLocalType((Class<?>)tipo, null));
					}
				}	
			}
		}
        return innerTypes;
    }
    
}
