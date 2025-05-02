package jcrystal.types.locals;

import java.util.List;
import java.util.Map;

public class GenericLocalEnum implements ILocalEnum {

    private String name;
    private List<ILocalEnumValue> values;
    private Map<String, ILocalType> properties;

    public GenericLocalEnum() {
    }

    public GenericLocalEnum(String name, List<ILocalEnumValue> values, Map<String, ILocalType> properties) {
        this.name = name;
        this.values = values;
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
    public List<ILocalEnumValue> getValues() {
        return values;
    }

    public void setValues(List<ILocalEnumValue> values) {
        this.values = values;
    }

    @Override
    public Map<String, ILocalType> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, ILocalType> properties) {
        this.properties = properties;
    }
}
