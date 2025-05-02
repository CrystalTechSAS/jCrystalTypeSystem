package jcrystal.types.locals;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class JavaLocalEnum implements ILocalEnum {

    Class<?> clase;
    public JavaLocalEnum(Class<?> clase){
        this.clase = clase;
    }

    @Override
    public String getName() {
        return clase.getSimpleName();
    }

    @Override
    public List<ILocalEnumValue> getValues() {
        List<ILocalEnumValue> valores = new ArrayList<>();
        for (Object o : clase.getEnumConstants()) {
			valores.add(new JavaLocalEnumValue(clase, (Enum<?>) o));
		}
        return valores;
    }

    @Override
    public Map<String, ILocalType> getProperties() {
        Map<String, ILocalType> propiedades = new TreeMap<>();
        try {
			for(Field f  : clase.getFields())
				if((Modifier.isFinal(f.getModifiers()) || f.getName().equals("id")) && !Modifier.isStatic(f.getModifiers()) )
					propiedades.put(f.getName(), new JavaLocalType(f.getType(), null) );
		} catch (Exception ex) {throw new NullPointerException(ex.getMessage());}
        return propiedades;
    }

}

class JavaLocalEnumValue implements ILocalEnumValue {

    Class<?> clase;
    Enum<?> value;
    public JavaLocalEnumValue(Class<?> clase, Enum<?> value){
        this.clase = clase;
        this.value = value;
    }

    @Override
    public String getName() {
        return value.name();
    }

    @Override
    public Object getPropertyValue(String propertyName) {
        try {
            Field f = clase.getField(propertyName);
            if((Modifier.isFinal(f.getModifiers()) || f.getName().equals("id")) && !Modifier.isStatic(f.getModifiers()) )
                return f.get(value);
        } catch (Exception ex) {throw new NullPointerException(ex.getMessage());}
        return null;
    }
}