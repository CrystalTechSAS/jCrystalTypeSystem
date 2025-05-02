package jcrystal.types.locals;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class JavaLocalField implements ILocalVariable {

    Field field;
    public JavaLocalField(Field field){
        this.field = field;
    }

    @Override
    public ILocalType type() {
        return new JavaLocalType(field.getType(), field.getGenericType());
    }

    @Override
    public String getName() {
        return field.getName();
    }

    @Override
    public String getValue() {
        return null;
    }

    @Override
    public int getModifiers() {
        return field.getModifiers();
    }

    @Override
    public String staticDefaultValue() {
        String staticDefaultValue = null;
        if(Modifier.isStatic(getModifiers())) {
			try {
				field.setAccessible(true);
				Object defaultValue = field.get(null);
				if(defaultValue != null) {
					staticDefaultValue = defaultValue.toString();
					if(type().isPrimitive()) {
						if(field.getType() == long.class && defaultValue.equals(new Long(0)))
							staticDefaultValue = null;		
						else if(field.getType() == int.class && defaultValue.equals(new Integer(0)))
							staticDefaultValue = null;
						else if(field.getType() == double.class && defaultValue.equals(new Double(0)))
							staticDefaultValue = null;
						else if(field.getType() == boolean.class && defaultValue.equals(new Boolean(false)))
							staticDefaultValue = null;
					}else if(type().isEnum()) {
						staticDefaultValue = field.getType().getSimpleName()+"."+field.getType().getMethod("name").invoke(defaultValue).toString();
					}else if(defaultValue.equals(new Long(0)))
						staticDefaultValue = null;		
					else if(defaultValue.equals(new Integer(0)))
						staticDefaultValue = null;
					else if(defaultValue.equals(new Double(0)))
						staticDefaultValue = null;
					else if(defaultValue.equals(new Boolean(false)))
						staticDefaultValue = null;

				}
				//if(defaultValue != null && field.getType().isEnum())
					//defaultValue = f.getType().getSimpleName()+"."+f.getType().getMethod("name").invoke(defaultValue);
			} catch (Exception e) {}
		}
        return staticDefaultValue;
    }

    @Override
    public Annotation[] annotations() {
        return field.getAnnotations();
    }    
}
