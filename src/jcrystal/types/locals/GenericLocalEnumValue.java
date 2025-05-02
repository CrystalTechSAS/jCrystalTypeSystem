package jcrystal.types.locals;

import java.util.Map;

public class GenericLocalEnumValue implements  ILocalEnumValue{

    private String name;
    private Map<String, Object> properties;

    public GenericLocalEnumValue() {
    }

    public GenericLocalEnumValue(String name, Map<String, Object> properties) {
        this.name = name;
        this.properties = properties;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Object getPropertyValue(String propertyName) {
        return properties != null ? properties.get(propertyName) : null;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
